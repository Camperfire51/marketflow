package com.camperfire.marketflow.service;

import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.dto.request.ProductRequestDTO;
import com.camperfire.marketflow.exception.InvalidProductParameterException;
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
    public Product getProduct(Long productId) {
        Product cachedProduct = getProductFromCache(productId);

        if (cachedProduct != null)
            return cachedProduct;

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id (" + productId + ") was not found"));

        saveProductToCache(productId, product);

        if (product.getStatus() != ProductStatus.PUBLISHED)
            throw new ProductNotFoundException("Product with id (" + productId + ") was not published");

        return product;
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
    public Product submitProduct(ProductRequestDTO productRequestDTO) {

        if (productRequestDTO.getName() == null || productRequestDTO.getName().isEmpty())
            throw new InvalidProductParameterException("Name cannot be empty");

        if (productRequestDTO.getBasePrice() == null || productRequestDTO.getBasePrice().compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidProductParameterException("Base price cannot be negative");

        if (productRequestDTO.getDiscountPercentage() == null || productRequestDTO.getDiscountPercentage().compareTo(BigDecimal.ZERO) < 0 ||
                productRequestDTO.getDiscountPercentage().compareTo(new BigDecimal(100)) > 0)
            throw new InvalidProductParameterException("Discount percentage must be an integer between 0 and 100");

        if (productRequestDTO.getDescription() == null || productRequestDTO.getDescription().isEmpty())
            throw new InvalidProductParameterException("Description cannot be empty");

        if (productRequestDTO.getQuantity() == null || productRequestDTO.getQuantity() < 0)
            throw new InvalidProductParameterException("Quantity cannot be negative");

        Product product = productMapper.toEntity(productRequestDTO);
        product.setStatus(ProductStatus.PENDING);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product modifyProduct(Long productId, ProductRequestDTO productRequestDTO) {
        if (!productRepository.existsById(productId))
            throw new ProductNotFoundException("Product with id (" + productId + ") was not found");

        Product product = productMapper.toEntity(productRequestDTO);
        product.setId(productId);
        product.setStatus(ProductStatus.PENDING);
        productRepository.save(product);
        return product;
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId))
            throw new ProductNotFoundException("Product with id (" + productId + ") was not found");

        productRepository.deleteById(productId);

        removeProductFromCache(productId);
    }

    @Override
    public Product setProductStatus(Long productId, ProductStatus status) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id (" + productId + ") was not found"));

        product.setStatus(status);
        productRepository.save(product);
        return product;
    }

    @Override
    public void setProductDiscount(Long productId, BigDecimal newDiscount) {
        Product product = getProduct(productId);

        product.setDiscountPercentage(newDiscount);

        productRepository.save(product);
    }

    public void saveProductToCache(Long productId, Product product) {
        redisTemplate.opsForValue().set("product:" + productId, product, 10, TimeUnit.MINUTES);
    }

    public Product getProductFromCache(Long productId) {
        return (Product) redisTemplate.opsForValue().get("product:" + productId);
    }

    public void removeProductFromCache(Long productId) {
        redisTemplate.delete("product:" + productId);
    }
}
