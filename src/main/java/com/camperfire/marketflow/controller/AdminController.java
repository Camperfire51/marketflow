package com.camperfire.marketflow.controller;

import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.dto.response.ProductResponseDTO;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.service.ProductServiceImpl;
import com.camperfire.marketflow.service.user.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    private final ProductMapper productMapper;

    @Autowired
    public AdminController(AdminService adminService, ProductMapper productMapper) {
        this.adminService = adminService;
        this.productMapper = productMapper;
    }

    @GetMapping("/product")
    public ResponseEntity<ProductResponseDTO> getProduct(@RequestParam(value = "id") Long id) {
        Product product = adminService.getProduct(id);
        return ResponseEntity.ok(productMapper.toResponse(product));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> getProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "vendorId", required = false) Long vendorId,
            @RequestParam(value = "status", required = false) ProductStatus status) {

        List<Product> products = adminService.getProducts(name, minPrice, maxPrice, category, vendorId, status);

        return ResponseEntity.ok(productMapper.toResponseList(products));
    }

    @PostMapping("/products")
    public ResponseEntity<Void> setProductStatus(
            @RequestBody Long productId,
            @RequestParam(value = "status") ProductStatus status) {

        adminService.setProductStatus(productId, status);

        return ResponseEntity.ok().build();
    }
}
