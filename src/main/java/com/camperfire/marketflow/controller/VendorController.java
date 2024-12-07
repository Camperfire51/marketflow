package com.camperfire.marketflow.controller;

import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.dto.request.ProductRequestDTO;
import com.camperfire.marketflow.dto.response.ProductResponseDTO;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.service.user.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    private final VendorService vendorService;
    private final ProductMapper productMapper;

    @Autowired
    public VendorController(VendorService vendorService, ProductMapper productMapper) {
        this.vendorService = vendorService;
        this.productMapper = productMapper;
    }

    @GetMapping("/product")
    public ResponseEntity<ProductResponseDTO> getProductById(@RequestParam(value = "id") Long id) {
        Product product = vendorService.getProduct(id);
        return ResponseEntity.ok(productMapper.toResponse(product));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> getProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "vendorId", required = false) Long vendorId) {

        List<Product> products = vendorService.getProducts(name, minPrice, maxPrice, category, vendorId);

        return ResponseEntity.ok(productMapper.toResponseList(products));
    }

    @GetMapping("/product-requests")
    public ResponseEntity<List<ProductResponseDTO>> getProductRequests(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "category", required = false) String category) {

        List<Product> products = vendorService.getProductRequests(name, minPrice, maxPrice, category);

        return ResponseEntity.ok(productMapper.toResponseList(products));
    }

    @PostMapping("/product")
    public ResponseEntity<ProductResponseDTO> submitProduct(
            @RequestBody ProductRequestDTO productRequestDTO) {
        Product product = vendorService.submitProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productMapper.toResponse(product));
    }

    @PutMapping("/product")
    public ResponseEntity<ProductResponseDTO> modifyProduct(
            @RequestParam(value = "productId") Long productId,
            @RequestBody ProductRequestDTO productRequestDTO) {
        Product product = vendorService.modifyProduct(productId, productRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productMapper.toResponse(product));
    }

    @DeleteMapping("/product")
    public ResponseEntity<ProductResponseDTO> deleteProduct(@RequestParam(value = "productId") Long productId) {
        vendorService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(ProductResponseDTO.builder().build());
    }

    @PutMapping("/set-product-discount")
    public ResponseEntity<Void> setProductDiscount(
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "newAmount") BigDecimal newAmount){

        vendorService.setProductDiscount(productId, newAmount);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
