package com.condabu.Inventory_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private long productId;
    @Column(nullable = false)
    private Integer stockLevel;
    @Column(nullable = false)
    private Integer threshold;
    private Integer reservedStock =0;
    @Column(nullable = false)
    private String batchNo;
    private LocalDateTime expirationDate;
    @Column(nullable = false , updatable = false)
    private LocalDateTime created;
    private LocalDateTime updated;

    @PrePersist
    protected  void  onCreate(){
        created = LocalDateTime.now();
        updated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updated = LocalDateTime.now();
    }

}
