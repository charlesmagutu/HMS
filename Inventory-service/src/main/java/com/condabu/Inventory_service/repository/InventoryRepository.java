package com.condabu.Inventory_service.repository;

import com.condabu.Inventory_service.entity.Inventory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByProductId(Long productId);

    @Modifying
    @Transactional
    @Query("UPDATE Inventory i SET i.stockLevel=COALESCE(i.stockLevel-i.reservedStock,0), i.reservedStock = COALESCE(i.reservedStock,0) +:quantity WHERE i.productId = :productId")
    void reserveStock(@Param("productId") Long productId, @Param("quantity") int quantity);


    @Modifying
    @Transactional
    @Query("UPDATE Inventory  i SET i.stockLevel=COALESCE(i.stockLevel+i.reservedStock,0), i.reservedStock = COALESCE(i.reservedStock,0) -:quantity WHERE i.productId = :productId")

    void releaseStock(@Param("productId") Long productId, @Param("quantity") int quantity);
}
