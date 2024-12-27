package com.camperfire.marketflow.service.payment;

import com.camperfire.marketflow.dto.crud.payment.PaymentRequest;
import com.camperfire.marketflow.dto.mapper.PaymentMapper;
import com.camperfire.marketflow.model.Payment;
import com.camperfire.marketflow.model.PaymentStatus;
import com.camperfire.marketflow.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

//TODO: Implement CRUD methods
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public Payment processPayment(PaymentRequest requestDTO) {
        Payment payment = paymentMapper.toEntity(requestDTO);

        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
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
