package com.camperfire.marketflow.service.inventory;

import com.camperfire.marketflow.dto.crud.inventory.InventoryRequest;

import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.Inventory;

public interface InventoryService {

    void setStock(Inventory inventory, Long newStock);

    Inventory createInventory(InventoryRequest request);

    Inventory readInventory(Long id);

    Inventory updateInventory(InventoryRequest request);

    void deleteInventory(Long id);
}
