package com.camperfire.marketflow.controller;

import com.camperfire.marketflow.dto.mapper.CartMapper;
import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.dto.response.CartResponseDTO;
import com.camperfire.marketflow.dto.response.ProductResponseDTO;
import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.CustomerOrder;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.service.user.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    private final CartMapper cartMapper;
    private final ProductMapper productMapper;

    @Autowired
    public CustomerController(CustomerService customerService, CartMapper cartMapper, ProductMapper productMapper) {
        this.customerService = customerService;
        this.cartMapper = cartMapper;
        this.productMapper = productMapper;
    }

    @GetMapping("/cart")
    public ResponseEntity<CartResponseDTO> getCart() {
        Cart cart = customerService.getCart();
        return ResponseEntity.ok(cartMapper.toResponse(cart));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> getProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "vendorId", required = false) Long vendorId) {

        List<Product> products = customerService.getProducts(name, minPrice, maxPrice, category, vendorId);

        return ResponseEntity.ok(productMapper.toResponseList(products));
    }

    @PostMapping("/cart")
    public ResponseEntity<CartResponseDTO> addProductToCart(
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "quantity") Long quantity) {

        customerService.addProductToCart(productId, quantity);

        Cart cart = customerService.getCart();

        return ResponseEntity.ok(cartMapper.toResponse(cart));
    }

    @DeleteMapping("/cart")
    public ResponseEntity<CartResponseDTO> removeProductFromCart(
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "quantity") Long quantity) {

        customerService.removeProductFromCart(productId, quantity);

        Cart cart = customerService.getCart();

        return ResponseEntity.ok(cartMapper.toResponse(cart));
    }

    @PutMapping("/reset-cart")
    public ResponseEntity<CartResponseDTO> resetCart() {
        customerService.resetCart();

        Cart cart = customerService.getCart();

        return ResponseEntity.ok(cartMapper.toResponse(cart));
    }

    @PostMapping("/order")
    public ResponseEntity<String> submitOrder() {
        CustomerOrder customerOrder = customerService.submitOrder();

        return ResponseEntity.ok("TODO, IMPLEMENT ORDER DTO");
    }
}
