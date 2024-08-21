package com.condabu.Inventory_service.service;

import com.condabu.Inventory_service.entity.Inventory;
import com.condabu.Inventory_service.dto.InventoryRepository;
import com.condabu.Inventory_service.entity.Product;
import com.opencsv.CSVReader;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional
    public Inventory createOrUpdateInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

//    public void bulkProductCSVUpload(InputStream inputStream){
//        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            CSVReader csvReader = new CSVReader(reader);
//            List<String[]> records = csvReader.readAll();
//
//            for(String[] record : records){
//            Product product = new Product();
//
//        }
//    }
}
