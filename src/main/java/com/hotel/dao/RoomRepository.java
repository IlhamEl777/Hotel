package com.hotel.dao;

import com.hotel.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface RoomRepository extends JpaRepository<Room, String> {

    Page<Room> findAllByIdContainingAndRoomTypeContaining(String number, String roomType, Pageable pageable);

}