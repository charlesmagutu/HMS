package com.condabu.Inventory_service.repository;

import com.condabu.Inventory_service.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findProductImagesById(Long id);

}
