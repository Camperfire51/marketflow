package com.camperfire.marketflow.dto.mapper.utility;

import com.camperfire.marketflow.model.user.Customer;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.repository.user.CustomerRepository;
import com.camperfire.marketflow.repository.ProductRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderMapperUtility {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderMapperUtility(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Named("mapCustomer")
    public Customer mapCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Named("mapProducts")
    public Map<Product, Long> mapProducts(Map<Long, Long> productQuantities) {
        Map<Product, Long> products = new HashMap<>();
        for (Map.Entry<Long, Long> entry : productQuantities.entrySet()) {
            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            products.put(product, entry.getValue());
        }
        return products;
    }

    @Named("mapProductQuantities")
    public Map<Long, Long> mapProductQuantities(Map<Product, Long> products) {
        Map<Long, Long> productQuantities = new HashMap<>();
        for (Map.Entry<Product, Long> entry : products.entrySet()) {
            productQuantities.put(entry.getKey().getId(), entry.getValue());
        }
        return productQuantities;
    }
}
