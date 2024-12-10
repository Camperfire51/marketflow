package com.camperfire.marketflow.service.user;

import com.camperfire.marketflow.dto.request.ProductRequestDTO;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.model.user.Vendor;

import java.math.BigDecimal;
import java.util.List;

public interface VendorService {
    Vendor getVendor();

    Product readProduct(Long productId);

    List<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, String categoryPath, Long vendorId, ProductStatus status);

    Product createProduct(ProductRequestDTO productRequestDTO);

    Product updateProduct(Long productId, ProductRequestDTO productRequestDTO);

    void deleteProduct(Long productId);

    void setProductDiscount(Long productId, BigDecimal newDiscount);

    void getRestockAlarms();
}
