package com.camperfire.marketflow.service.product;

import com.camperfire.marketflow.dto.crud.product.ProductRequest;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, String categoryPath, Long vendorId, ProductStatus status);

    void setProductStatus(Long productId, ProductStatus status);

    void setProductDiscount(Long productId, BigDecimal newDiscount);

    Product createProduct(ProductRequest productRequestDTO);

    Product readProduct(Long productId);

    Product updateProduct(ProductRequest productRequestDTO);

    void deleteProduct(Long productId);
}
