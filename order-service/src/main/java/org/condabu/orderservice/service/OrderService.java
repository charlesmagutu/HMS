package org.condabu.orderservice.service;


import jakarta.transaction.Transactional;
import org.condabu.orderservice.entity.Cart;
import org.condabu.orderservice.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private CartService cartService;
    @Transactional
    public void createOrder(Long userId){

    }
}
