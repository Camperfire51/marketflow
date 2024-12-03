package com.camperfire.marketflow.service.user;

import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.model.user.Admin;
import com.camperfire.marketflow.service.AuthUserService;
import com.camperfire.marketflow.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AuthUserService authUserService;
    private final ProductService productService;

    @Autowired
    public AdminServiceImpl(AuthUserService authUserService, ProductService productService) {
        this.authUserService = authUserService;
        this.productService = productService;
    }

    @Override
    public Admin getAdmin(){
        return (Admin) authUserService.getAuthUser().getBaseUser();
    }

    @Override
    public Product getProduct(Long productId) {
        return productService.getProduct(productId);
    }

    @Override
    public List<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, String categoryPath, Long vendorId, ProductStatus status) {
        return productService.getProducts(name, minPrice, maxPrice, categoryPath, vendorId, status);
    }

    @Override
    public void setProductStatus(Long productId, ProductStatus status) {
        productService.setProductStatus(productId, status);
    }
}
