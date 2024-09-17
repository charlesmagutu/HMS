package com.condabu.Inventory_service.dto;

import lombok.Data;

@Data
public class StockCheckRequest {
    private Long productId;
    private Integer quantity;
}
