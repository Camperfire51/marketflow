package com.camperfire.marketflow.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDTO {
    private BigDecimal totalBasePrice; // Total price without discounts
    private BigDecimal totalDiscountedPrice; // Total price with discounts applied
    private Map<ProductResponseDTO, Long> cartItems; // List of cart items
}
