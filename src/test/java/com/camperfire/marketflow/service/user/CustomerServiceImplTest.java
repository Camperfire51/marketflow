package com.camperfire.marketflow.service.user;

import com.camperfire.marketflow.model.Product;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceImplTest {

    @Test
    void testMapIntegrityWhenKeyIsReloaded() {
        // Arrange
        Map<Product, Integer> productMap = new HashMap<>();

        Product product1 = new Product();
        productMap.put(product1, 10);

        // Act: Simulate reloading the product from the database
        Product reloadedProduct = new Product(); // New instance but same ID

        // Assert
        assertTrue(productMap.containsKey(reloadedProduct),
                "Map should recognize reloadedProduct as the same key.");
        assertEquals(10, productMap.get(reloadedProduct),
                "Map should retrieve the correct value for reloadedProduct.");
    }

}