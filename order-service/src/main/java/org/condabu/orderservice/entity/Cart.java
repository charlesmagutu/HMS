package org.condabu.orderservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean checkedOut;
    private int quantity;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();
    private Double totalPrice;

    public Double calculateTotalPrice(Cart cart){
        return items.stream().mapToDouble(items -> items.getPrice() * items.getQuantity()).sum();

    }
}
