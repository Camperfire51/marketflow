package com.camperfire.marketflow.service;

import com.camperfire.marketflow.dto.request.ProductRequestDTO;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, String categoryPath, Long vendorId, ProductStatus status);

    void setProductStatus(Long productId, ProductStatus status);

    void setProductDiscount(Long productId, BigDecimal newDiscount);

    Product createProduct(ProductRequestDTO productRequestDTO);

    Product readProduct(Long productId);

    Product updateProduct(Long productId, ProductRequestDTO productRequestDTO);

    void deleteProduct(Long productId);
}
