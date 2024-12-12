package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.mapper.utility.OrderMapperUtility;
import com.camperfire.marketflow.dto.request.OrderRequest;
import com.camperfire.marketflow.dto.response.OrderResponse;
import com.camperfire.marketflow.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = OrderMapperUtility.class)
public interface OrderMapper {

    Order toEntity(OrderRequest request);

    OrderResponse toResponse(Order entity);
}
