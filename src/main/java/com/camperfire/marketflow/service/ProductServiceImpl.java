package com.camperfire.marketflow.service;

import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.dto.request.ProductRequestDTO;
import com.camperfire.marketflow.exception.NotImplementedException;
import com.camperfire.marketflow.exception.ProductNotFoundException;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.repository.ProductRepository;
import com.camperfire.marketflow.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Override
    public void setProductStatus(Long productId, ProductStatus status) {
        Product product = readProduct(productId);
        product.setStatus(status);
        productRepository.save(product);
    }

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
    public Product createProduct(ProductRequestDTO productRequestDTO){
        Product product = productMapper.toEntity(productRequestDTO);
        product.setStatus(ProductStatus.PENDING);
        return productRepository.save(product);
    }

    @Override
    public Product readProduct(Long productId){
        Product product = (Product) redisTemplate.opsForValue().get("product:" + productId);

        if (product != null)
            return product;

        product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id (" + productId + ") was not found"));

        redisTemplate.opsForValue().set("product:" + productId, product, 10, TimeUnit.MINUTES);

        return product;
    }

    @Override
    public Product updateProduct(Long productId, ProductRequestDTO productRequestDTO){
        Product product = productMapper.toEntity(productRequestDTO);
        product.setStatus(ProductStatus.PENDING);
        product.setId(productId);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        throw new NotImplementedException("Hard delete is not supported");

//        if (!productRepository.existsById(productId))
//            throw new ProductNotFoundException("Product with id (" + productId + ") was not found");
//
//        productRepository.deleteById(productId);
//
//        redisTemplate.delete("product:" + productId);
    }
}
