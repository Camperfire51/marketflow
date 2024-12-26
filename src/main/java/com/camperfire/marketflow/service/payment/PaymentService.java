package com.camperfire.marketflow.service.payment;

import com.camperfire.marketflow.dto.crud.payment.PaymentRequest;
import com.camperfire.marketflow.dto.response.PaymentResponse;
import com.camperfire.marketflow.model.Payment;

public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest request);

    Payment createPayment(PaymentRequest request);

    Payment readPayment(Long id);

    Payment updatePayment(PaymentRequest request);

    void deletePayment(Long id);
}
