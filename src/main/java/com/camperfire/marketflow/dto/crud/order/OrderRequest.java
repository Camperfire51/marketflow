package com.camperfire.marketflow.dto.crud.order;

import com.camperfire.marketflow.model.Address;
import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.validation.CreateRequest;
import com.camperfire.marketflow.validation.UpdateRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @Null(groups = CreateRequest.class, message = "ID is not required for create request")
    @NotNull(groups = UpdateRequest.class, message = "ID is required for update request.")
    @Positive(message = "ID must be positive.")
    private Long id;

    @NotNull(message = "Products cannot be null")
    private Map<
            @NotNull(message = "Product ID cannot be null") @Positive(message = "Product ID must be positive") Long,
            @NotNull(message = "Quantity cannot be null") @Min(value = 1, message = "At least 1 product is required") Long
            > products = new HashMap<>();

    @NotNull(message = "Customer id cannot be null")
    private Long customerId;

    @NotNull(message = "Address is required")
    private Address shippingAddress;
}
