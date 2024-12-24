package com.camperfire.marketflow.service.inventory;

import com.camperfire.marketflow.dto.crud.inventory.InventoryRequest;

import com.camperfire.marketflow.model.Inventory;

public interface InventoryService {

    Inventory createInventory(InventoryRequest request);

    Inventory readInventory(Long id);

    Inventory updateInventory(InventoryRequest request);

    void deleteInventory(Long id);
}
