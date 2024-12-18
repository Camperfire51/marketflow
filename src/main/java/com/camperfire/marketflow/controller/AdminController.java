package com.camperfire.marketflow.controller;

import com.camperfire.marketflow.dto.mapper.CategoryMapper;
import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.dto.request.CategoryRequestDTO;
import com.camperfire.marketflow.dto.request.NotificationRequest;
import com.camperfire.marketflow.dto.response.CategoryResponseDTO;
import com.camperfire.marketflow.dto.response.NotificationResponse;
import com.camperfire.marketflow.dto.response.ProductResponseDTO;
import com.camperfire.marketflow.model.Category;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.service.notification.NotificationService;
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

    private final CategoryMapper categoryMapper;

    @Autowired
    public AdminController(AdminService adminService, ProductMapper productMapper, CategoryMapper categoryMapper) {
        this.adminService = adminService;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
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

    @PutMapping("/product-status")
    public ResponseEntity<String> setProductStatus(
            @RequestParam Long productId,
            @RequestParam(value = "status") ProductStatus status) {

        adminService.setProductStatus(productId, status);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryResponseDTO>> getCategories() {
        List<Category> categories = adminService.getCategories();

        return ResponseEntity.ok(categoryMapper.toResponseList(categories));
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        Category category = adminService.createCategory(categoryRequestDTO);

        return ResponseEntity.ok(categoryMapper.toResponse(category));
    }

    @DeleteMapping("/category")
    public ResponseEntity<Void> deleteCategory(@RequestParam(name = "categoryId") Long categoryId){
        adminService.deleteCategory(categoryId);

        return ResponseEntity.ok().build();
    }




}
