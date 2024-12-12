package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.request.PaymentRequest;
import com.camperfire.marketflow.dto.response.PaymentResponse;
import com.camperfire.marketflow.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toEntity(PaymentRequest requestDTO);

    PaymentResponse toResponse(Payment payment);
}
