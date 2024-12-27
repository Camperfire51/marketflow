package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.crud.cart.CartResponse;
import com.camperfire.marketflow.dto.mapper.utility.CartMapperUtility;
import com.camperfire.marketflow.dto.crud.cart.CartRequest;
import com.camperfire.marketflow.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CartMapperUtility.class, ProductMapper.class})
public interface CartMapper {

    Cart toEntity(CartRequest request);

    CartResponse toResponse(Cart entity);
}
