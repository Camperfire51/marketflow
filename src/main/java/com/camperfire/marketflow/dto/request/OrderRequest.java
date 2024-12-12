package com.camperfire.marketflow.dto.request;

import com.camperfire.marketflow.model.Address;
import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.OrderStatus;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.model.user.Customer;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotEmpty(message = "Order must contain at least one product")
    private Map<@NotNull(message = "Product ID is required") Product, @Positive(message = "Quantity must be a positive value") Long> products;

    @NotNull(message = "Customer cannot be null")
    private Customer customer;

    private LocalDateTime orderDate;

    @NotNull(message = "Address is required")
    private Address shippingAddress;

    @NotNull(message = "Order status is required")
    private OrderStatus status;
}
