package com.condabu.Inventory_service.service;

import com.condabu.Inventory_service.common.ServiceResponse;
import com.condabu.Inventory_service.entity.Inventory;
import com.condabu.Inventory_service.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired StockService stockService;
    @Transactional
    public Inventory createInventory(Inventory inventory) {

        return inventoryRepository.save(inventory);
    }

    public ResponseEntity<ServiceResponse> updateInventory(Long id, Inventory inventory) {
        if (!checkIfInventoryExists(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ServiceResponse("failed", "inventory not found"));
        }

        try {
            Optional<Inventory> existingProductOpt = inventoryRepository.findById(id);

            if (existingProductOpt.isPresent()) {
                Inventory existingProduct = existingProductOpt.get();

                // Update existing product with new values
                existingProduct.setBatchNo(inventory.getBatchNo());
                existingProduct.setThreshold(inventory.getThreshold());
                existingProduct.setReservedStock(inventory.getReservedStock());
                existingProduct.setStockLevel(inventory.getStockLevel());

                // Save updated product
                inventoryRepository.save(existingProduct);

                // Log successful update
                log.info("Updated inventory with id {}: {}", id, existingProduct);

                return ResponseEntity.ok(new ServiceResponse("success", "inventory updated successfully"));
            } else {
                // Handle case where inventory was not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ServiceResponse("failed", "inventory not found"));
            }
        } catch (Exception e) {
            log.error("Error updating inventory with id {}", id, e);
            // Return internal server error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ServiceResponse("error", "an internal server error occurred"));
        }
    }


//    public void bulkProductCSVUpload(InputStream inputStream){
//        try{BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            CSVReader csvReader = new CSVReader(reader);
//            List<String[]> records = csvReader.readAll();
//
//            for(String[] record : records){
//            Product product = new Product();
//
//        }
//    }

    public  boolean checkIfInventoryExists(Long id){
        Optional<Inventory> inventory = Optional.ofNullable(inventoryRepository.findByProductId(id));
        return inventory.isPresent();
    }
}
