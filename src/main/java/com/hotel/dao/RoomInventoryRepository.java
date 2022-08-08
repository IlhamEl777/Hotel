package com.hotel.dao;

import com.hotel.entity.RoomInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomInventoryRepository extends JpaRepository<RoomInventory, Long> {
    Page<RoomInventory> findByRoomNumber_Id(String id, Pageable pageable);
    Long countByRoomNumber_Id(String id);

    Long countByInventoryName_Id(String id);

    Long countById(Long id);

    Boolean existsByRoomNumber_IdAndInventoryName_Id(String roomNumber, String inventoryName);
}