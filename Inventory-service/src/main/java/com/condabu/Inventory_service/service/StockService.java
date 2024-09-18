package com.condabu.Inventory_service.service;

import com.condabu.Inventory_service.entity.Inventory;
import com.condabu.Inventory_service.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
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

    public void reserveStock(Long productId, int quantity){
        log.info("Reserving stock for product ID:{}, Quantity: {}", productId, quantity);
        if (isStockAvailable(productId,quantity)){
            inventoryRepository.reserveStock(productId, quantity);
        }
    }
    public void releaseStock(Long productId, int quantity){
        log.info("Releasing stock for product ID:{}, Quantity: {}", productId, quantity);
        inventoryRepository.releaseStock(productId, quantity);
    }
}
