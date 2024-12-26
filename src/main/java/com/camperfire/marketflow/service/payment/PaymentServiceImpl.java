package com.camperfire.marketflow.service.payment;

import com.camperfire.marketflow.dto.mapper.PaymentMapper;
import com.camperfire.marketflow.dto.crud.payment.PaymentRequest;
import com.camperfire.marketflow.dto.response.PaymentResponse;
import com.camperfire.marketflow.model.EmailMessage;
import com.camperfire.marketflow.model.Payment;
import com.camperfire.marketflow.model.PaymentStatus;
import com.camperfire.marketflow.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest requestDTO) {
        Payment payment = paymentMapper.toEntity(requestDTO);

        // Simulate payment processing
        payment.setStatus(PaymentStatus.COMPLETED); // Assume success for now
        payment.setPaymentDate(LocalDateTime.now());

        // Save to DB
        payment = paymentRepository.save(payment);

        // Map Entity to ResponseDTO and return
        return paymentMapper.toResponse(payment);
    }

    @Override
    public Payment createPayment(PaymentRequest request) {
        return null;
    }

    @Override
    public Payment readPayment(Long id) {
        return null;
    }

    @Override
    public Payment updatePayment(PaymentRequest request) {
        return null;
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow();

        paymentRepository.delete(payment);
    }


}
