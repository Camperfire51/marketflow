package com.camperfire.marketflow.service.user;

import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.dto.request.NotificationRequest;
import com.camperfire.marketflow.dto.request.OrderRequest;
import com.camperfire.marketflow.dto.request.PaymentRequest;
import com.camperfire.marketflow.dto.request.ProductRequestDTO;
import com.camperfire.marketflow.exception.NotEnoughProductQuantityException;
import com.camperfire.marketflow.exception.PaymentException;
import com.camperfire.marketflow.exception.ProductNotFoundException;
import com.camperfire.marketflow.exception.ProductOutOfStocksException;
import com.camperfire.marketflow.model.*;
import com.camperfire.marketflow.model.user.BaseUser;
import com.camperfire.marketflow.model.user.Customer;
import com.camperfire.marketflow.repository.CartRepository;
import com.camperfire.marketflow.service.*;
import com.camperfire.marketflow.service.email.EmailService;
import com.camperfire.marketflow.service.notification.NotificationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final AuthUserService authUserService;

    private final ProductService productService;
    private final OrderService orderService;
    private final CartRepository cartRepository;

    private final NotificationService notificationService;
    private final EmailService emailService;
    private final ProductMapper productMapper;
    private final PaymentService paymentService;

    public CustomerServiceImpl(AuthUserService authUserService, ProductService productService, OrderService orderService, CartRepository cartRepository, NotificationService notificationService, EmailService emailService, ProductMapper productMapper, PaymentService paymentService) {
        this.authUserService = authUserService;

        this.productService = productService;
        this.orderService = orderService;
        this.cartRepository = cartRepository;
        this.notificationService = notificationService;
        this.emailService = emailService;
        this.productMapper = productMapper;
        this.paymentService = paymentService;
    }

    @Override
    public Customer getCustomer() { return (Customer) authUserService.getAuthUser().getBaseUser(); }

    @Override
    public Cart getCart() {

        Customer customer = getCustomer();

        return customer.getCart();
    }

    @Override
    public Product getProduct(Long productId) {
        Product product = productService.readProduct(productId);
        return productService.readProduct(productId);
    }

    @Override
    public List<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, String category, Long vendorId){
        return productService.getProducts(name, minPrice, maxPrice, category, vendorId, ProductStatus.PUBLISHED);
    }

    @Override
    public Cart addProductToCart(Long productId, Long quantity) {
        Product product = productService.readProduct(productId);

        Long remainingQuantity = product.getQuantity();

        if (remainingQuantity == 0)
            throw new ProductOutOfStocksException("No stocks for product with id: " + productId);
        else if (remainingQuantity < quantity)
            throw new NotEnoughProductQuantityException("Not enough product quantity to add to cart.\n" +
                    " Last remaining quantity: " + remainingQuantity);

        Cart cart = getCart();

        Map<Product, Long> products = cart.getProducts();

        if (products.containsKey(product)){
            Long currentQuantity = products.get(product);
            Long newQuantity = currentQuantity + quantity;
            products.put(product, newQuantity);
        }
        else {
            products.put(product, quantity);
        }

        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProductFromCart(Long productId, Long quantity) {
        Product product = productService.readProduct(productId);

        Customer customer = getCustomer();

        Cart cart = customer.getCart();

        Map<Product, Long> products = cart.getProducts();

        if (!products.containsKey(product))
            throw new ProductNotFoundException("Product with id (" + productId + ") was not found in the cart.");

        Long currentQuantity = products.get(product);
        Long newQuantity = currentQuantity - quantity > 0 ? currentQuantity : quantity;

        products.put(product, newQuantity);
        return cartRepository.save(cart);
    }

    @Override
    public Cart resetCart() {
        Cart cart = getCart();

        cart.getProducts().clear();

        return cartRepository.save(cart);
    }

    @Override
    public Order order() {
        Cart cart = getCart();

        for (Map.Entry<Product, Long> productEntry : cart.getProducts().entrySet()) {
            Product product = productEntry.getKey();
            Long requestedQuantity = productEntry.getValue();
            Long stockQuantity = product.getQuantity();

            if (stockQuantity < requestedQuantity)
                throw new NotEnoughProductQuantityException("Not enough stock for product with id: " + productEntry.getKey().getId() + "\n Only have: " + stockQuantity);
        }

        OrderRequest orderRequest = OrderRequest.builder()
                .customer(getCustomer())
                .orderDate(LocalDateTime.now())
                .products(cart.getProducts())
                .status(OrderStatus.PENDING)
                .shippingAddress(getCustomer().getAddress())
                .build();

        PaymentRequest paymentRequest = PaymentRequest.builder().build();

        if (paymentService.processPayment(paymentRequest).getStatus() != PaymentStatus.COMPLETED)
            throw new PaymentException("Payment failed");

        Order order = orderService.createOrder(orderRequest);

        for (Map.Entry<Product, Long> productEntry : cart.getProducts().entrySet()) {
            Product product = productEntry.getKey();
            Long requestedQuantity = productEntry.getValue();
            Long stockQuantity = product.getQuantity();
            Long newQuantity = stockQuantity - requestedQuantity;

            product.setQuantity(newQuantity);

            ProductRequestDTO productRequestDTO = productMapper.toRequest(product);

            productService.updateProduct(product.getId(), productRequestDTO);

            BaseUser vendor = product.getVendor();

            if (newQuantity < product.getRestockAlarmQuantity()){
                NotificationRequest notificationRequest = NotificationRequest.builder()
                        .message("Quantity of your product " + product.getName() + "is below the restock level")
                        .type(NotificationType.PRODUCT_RESTOCK_ALARM)
                        .userId(vendor.getId())
                        .build();

                notificationService.createNotification(notificationRequest);

                BaseUser customer = getCustomer();
            }
        }

        resetCart();

        return order;
    }
}
