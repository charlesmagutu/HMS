package org.condabu.orderservice.repository;

import org.condabu.orderservice.entity.Cart;
import org.condabu.orderservice.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository  extends JpaRepository<Cart, Long> {
    Optional<Cart> findById(Long id);
}
