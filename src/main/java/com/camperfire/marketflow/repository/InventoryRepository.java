package com.camperfire.marketflow.repository;

import com.camperfire.marketflow.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
}
