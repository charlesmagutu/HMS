package com.condabu.Inventory_service.controller;


import com.condabu.Inventory_service.common.ServiceResponse;
import com.condabu.Inventory_service.dto.ProductDto;
import com.condabu.Inventory_service.dto.ProductImageDTO;
import com.condabu.Inventory_service.entity.Product;
import com.condabu.Inventory_service.service.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    
    @GetMapping
    public List<ProductDto> listProducts(){

        return productServices.listAllProducts();
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id){
        return productServices.deleteProduct(id);
    }

    @GetMapping("/{id}")

    public ResponseEntity<Object> getProductById(@PathVariable Long id){
        try{
            ProductDto productDto = productServices.getProductById(id);

            return ResponseEntity.status(HttpStatus.OK).body(productDto);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ServiceResponse("failed", "Product with id"+" "+id+" "+"not found"));
        }
    }

    @PatchMapping("/upload/{id}")

    public ResponseEntity<Object> uploadProductImage(@PathVariable Long id, @RequestBody List<ProductImageDTO> productImages) {
        return productServices.uploadImages(id, productImages);
    }
}
