package com.camperfire.marketflow.service;

import com.camperfire.marketflow.dto.request.PaymentRequest;
import com.camperfire.marketflow.dto.response.PaymentResponse;
import com.camperfire.marketflow.model.Payment;

public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest requestDTO);

    Payment createPayment(PaymentRequest requestDTO);

    Payment readPayment(Long id);

    Payment updatePayment(Long id, PaymentRequest requestDTO);

    void deletePayment(Long id);
}
