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
    private BigDecimal totalBasePrice;
    private BigDecimal totalDiscount;
    private Map<ProductResponseDTO, Long> products;
}
