package com.camperfire.marketflow.controller;

import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.dto.request.ProductRequestDTO;
import com.camperfire.marketflow.dto.response.ProductResponseDTO;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.service.user.VendorService;
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

    private final VendorService vendorService;
    private final ProductMapper productMapper;

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> getProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "vendorId", required = false) Long vendorId,
            @RequestParam(value = "productStatus", required = false) ProductStatus productStatus) {

        List<Product> products = vendorService.getProducts(name, minPrice, maxPrice, category, vendorId, productStatus);

        return ResponseEntity.ok(productMapper.toResponseList(products));
    }

    @PutMapping("/product-discount")
    public ResponseEntity<Void> setProductDiscount(
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "newAmount") BigDecimal newAmount) {

        vendorService.setProductDiscount(productId, newAmount);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<ProductResponseDTO>> getProductRestockAlarms() {
        vendorService.getRestockAlarms();
        return null; //TODO: Imple
    }

    // Basic CRUD for product

    @GetMapping("/product") // Read
    public ResponseEntity<ProductResponseDTO> getProduct(@RequestParam(value = "id") Long id) {
        Product product = vendorService.readProduct(id);
        return ResponseEntity.ok(productMapper.toResponse(product));
    }

    @PostMapping("/product") // Create
    public ResponseEntity<ProductResponseDTO> submitProduct(
            @RequestBody ProductRequestDTO productRequestDTO) {
        Product product = vendorService.createProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productMapper.toResponse(product));
    }

    @PutMapping("/product") // Update
    public ResponseEntity<ProductResponseDTO> modifyProduct(
            @RequestParam(value = "productId") Long productId,
            @RequestBody ProductRequestDTO productRequestDTO) {
        Product product = vendorService.updateProduct(productId, productRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productMapper.toResponse(product));
    }

    @DeleteMapping("/product") // Delete
    public ResponseEntity<ProductResponseDTO> deleteProduct(@RequestParam(value = "productId") Long productId) {
        vendorService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(ProductResponseDTO.builder().build());
    }
}
