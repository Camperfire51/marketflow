package com.camperfire.marketflow.controller;

import com.camperfire.marketflow.dto.crud.cart.CartResponse;
import com.camperfire.marketflow.dto.crud.order.OrderResponse;
import com.camperfire.marketflow.dto.crud.product.ProductResponse;
import com.camperfire.marketflow.dto.mapper.CartMapper;
import com.camperfire.marketflow.dto.mapper.OrderMapper;
import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.Order;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.service.cart.CartService;
import com.camperfire.marketflow.service.order.OrderService;
import com.camperfire.marketflow.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;

    @GetMapping("/product")
    public ResponseEntity<ProductResponse> getProduct(@RequestParam(value = "name") Long id) {
        productService.readProduct(id); //TODO: Users shouldn't be able to see any non-published products.
        return ResponseEntity.ok(productMapper.toResponse(productService.readProduct(id)));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "vendorId", required = false) Long vendorId) {

        List<Product> products = productService.getProducts(name, minPrice, maxPrice, category, vendorId, ProductStatus.PUBLISHED);
        return ResponseEntity.ok(productMapper.toResponseList(products));
    }

    @GetMapping("/cart")
    public ResponseEntity<CartResponse> getCart() {
        Cart cart = cartService.getAuthenticatedCart();
        return ResponseEntity.ok(cartMapper.toResponse(cart));
    }

    @PostMapping("/cart")
    public ResponseEntity<CartResponse> addProductToCart(
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "quantity") Long quantity) {

        Cart cart = cartService.addProductToCart(productId, quantity);
        return ResponseEntity.ok(cartMapper.toResponse(cart));
    }

    @PutMapping("/reset-cart")
    public ResponseEntity<Void> resetCart() {

        cartService.resetCart();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cart")
    public ResponseEntity<CartResponse> removeProductFromCart(
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "quantity") Long quantity) {

        Cart cart = cartService.removeProductFromCart(productId, quantity);
        return ResponseEntity.ok(cartMapper.toResponse(cart));
    }

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> submitOrder() {
        Order order = orderService.submitOrder();
        return ResponseEntity.ok(orderMapper.toResponse(order));
    }
}
