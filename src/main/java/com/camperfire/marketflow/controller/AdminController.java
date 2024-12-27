package com.camperfire.marketflow.controller;

import com.camperfire.marketflow.dto.crud.category.CategoryRequest;
import com.camperfire.marketflow.dto.crud.category.CategoryResponse;
import com.camperfire.marketflow.dto.crud.product.ProductResponse;
import com.camperfire.marketflow.dto.mapper.CategoryMapper;
import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.model.Category;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.service.category.CategoryService;
import com.camperfire.marketflow.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    @GetMapping("/product")
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
            @RequestParam(value = "status", required = false) ProductStatus status) {

        List<Product> products = productService.getProducts(name, minPrice, maxPrice, category, vendorId, status);

        return ResponseEntity.ok(productMapper.toResponseList(products));
    }

    @PutMapping("/product-status")
    public ResponseEntity<String> setProductStatus(
            @RequestParam Long productId,
            @RequestParam(value = "status") ProductStatus status) {

        productService.setProductStatus(productId, status);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        List<Category> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(categoryMapper.toResponseList(categories));
    }

    @GetMapping("/category")
    public ResponseEntity<CategoryResponse> getCategory(@RequestParam(value = "id") Long id) {
        Category category = categoryService.readCategory(id);

        return ResponseEntity.ok(categoryMapper.toResponse(category));
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest){
        Category category = categoryService.createCategory(categoryRequest);

        return ResponseEntity.ok(categoryMapper.toResponse(category));
    }

    @PutMapping("/category")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest categoryRequest){
        Category category = categoryService.updateCategory(categoryRequest);

        return ResponseEntity.ok(categoryMapper.toResponse(category));
    }

    @DeleteMapping("/category")
    public ResponseEntity<Void> deleteCategory(@RequestParam(name = "categoryId") Long categoryId){
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok().build();
    }
}
