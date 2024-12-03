package com.camperfire.marketflow.service;

import com.camperfire.marketflow.dto.request.ProductRequestDTO;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    Product getProduct(Long productId);

    List<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, String categoryPath, Long vendorId, ProductStatus status);

    Product submitProduct(ProductRequestDTO productRequestDTO);

    Product modifyProduct(Long productId, ProductRequestDTO productRequestDTO);

    void deleteProduct(Long id);

    Product setProductStatus(Long productId, ProductStatus status);
}
