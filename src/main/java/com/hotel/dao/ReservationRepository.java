package com.hotel.dao;

import com.hotel.dto.reservation.ReservationGridDTO;
import com.hotel.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
            SELECT new com.hotel.dto.reservation.ReservationGridDTO(
            r.id, 
            r.reservationMethod,
            r.roomNumber.id, 
            r.guestIdNumber.id, 
            r.bookDate,
            r.checkIn,
            r.checkOut,
            r.cost,
            r.paymentDate,
            r.paymentMethod,
            r.remark
            )
            FROM Reservation r
            WHERE (r.id = :id OR :id IS NULL)
            AND r.guestIdNumber.id like %:guestId%
            AND r.roomNumber.id like %:roomId%
            AND r.reservationMethod like %:reservationMethod%           
            """)
    Page<ReservationGridDTO> findAllGrid(Long id, String guestId, String roomId, String reservationMethod, Pageable pageable);


    Long countByroomNumber_IdAndCheckOutIsGreaterThanEqual(String id, LocalDateTime checkOut);

    //show reservation with roomNumber = 1 and max checkOut
    @Query("""
            SELECT r FROM Reservation r
            WHERE r.roomNumber.id = :id
            AND r.checkOut = (SELECT MAX(r.checkOut) FROM Reservation r WHERE r.roomNumber.id = :id)
            """)
    List<Reservation> findByRoomNumberIdAndCheckOut(String id);


    Long countByRoomNumber_Id(String number);
}