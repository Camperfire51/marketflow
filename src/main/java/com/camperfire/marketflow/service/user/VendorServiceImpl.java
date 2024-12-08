package com.camperfire.marketflow.service.user;

import com.camperfire.marketflow.dto.request.ProductRequestDTO;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.model.user.Vendor;
import com.camperfire.marketflow.service.AuthUserService;
import com.camperfire.marketflow.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    private final ProductService productService;
    private final AuthUserService authUserService;

    public VendorServiceImpl(ProductService productService, AuthUserService authUserService) {
        this.productService = productService;
        this.authUserService = authUserService;
    }

    @Override
    public Vendor getVendor() { return (Vendor) authUserService.getAuthUser().getBaseUser(); }

    @Override
    public Product getProduct(Long productId) {
        return productService.getProduct(productId);
    }

    @Override
    public List<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, String categoryPath, Long vendorId) {
        return productService.getProducts(name, minPrice, maxPrice, categoryPath, vendorId, ProductStatus.PUBLISHED);
    }

    @Override
    public List<Product> getProductRequests(String name, BigDecimal minPrice, BigDecimal maxPrice, String categoryPath) {
        Long vendorId = getVendor().getId();
        return productService.getProducts(name, minPrice, maxPrice, categoryPath, vendorId, ProductStatus.PENDING);
    }

    @Override
    public Product submitProduct(ProductRequestDTO productRequestDTO) {
        return productService.submitProduct(productRequestDTO);
    }

    @Override
    public Product modifyProduct(Long productId, ProductRequestDTO productRequestDTO) {
        return productService.modifyProduct(productId, productRequestDTO);
    }

    @Override
    public void deleteProduct(Long productId) {
        productService.deleteProduct(productId);
    }

    @Override
    public void setProductDiscount(Long productId, BigDecimal newDiscount){
        productService.setProductDiscount(productId, newDiscount);
    }

    @Override
    public void getRestockAlarms() {

    }
}
