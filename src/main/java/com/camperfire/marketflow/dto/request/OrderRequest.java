package com.camperfire.marketflow.dto.request;

import com.camperfire.marketflow.model.Address;
import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.OrderStatus;
import com.camperfire.marketflow.model.user.Customer;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotNull(message = "Cart cannot be null")
    private Cart cart;

    @NotNull(message = "Customer cannot be null")
    private Customer customer;

    private LocalDateTime orderDate;

    @NotNull(message = "Address is required")
    private Address shippingAddress;

    @NotNull(message = "Order status is required")
    private OrderStatus status;
}
