package com.condabu.Inventory_service.service;

import com.condabu.Inventory_service.dto.StockCheckRequest;
import com.condabu.Inventory_service.entity.Inventory;
import com.condabu.Inventory_service.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductServices productServices;

    @Transactional
    public Inventory createOrUpdateInventory(Inventory inventory) {

        return inventoryRepository.save(inventory);
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

}
