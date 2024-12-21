package com.camperfire.marketflow;

import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.Category;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.repository.CategoryRepository;
import com.camperfire.marketflow.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class MapIntegrityTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testMapIntegrityAfterEntityReload() {
        // Step 1: Create and save a Product
        Product product = new Product();
        product.setName("Test Product");
        product.setBasePrice(BigDecimal.valueOf(100.0));

        Product savedProduct = productRepository.save(product);

        // Step 2: Create a Map with the Product as a key
        Cart cart = new Cart();
        cart.getProducts().put(1L, 10L);

        // Step 3: Reload the Product from the database
        Product reloadedProduct = productRepository.findById(savedProduct.getId()).orElse(null);

        // Step 4: Verify map integrity (using reloaded entity as the key)
        Long quantity = cart.getProducts().get(reloadedProduct);

        // Assertion: Check that the quantity is correctly retrieved
        assertNotNull(quantity, "The map should retrieve the correct quantity for the reloaded product.");
        assertEquals(10L, quantity, "The quantity for the product should remain consistent after reloading.");
    }
}
