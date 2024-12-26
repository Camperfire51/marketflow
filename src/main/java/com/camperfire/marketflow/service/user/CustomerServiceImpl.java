package com.camperfire.marketflow.service.user;

import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.dto.request.NotificationRequest;
import com.camperfire.marketflow.dto.request.OrderRequest;
import com.camperfire.marketflow.dto.crud.payment.PaymentRequest;
import com.camperfire.marketflow.dto.request.ProductRequestDTO;
import com.camperfire.marketflow.exception.NotEnoughProductQuantityException;
import com.camperfire.marketflow.exception.PaymentException;
import com.camperfire.marketflow.exception.ProductNotFoundException;
import com.camperfire.marketflow.exception.ProductOutOfStocksException;
import com.camperfire.marketflow.model.*;
import com.camperfire.marketflow.model.user.User;
import com.camperfire.marketflow.model.user.Customer;
import com.camperfire.marketflow.repository.CartRepository;
import com.camperfire.marketflow.service.*;
import com.camperfire.marketflow.service.authUser.AuthUserService;
import com.camperfire.marketflow.service.notification.NotificationService;
import com.camperfire.marketflow.service.order.OrderService;
import com.camperfire.marketflow.service.payment.PaymentService;
import com.camperfire.marketflow.service.product.ProductService;
import org.springframework.cache.annotation.Cacheable;
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
    private final ProductMapper productMapper;
    private final PaymentService paymentService;

    public CustomerServiceImpl(AuthUserService authUserService, ProductService productService, OrderService orderService, CartRepository cartRepository, NotificationService notificationService, ProductMapper productMapper, PaymentService paymentService) {
        this.authUserService = authUserService;

        this.productService = productService;
        this.orderService = orderService;
        this.cartRepository = cartRepository;
        this.notificationService = notificationService;
        this.productMapper = productMapper;
        this.paymentService = paymentService;
    }

    @Override
    public Customer getCustomer() { return (Customer) authUserService.getAuthUser().getUser(); }

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

    @Cacheable(value = "products")
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

        Map<Long, Long> products = cart.getProducts();

        if (products.containsKey(product)){
            Long currentQuantity = products.get(product);
            Long newQuantity = currentQuantity + quantity;
            products.put(product.getId(), newQuantity);
        }
        else {
            products.put(product.getId(), quantity);
        }

        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProductFromCart(Long productId, Long quantity) {
        Product product = productService.readProduct(productId);

        Customer customer = getCustomer();

        Cart cart = customer.getCart();

        Map<Long, Long> products = cart.getProducts();

        if (!products.containsKey(product.getId()))
            throw new ProductNotFoundException("Product with id (" + productId + ") was not found in the cart.");

        Long currentQuantity = products.get(product.getId());
        Long newQuantity = currentQuantity - quantity > 0 ? currentQuantity : quantity;

        products.put(product.getId(), newQuantity);
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

        for (Map.Entry<Long, Long> productEntry : cart.getProducts().entrySet()) {
            Long productId = productEntry.getKey();
            Product product = productService.readProduct(productId);
            Long requestedQuantity = productEntry.getValue();
            Long stockQuantity = product.getQuantity();

            if (stockQuantity < requestedQuantity)
                throw new NotEnoughProductQuantityException("Not enough stock for productId: " + product.getId() + "\n Only have: " + stockQuantity);
        }

        PaymentRequest paymentRequest = PaymentRequest.builder()
                // TODO: Implement payment request.
                .build();

        if (paymentService.processPayment(paymentRequest).getStatus() != PaymentStatus.COMPLETED)
            throw new PaymentException("Payment failed");

        OrderRequest orderRequest = OrderRequest.builder()
                .customer(getCustomer())
                .orderDate(LocalDateTime.now())
                .cart(cart)
                .status(OrderStatus.PENDING)
                .shippingAddress(getCustomer().getAddress())
                .build();

        Order order = orderService.createOrder(orderRequest);

        for (Map.Entry<Long, Long> productEntry : cart.getProducts().entrySet()) {
            Long productId = productEntry.getKey();
            Product product = productService.readProduct(productId);
            Long requestedQuantity = productEntry.getValue();
            Long stockQuantity = product.getQuantity();
            Long newQuantity = stockQuantity - requestedQuantity;

            product.setQuantity(newQuantity);

            ProductRequestDTO productRequestDTO = productMapper.toRequest(product);

            productService.updateProduct(product.getId(), productRequestDTO);

            User vendor = product.getVendor();

            if (newQuantity < product.getRestockAlarmQuantity()){
                NotificationRequest notificationRequest = NotificationRequest.builder()
                        .message("Quantity of your product " + product.getName() + "is below the restock level")
                        .type(NotificationType.PRODUCT_RESTOCK_ALARM)
                        .userId(vendor.getId())
                        .build();

                notificationService.createNotification(notificationRequest);

                User customer = getCustomer();
            }
        }

        resetCart();

        return order;
    }
}
