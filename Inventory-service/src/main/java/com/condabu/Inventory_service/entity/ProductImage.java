package com.condabu.Inventory_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductImage {
    @Id
    private Long id;
    private String productId;
    private String imageUrl;
    private String imageType;
    private LocalDateTime created;


    @PrePersist
    public  void  onCreate(){
        created = LocalDateTime.now();
    }
}
