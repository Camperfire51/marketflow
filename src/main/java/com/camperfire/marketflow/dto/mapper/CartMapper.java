package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.mapper.utility.CartMapperUtility;
import com.camperfire.marketflow.dto.response.CartResponseDTO;
import com.camperfire.marketflow.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CartMapperUtility.class, ProductMapper.class})
public interface CartMapper {

    @Mapping(target = "totalBasePrice", source = "products", qualifiedByName = "calculateTotalBasePrice")
    @Mapping(target = "totalDiscount", source = "products", qualifiedByName = "calculateTotalDiscount")
    @Mapping(target = "products", source = "products", qualifiedByName = "mapProducts")
    CartResponseDTO toResponse(Cart cart);
}
