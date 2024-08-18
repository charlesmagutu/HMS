package com.condabu.Inventory_service.controller;


import com.condabu.Inventory_service.entity.Inventory;
import com.condabu.Inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    public ResponseEntity<Inventory> createOrUpdateInventory(@RequestBody Inventory inventory) {

        Inventory saveInventory = inventoryService.createOrUpdateInventory(inventory);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveInventory);
    }
}
