package com.camperfire.marketflow.dto.mapper.utility;

import com.camperfire.marketflow.dto.mapper.ProductMapper;
import com.camperfire.marketflow.dto.response.ProductResponseDTO;
import com.camperfire.marketflow.model.Product;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class CartMapperUtility {

    private final ProductMapper productMapper;

    public CartMapperUtility(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Named("calculateTotalBasePrice")
    public BigDecimal calculateTotalBasePrice(Map<Product, Long> products) {
        return products.entrySet().stream()
                .map(entry -> entry.getKey().getBasePrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Named("calculateTotalDiscount")
    public BigDecimal calculateTotalDiscount(Map<Product, Long> products) {
        return products.entrySet().stream()
                .map(entry -> {
                    Product product = entry.getKey();
                    BigDecimal discountAmount = product.getBasePrice()
                            .multiply(product.getDiscountPercentage())
                            .divide(BigDecimal.valueOf(100));
                    return discountAmount.multiply(BigDecimal.valueOf(entry.getValue()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Named("mapProducts")
    public Map<ProductResponseDTO, Long> mapProducts(Map<Product, Long> products) {
        Map<ProductResponseDTO, Long> mappedProducts = new HashMap<>();
        for (Map.Entry<Product, Long> entry : products.entrySet()) {
            mappedProducts.put(productMapper.toResponse(entry.getKey()), entry.getValue());
        }
        return mappedProducts;
    }
}
