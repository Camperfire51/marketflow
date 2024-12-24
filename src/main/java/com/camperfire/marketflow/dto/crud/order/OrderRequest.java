package com.camperfire.marketflow.dto.crud.order;

import com.camperfire.marketflow.model.Address;
import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotNull(message = "Cart cannot be null")
    private Cart cart;

    @NotNull(message = "Customer id cannot be null")
    private Long customerId;

    @NotNull(message = "Address is required")
    private Address shippingAddress;
}
