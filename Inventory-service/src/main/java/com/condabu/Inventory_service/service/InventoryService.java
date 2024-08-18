package com.condabu.Inventory_service.service;

import com.condabu.Inventory_service.entity.Inventory;
import com.condabu.Inventory_service.dto.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;


    public Inventory createOrUpdateInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

}
