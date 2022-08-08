package com.hotel.dao;

import com.hotel.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, String> {
    Page<Inventory> findByIdContaining(String name, Pageable pageable);
    Boolean existsByIdAndStockGreaterThanEqual(String id, Integer qty);

}