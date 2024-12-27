package com.camperfire.marketflow.repository;

import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT p 
            FROM Product p
            WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
              AND (:categoryPath IS NULL OR p.category.fullName = :categoryPath)
              AND (:minPrice IS NULL OR p.basePrice >= :minPrice)
              AND (:maxPrice IS NULL OR p.basePrice <= :maxPrice)
              AND (:vendorId IS NULL OR p.vendor.id = :vendorId)
              AND (:status IS NULL OR p.status = :status)
            """)
    List<Product> findProducts(
            @Param("name") String name,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("categoryPath") String categoryPath,
            @Param("vendorId") Long vendorId,
            @Param("status") ProductStatus status
    );

}
