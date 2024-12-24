package com.camperfire.marketflow;

import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.repository.ProductRepository;
import com.camperfire.marketflow.service.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

@SpringBootTest(classes = {ProductService.class, ProductRepository.class})
@ActiveProfiles("test") // Use a profile with test-specific settings
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductServicePerformanceTest {

    @Autowired
    private ProductService productService;

    @BeforeEach
    void setUp(@Autowired ProductRepository productRepository) {
        // Seed test data
        Product product = new Product();
        product.setName("Test Product");
        product.setBasePrice(BigDecimal.valueOf(100));
        productRepository.save(product);
    }

    @Test
    void testPerformanceWithAndWithoutCache() {
        Long productId = 1L;

        // First call - without cache (simulate database hit)
        long startWithoutCache = System.currentTimeMillis();
        productService.readProduct(productId);
        long endWithoutCache = System.currentTimeMillis();
        System.out.println("Without Cache: " + (endWithoutCache - startWithoutCache) + "ms");

        // Subsequent call - with cache
        long startWithCache = System.currentTimeMillis();
        productService.readProduct(productId);
        long endWithCache = System.currentTimeMillis();
        System.out.println("With Cache: " + (endWithCache - startWithCache) + "ms");

        assertTrue((endWithoutCache - startWithoutCache) > (endWithCache - startWithCache),
                "Cache should significantly reduce the retrieval time");
    }
}
