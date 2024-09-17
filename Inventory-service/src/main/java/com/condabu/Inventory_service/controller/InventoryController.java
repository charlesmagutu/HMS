package com.condabu.Inventory_service.controller;


import com.condabu.Inventory_service.common.ServiceResponse;
import com.condabu.Inventory_service.dto.StockCheckRequest;
import com.condabu.Inventory_service.entity.Inventory;
import com.condabu.Inventory_service.service.InventoryService;
import com.condabu.Inventory_service.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private StockService stockService;

    @PostMapping
    public ResponseEntity<Inventory> createOrUpdateInventory(@RequestBody Inventory inventory) {

        Inventory saveInventory = inventoryService.createOrUpdateInventory(inventory);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveInventory);
    }

    @PostMapping("check-stock")
    public ResponseEntity<Object> isStockAvailable(@RequestBody StockCheckRequest stockCheckRequest){
        Long product  = stockCheckRequest.getProductId();
        int qty = stockCheckRequest.getQuantity();

        try{
            boolean isAvailable = stockService.isStockAvailable(product, qty);
            if(isAvailable){
                return ResponseEntity.ok().body(new ServiceResponse("success", "stock available"));
            }else{
                return  ResponseEntity.ok().body(new ServiceResponse("failed", "enough stock not available"));
            }
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ServiceResponse("error", "An internal server error occurred while processing your request"));
        }
    }
}
