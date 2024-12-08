package com.camperfire.marketflow.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {
    private String name;
    private BigDecimal basePrice;
    private BigDecimal discountPercentage;
    private String description;
    private Long categoryId;
    private Long vendorId;
    private Long quantity;
    private Long restockAlarmQuantity;
}