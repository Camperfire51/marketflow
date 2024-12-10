package com.camperfire.marketflow.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Base price cannot be negative")
    private BigDecimal basePrice;

    @NotNull(message = "Discount percentage is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Discount percentage must be at least 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Discount percentage must be at most 100")
    private BigDecimal discountPercentage;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Vendor ID is required")
    private Long vendorId;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Long quantity;

    @NotNull(message = "Restock alarm quantity is required")
    @Min(value = 0, message = "Restock alarm quantity cannot be negative")
    private Long restockAlarmQuantity;
}