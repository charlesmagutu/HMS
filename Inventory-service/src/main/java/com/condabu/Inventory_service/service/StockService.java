package com.condabu.Inventory_service.service;

import com.condabu.Inventory_service.entity.Inventory;
import com.condabu.Inventory_service.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StockService {
    @Autowired
    private InventoryRepository inventoryRepository;
    public boolean isStockAvailable(Long productId, int requestedQty){
        Optional<Inventory> productExists = Optional.ofNullable(inventoryRepository.findByProductId(productId));

        if(productExists.isPresent()){
            Inventory inventory = productExists.get();
            return inventory.getStockLevel() >=requestedQty;
        }else {
            return false;
        }
    }

}
