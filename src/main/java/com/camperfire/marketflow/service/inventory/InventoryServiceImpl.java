package com.camperfire.marketflow.service.inventory;

import com.camperfire.marketflow.dto.crud.inventory.InventoryRequest;
import com.camperfire.marketflow.model.Inventory;
import com.camperfire.marketflow.repository.InventoryRepository;

public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory createInventory(InventoryRequest request) {


        inventoryRepository.save()
    }

    @Override
    public Inventory readInventory(Long id) {
        return null;
    }

    @Override
    public Inventory updateInventory(InventoryRequest request) {
        return null;
    }

    @Override
    public void deleteInventory(Long id) {

    }
}
