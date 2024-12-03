package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.request.CustomerRequestDTO;
import com.camperfire.marketflow.dto.response.CustomerResponseDTO;
import com.camperfire.marketflow.model.user.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerRequestDTO dto);

    CustomerResponseDTO toResponse(Customer entity);
}
