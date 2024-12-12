package com.camperfire.marketflow.service.user;

import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.Order;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.user.Customer;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    Customer getCustomer();

    Cart getCart();

    Product getProduct(Long productId);

    List<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, String category, Long vendorId);

    Cart addProductToCart(Long productId, Long quantity);

    Cart removeProductFromCart(Long productId, Long quantity);

    Cart resetCart();

    Order order();
}
