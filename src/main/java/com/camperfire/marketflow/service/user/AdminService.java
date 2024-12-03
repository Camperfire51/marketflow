package com.camperfire.marketflow.service.user;

import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.model.user.Admin;

import java.math.BigDecimal;
import java.util.List;

public interface AdminService {

    Admin getAdmin();

    Product getProduct(Long productId);

    List<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, String categoryPath, Long vendorId, ProductStatus status);

    void setProductStatus(Long productId, ProductStatus status);
}
