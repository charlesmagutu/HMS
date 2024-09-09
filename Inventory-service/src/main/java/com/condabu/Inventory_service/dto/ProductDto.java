package com.condabu.Inventory_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class ProductDto {
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
    private List<ProductImageDTO> images;
}
