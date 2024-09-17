package com.condabu.Inventory_service.repository;

import com.condabu.Inventory_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductById(Long id);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.images")
    List<Product> findAllWithImages();
}
