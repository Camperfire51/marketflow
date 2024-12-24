package com.camperfire.marketflow.dto.crud.inventory;

import com.camperfire.marketflow.validation.CreateRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequest {

    @NotNull(groups = CreateRequest.class, message = "Product id is required for create request.")
    private Long productId;

    @NotNull(groups = CreateRequest.class, message = "Quantity is required for create request.")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Long quantity;

    @NotNull(groups = CreateRequest.class, message = "Restock alarm quantity is required for create request.")
    @Min(value = 0, message = "Restock alarm quantity cannot be negative")
    private Long restockAlarmQuantity;
}
