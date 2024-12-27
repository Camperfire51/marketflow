package com.camperfire.marketflow.controller;

import com.camperfire.marketflow.dto.crud.notification.NotificationResponse;
import com.camperfire.marketflow.dto.crud.product.ProductRequest;
import com.camperfire.marketflow.dto.crud.product.ProductResponse;
import com.camperfire.marketflow.dto.mapper.NotificationMapper;
import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.model.Notification;
import com.camperfire.marketflow.model.NotificationType;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.service.notification.NotificationService;
import com.camperfire.marketflow.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/vendor")
public class VendorController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @GetMapping("/product") // Read
    public ResponseEntity<ProductResponse> getProduct(@RequestParam(value = "id") Long id) {
        Product product = productService.readProduct(id);
        return ResponseEntity.ok(productMapper.toResponse(product));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "vendorId", required = false) Long vendorId,
            @RequestParam(value = "productStatus", required = false) ProductStatus productStatus) {

        List<Product> products = productService.getProducts(name, minPrice, maxPrice, category, vendorId, productStatus);
        //TODO: Vendor's shouldn't be able to see non-published product which don't belong to them.

        return ResponseEntity.ok(productMapper.toResponseList(products));
    }

    @PostMapping("/product")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        Product product = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.OK).body(productMapper.toResponse(product));
    }

    @PutMapping("/product")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest request) {
        Product product = productService.updateProduct(request);
        return ResponseEntity.status(HttpStatus.OK).body(productMapper.toResponse(product));
    }

    @DeleteMapping("/product")
    public ResponseEntity<ProductResponse> deleteProduct(@RequestParam(value = "id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/product-discount")
    public ResponseEntity<Void> setProductDiscount(
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "newAmount") BigDecimal newAmount) {
        productService.setProductDiscount(productId, newAmount);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationResponse>> getProductRestockAlarms(
            @RequestParam(value = "notificationType") NotificationType notificationType,
            @RequestParam(value = "isRead") boolean isRead
    ) {
        List<Notification> notifications = notificationService.getAuthenticatedNotifications(notificationType, isRead);

        return ResponseEntity.ok(notificationMapper.toResponseList(notifications));
    }
}
