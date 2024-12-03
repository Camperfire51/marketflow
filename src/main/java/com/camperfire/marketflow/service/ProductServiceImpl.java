package com.camperfire.marketflow.service;

import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.dto.request.ProductRequestDTO;
import com.camperfire.marketflow.exception.ProductNotFoundException;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import com.camperfire.marketflow.model.user.Vendor;
import com.camperfire.marketflow.repository.ProductRepository;
import com.camperfire.marketflow.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Product getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id (" + productId + ") was not found"));

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
        Product product = productMapper.toEntity(productRequestDTO);
        product.setStatus(ProductStatus.PENDING);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product modifyProduct(Long productId, ProductRequestDTO productRequestDTO) {
        if(!productRepository.existsById(productId))
            throw new ProductNotFoundException("Product with id (" + productId + ") was not found");

        Product product = productMapper.toEntity(productRequestDTO);
        product.setId(productId);
        product.setStatus(ProductStatus.PENDING);
        productRepository.save(product);
        return product;
    }

    @Override
    public void deleteProduct(Long productId) {
        if(!productRepository.existsById(productId))
            throw new ProductNotFoundException("Product with id (" + productId + ") was not found");

        productRepository.deleteById(productId);
    }

    @Override
    public Product setProductStatus(Long productId, ProductStatus status) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id (" + productId + ") was not found"));

        product.setStatus(status);
        productRepository.save(product);
        return product;
    }
}
