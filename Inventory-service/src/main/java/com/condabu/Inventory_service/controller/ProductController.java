package com.condabu.Inventory_service.controller;


import com.condabu.Inventory_service.dto.ImageRepository;
import com.condabu.Inventory_service.entity.Product;
import com.condabu.Inventory_service.service.ProductServices;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @PostMapping
    public ResponseEntity<Object> createNewProduct(@RequestBody Product product){
        return productServices.createProduct(product);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Object> updateExistingProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return productServices.updateProduct(id, product);
    }

}
