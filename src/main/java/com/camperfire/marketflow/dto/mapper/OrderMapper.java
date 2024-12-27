package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.crud.order.OrderResponse;
import com.camperfire.marketflow.dto.mapper.utility.OrderMapperUtility;
import com.camperfire.marketflow.dto.crud.order.OrderRequest;
import com.camperfire.marketflow.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = OrderMapperUtility.class)
public interface OrderMapper {

    Order toEntity(OrderRequest request);

    OrderResponse toResponse(Order entity);
}
