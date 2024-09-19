package org.condabu.orderservice.controller;

import org.condabu.orderservice.entity.Order;
import org.condabu.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@RequestParam Long cartId, @RequestParam Long userId) {
        Order order = orderService.createOrder(cartId, userId);
        return ResponseEntity.ok(order);
    }
}
