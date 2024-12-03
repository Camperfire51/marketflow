package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.response.CartResponseDTO;
import com.camperfire.marketflow.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartResponseDTO toResponse(Cart entity);
}
