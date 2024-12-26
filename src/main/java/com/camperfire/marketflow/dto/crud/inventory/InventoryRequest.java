package com.camperfire.marketflow.dto.crud.inventory;

import com.camperfire.marketflow.validation.CreateRequest;
import com.camperfire.marketflow.validation.UpdateRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequest {

    @Null(groups = CreateRequest.class, message = "ID is not required for create request")
    @NotNull(groups = UpdateRequest.class, message = "ID is required for update request.")
    @Positive(message = "ID must be positive.")
    private Long id;

    @NotNull(groups = CreateRequest.class, message = "Product id is required for create request.")
    private Long productId;

    @NotNull(groups = CreateRequest.class, message = "Quantity is required for create request.")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Long quantity;

    @NotNull(groups = CreateRequest.class, message = "Restock alarm quantity is required for create request.")
    @Min(value = 0, message = "Restock alarm quantity cannot be negative")
    private Long restockAlarmQuantity;
}
