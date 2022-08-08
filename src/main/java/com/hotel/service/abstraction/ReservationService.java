package com.hotel.service.abstraction;

import com.hotel.dto.reservation.ReservationGridDTO;
import com.hotel.dto.reservation.InsertReservationDTO;
import com.hotel.dto.reservation.UpdateReservationDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface ReservationService {

    public Page<ReservationGridDTO> getGrid(Long id, String guestId, String roomId, String reservationMethod, Integer page);

    public UpdateReservationDTO getUpdate(Long id);

    public ReservationGridDTO saveItem(InsertReservationDTO dto);

    public ReservationGridDTO updateItem(UpdateReservationDTO dto);

    public Long deleteOne(Long id);

    public Boolean checkExistingReservation(Long id);

    public Long durationDay(LocalDateTime start, LocalDateTime finish);

    public Boolean checkRoomAvailability(String roomId, LocalDateTime checkIn);

}
