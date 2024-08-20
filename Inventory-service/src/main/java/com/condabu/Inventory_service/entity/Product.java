package com.condabu.Inventory_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.annotation.Documented;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String brand;
    private Integer supplierId;
    private LocalDateTime created;
    private LocalDateTime updated;

    @PrePersist
    protected void onCreate(){
        created = LocalDateTime.now();
        updated = LocalDateTime.now();
    }
    @PreUpdate

    protected void  onUpdate(){
        updated = LocalDateTime.now();
    }
}
