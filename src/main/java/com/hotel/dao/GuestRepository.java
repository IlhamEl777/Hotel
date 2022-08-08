package com.hotel.dao;

import com.hotel.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, String> {

    Page<Guest> findByIdContainingAndFullNameContaining(String id, String fullName, Pageable pageable);
}