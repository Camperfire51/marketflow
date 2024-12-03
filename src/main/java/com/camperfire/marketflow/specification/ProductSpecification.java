package com.camperfire.marketflow.specification;

import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> hasCategoryPath(String categoryName) {
        return (root, query, criteriaBuilder) ->
                categoryName == null ? null : criteriaBuilder.like(root.get("category").get("path"), "%" + categoryName + "%");
    }

    public static Specification<Product> hasPriceGreaterThanOrEqual(BigDecimal minPrice) {
        return (root, query, criteriaBuilder) ->
                minPrice == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> hasPriceLessThanOrEqual(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) ->
                maxPrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> hasVendorById(Long vendorId) {
        return (root, query, criteriaBuilder) ->
                vendorId == null ? null : criteriaBuilder.equal(root.get("vendor").get("id"), vendorId);
    }

    public static Specification<Product> hasStatus(ProductStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }
}
