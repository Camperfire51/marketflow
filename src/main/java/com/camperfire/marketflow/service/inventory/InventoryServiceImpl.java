package com.camperfire.marketflow.service.inventory;

import com.camperfire.marketflow.dto.crud.inventory.InventoryRequest;
import com.camperfire.marketflow.dto.mapper.InventoryMapper;
import com.camperfire.marketflow.model.Inventory;
import com.camperfire.marketflow.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public Inventory createInventory(InventoryRequest request) {
        Inventory inventory = inventoryMapper.toEntity(request);

        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory readInventory(Long id) {
        return inventoryRepository.findById(id).orElseThrow();
    }

    @Override
    public Inventory updateInventory(InventoryRequest request) {
        Inventory inventory = inventoryRepository.findById(request.getId()).orElseThrow();

        //TODO: Implement update logic.

        return inventoryRepository.save(inventory);
    }

    @Override
    public void deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow();

        inventoryRepository.delete(inventory);
    }
}
