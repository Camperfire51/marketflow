package com.camperfire.marketflow.dto.crud.payment;

import com.camperfire.marketflow.validation.CreateRequest;
import com.camperfire.marketflow.validation.UpdateRequest;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    @Null(groups = CreateRequest.class, message = "ID is not required for create request")
    @NotNull(groups = UpdateRequest.class, message = "ID is required for update request.")
    @Positive(message = "ID must be positive.")
    private Long id;

    @NotBlank(message = "Credit card number cannot be blank")
    @Size(min = 16, max = 16, message = "Credit card number must be 16 digits")
    private String creditCardNumber;

    @NotBlank(message = "Cardholder name cannot be blank")
    private String cardHolderName;

    @NotNull(message = "Expiration date cannot be null")
    @Future(message = "Expiration date must be in the future")
    private LocalDateTime expirationDate;

    @NotBlank(message = "CVV cannot be blank")
    @Pattern(regexp = "\\d{3,4}", message = "CVV must be 3 or 4 digits")
    private String cvv;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;
}
