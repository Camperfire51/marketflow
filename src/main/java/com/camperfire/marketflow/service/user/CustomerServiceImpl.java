package com.camperfire.marketflow.service.user;

import com.camperfire.marketflow.exception.NotEnoughProductQuantityException;
import com.camperfire.marketflow.exception.ProductNotFoundException;
import com.camperfire.marketflow.exception.ProductOutOfStocksException;
import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.CustomerOrder;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.model.user.Customer;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.repository.CartRepository;
import com.camperfire.marketflow.repository.user.CustomerRepository;
import com.camperfire.marketflow.service.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final AuthUserService authUserService;

    private final ProductService productService;
    private final OrderService orderService;
    private final CartRepository cartRepository;

    public CustomerServiceImpl(AuthUserService authUserService, ProductService productService, OrderService orderService, CartRepository cartRepository) {
        this.authUserService = authUserService;

        this.productService = productService;
        this.orderService = orderService;
        this.cartRepository = cartRepository;
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
        Product product = productService.getProduct(productId);
        return productService.getProduct(productId);
    }

    @Override
    public List<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, String category, Long vendorId){
        return productService.getProducts(name, minPrice, maxPrice, category, vendorId, ProductStatus.PUBLISHED);
    }

    @Override
    public Cart addProductToCart(Long productId, Long quantity) {
        Product product = productService.getProduct(productId);

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
        Product product = productService.getProduct(productId);

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
    public CustomerOrder order() {
        Cart cart = getCart();
        CustomerOrder order = orderService.order(cart);
        resetCart();
        return order;
    }
}
