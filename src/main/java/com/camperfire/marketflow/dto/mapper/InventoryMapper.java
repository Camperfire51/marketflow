package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.crud.inventory.InventoryRequest;
import com.camperfire.marketflow.dto.crud.inventory.InventoryResponse;
import com.camperfire.marketflow.model.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    Inventory toEntity(InventoryRequest request);

    InventoryResponse toResponse(Inventory inventory);
}
