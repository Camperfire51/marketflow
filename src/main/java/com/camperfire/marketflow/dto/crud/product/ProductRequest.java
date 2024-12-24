package com.camperfire.marketflow.dto.crud.product;

import com.camperfire.marketflow.validation.CreateRequest;
import com.camperfire.marketflow.validation.UpdateRequest;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    @Null(groups = CreateRequest.class, message = "ID is not required for create request")
    @NotNull(groups = UpdateRequest.class, message = "ID is required for update request.")
    @Positive(message = "ID must be positive.")
    private Long id;

    @NotNull(groups = CreateRequest.class, message = "Name is required for create request.")
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 10, max = 255)
    private String name;

    @NotNull(groups = CreateRequest.class, message = "Base price is required")
    @DecimalMin(value = "0.0", groups = CreateRequest.class, message = "Base price cannot be negative")
    @DecimalMax(value = "99999.99", message = "Base price cannot be more than 10.000")
    private BigDecimal basePrice;

    @NotNull(groups = CreateRequest.class, message = "Discount percentage is required")
    @DecimalMin(value = "0.0", message = "Discount percentage must be at least 0")
    @DecimalMax(value = "100.0", message = "Discount percentage must be at most 100")
    private BigDecimal discountPercentage;

    @NotNull(groups = CreateRequest.class, message = "Discount percentage is required")
    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotNull(groups = CreateRequest.class, message = "Category ID is required")
    @Min(value = 1, message = "Category id must be greater than 0")
    private Long categoryId;

    @NotNull(groups = CreateRequest.class, message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Long quantity;

    @NotNull(groups = CreateRequest.class, message = "Restock alarm quantity is required")
    @Min(value = 0, message = "Restock alarm quantity cannot be negative")
    private Long restockAlarmQuantity;
}
