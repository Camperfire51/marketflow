package com.camperfire.marketflow.dto.crud.cart;

import com.camperfire.marketflow.validation.CreateRequest;
import com.camperfire.marketflow.validation.UpdateRequest;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRequest {

    @Null(groups = CreateRequest.class, message = "ID is not required for create request")
    @NotNull(groups = UpdateRequest.class, message = "ID is required for update request.")
    @Positive(message = "ID must be positive.")
    private Long id;

    @NotNull(groups = CreateRequest.class, message = "Customer id is required for update request.")
    private Long customerId;

    @NotNull(message = "Products cannot be null")
    private Map<
            @NotNull(message = "Product ID cannot be null") @Positive(message = "Product ID must be positive") Long,
            @NotNull(message = "Quantity cannot be null") @Min(value = 0, message = "At least 1 product is required") Long
            > products = new HashMap<>();
}
