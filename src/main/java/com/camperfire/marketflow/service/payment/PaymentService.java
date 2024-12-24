package com.camperfire.marketflow.service.payment;

import com.camperfire.marketflow.dto.crud.payment.PaymentRequest;
import com.camperfire.marketflow.dto.response.PaymentResponse;
import com.camperfire.marketflow.model.Payment;

public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest requestDTO);

    Payment createPayment(PaymentRequest requestDTO);

    Payment readPayment(Long id);

    Payment updatePayment(Long id, PaymentRequest requestDTO);

    void deletePayment(Long id);
}
