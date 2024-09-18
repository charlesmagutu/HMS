package org.condabu.orderservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.condabu.orderservice.entity.OrderItem;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private Long userId;
    private List<OrderItemRequest> items;
}
