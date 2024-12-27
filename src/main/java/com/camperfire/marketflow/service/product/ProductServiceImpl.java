package com.camperfire.marketflow.service.product;

import com.camperfire.marketflow.dto.crud.product.ProductRequest;
import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.model.UserPrincipal;
import com.camperfire.marketflow.model.user.Vendor;
import com.camperfire.marketflow.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, String categoryPath, Long vendorId, ProductStatus status) {
        //TODO: Implement security mechanism for vendors to be able to see only their pending/rejected/removed products and customers to see only published products.
        return productRepository.findProducts(name, minPrice, maxPrice, categoryPath, vendorId, status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void setProductStatus(Long productId, ProductStatus status) {
        Product product = readProduct(productId);
        product.setStatus(status);
        productRepository.save(product);
    }

    @PreAuthorize("hasRole('VENDOR')")
    @Override
    public void setProductDiscount(Long productId, BigDecimal newDiscount) {
        Product product = readProduct(productId);

        product.setDiscountPercentage(newDiscount);

        productRepository.save(product);
    }

    // CRUD operations with database and cache considerations
    // Note that, product deletion is not implemented. Instead, we set the status of the product to "DELETED".
    // This way, it is "soft deleted", and can be undone by the authority.

    @Override
    public Product createProduct(ProductRequest request){

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Vendor vendor = (Vendor) userPrincipal.getAuthUser().getUser();

        Product product = productMapper.toEntity(request);

        product.setStatus(ProductStatus.PENDING);

        product.setVendor(vendor);

        return productRepository.save(product);
    }

    @Cacheable(value = "products", key = "#id")
    @Override
    public Product readProduct(Long id){
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public Product updateProduct(ProductRequest request){
        Product product = productRepository.findById(request.getId()).orElseThrow();

        //TODO: Implement update logic.
        product.setStatus(ProductStatus.PENDING);
        //TODO: Implement update logic.

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow();

        productRepository.delete(product);
    }
}
