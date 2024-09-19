package org.condabu.orderservice.service;


import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.condabu.orderservice.entity.Cart;
import org.condabu.orderservice.entity.Order;
import org.condabu.orderservice.entity.OrderItem;
import org.condabu.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderRepository orderRepository;
    @Transactional
    public Order createOrder(Long cartId, Long userId){
        Cart cart = cartService.getCart(cartId);
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setStatus("PENDING");
        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cartId);
        return savedOrder;
    }
}
