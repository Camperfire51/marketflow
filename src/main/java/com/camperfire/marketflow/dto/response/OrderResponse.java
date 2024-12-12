package com.camperfire.marketflow.dto.response;

import com.camperfire.marketflow.model.Address;
import com.camperfire.marketflow.model.OrderStatus;
import com.camperfire.marketflow.model.Product;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotEmpty(message = "Order must contain at least one product")
    private Map<@NotNull(message = "Product ID is required") Product,
            @Positive(message = "Quantity must be a positive value") Long> products;

    private LocalDateTime orderDate;

    @NotNull(message = "Address is required")
    private Address shippingAddress;

    @NotNull(message = "Order status is required")
    private OrderStatus status;
}
