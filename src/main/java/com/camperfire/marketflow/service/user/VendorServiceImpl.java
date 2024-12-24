package com.camperfire.marketflow.service.user;

import com.camperfire.marketflow.dto.request.ProductRequestDTO;
import com.camperfire.marketflow.exception.UnauthorizedException;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.model.user.Vendor;
import com.camperfire.marketflow.service.AuthUserService;
import com.camperfire.marketflow.service.product.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class VendorServiceImpl implements VendorService {

    private final ProductService productService;
    private final AuthUserService authUserService;

    public VendorServiceImpl(ProductService productService, AuthUserService authUserService) {
        this.productService = productService;
        this.authUserService = authUserService;
    }

    @Override
    public Vendor getVendor() { return (Vendor) authUserService.getAuthUser().getUser(); }

    @Override
    public List<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, String categoryPath, Long vendorId, ProductStatus status) {

        if (status != ProductStatus.PUBLISHED && !Objects.equals(vendorId, getVendor().getId()))
            throw new UnauthorizedException("Unauthorized");

        return productService.getProducts(name, minPrice, maxPrice, categoryPath, vendorId, status);
    }

    @Override
    public void setProductDiscount(Long productId, BigDecimal newDiscount){
        Product product = productService.readProduct(productId);

        if (product.getStatus() != ProductStatus.PUBLISHED && product.getVendor() != getVendor())
            throw new UnauthorizedException("Unauthorized");

        productService.setProductDiscount(productId, newDiscount);
    }

    // Basic CRUD

    @Override
    public Product createProduct(ProductRequestDTO productRequestDTO) {
        return productService.createProduct(productRequestDTO);
    }

    @Override
    public Product readProduct(Long productId) {
        Product product = productService.readProduct(productId);

        if (product.getStatus() != ProductStatus.PUBLISHED && product.getVendor() != getVendor())
            throw new UnauthorizedException("Unauthorized");

        return product;
    }

    @Override
    public Product updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        Product product = productService.readProduct(productId);

        if (product.getStatus() != ProductStatus.PUBLISHED && product.getVendor() != getVendor())
            throw new UnauthorizedException("Unauthorized");

        return productService.updateProduct(productId, productRequestDTO);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productService.readProduct(productId);

        if (product.getStatus() != ProductStatus.PUBLISHED && product.getVendor() != getVendor())
            throw new UnauthorizedException("Unauthorized");

        productService.setProductStatus(productId, ProductStatus.DELETED); // Soft delete
    }

    @Override
    public void getRestockAlarms() {

    }
}
