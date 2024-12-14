package com.camperfire.marketflow.dto.response;

import com.camperfire.marketflow.model.Address;
import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Cart cannot be null")
    private Cart cart;

    private LocalDateTime orderDate;

    @NotNull(message = "Address is required")
    private Address shippingAddress;

    @NotNull(message = "Order status is required")
    private OrderStatus status;
}
