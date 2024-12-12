package com.camperfire.marketflow.dto.response;

import com.camperfire.marketflow.model.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;

    private BigDecimal amount;

    private PaymentStatus status;

    private LocalDateTime paymentDate;
}
