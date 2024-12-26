package com.camperfire.marketflow.service.product;

import com.camperfire.marketflow.dto.crud.product.ProductRequest;
import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.model.UserPrincipal;
import com.camperfire.marketflow.model.user.Vendor;
import com.camperfire.marketflow.repository.ProductRepository;
import com.camperfire.marketflow.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, RedisTemplate<String, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<Product> getProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, String categoryPath, Long vendorId, ProductStatus status) {
        Specification<Product> spec = Specification.where(null);

        //TODO: Instead of if chain, use JPA queries.

        // Add name filter if provided
        if (name != null && !name.isEmpty()) {
            spec = spec.and(ProductSpecification.hasName(name));
        }

        // Add category path filter if provided
        if (categoryPath != null && !categoryPath.isEmpty()) {
            spec = spec.and(ProductSpecification.hasCategoryPath(categoryPath));
        }

        // Add minPrice filter if provided
        if (minPrice != null) {
            spec = spec.and(ProductSpecification.hasPriceGreaterThanOrEqual(minPrice));
        }

        // Add maxPrice filter if provided
        if (maxPrice != null) {
            spec = spec.and(ProductSpecification.hasPriceLessThanOrEqual(maxPrice));
        }

        // Add vendor filter if provided
        if (vendorId != null) {
            spec = spec.and(ProductSpecification.hasVendorById(vendorId));
        }

        if (status != null) {
            spec = spec.and(ProductSpecification.hasStatus(status));
        }

        return productRepository.findAll(spec);
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
