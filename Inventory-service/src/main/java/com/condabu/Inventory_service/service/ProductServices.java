package com.condabu.Inventory_service.service;


import com.condabu.Inventory_service.entity.Product;
import com.condabu.Inventory_service.dto.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServices {

    @Autowired
    private ProductRepository productRepository;
    public Product createProduct(Product product) {

        return productRepository.save(product);
    }

}
