package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.crud.payment.PaymentRequest;
import com.camperfire.marketflow.dto.crud.payment.PaymentResponse;
import com.camperfire.marketflow.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toEntity(PaymentRequest requestDTO);

    PaymentResponse toResponse(Payment payment);
}
