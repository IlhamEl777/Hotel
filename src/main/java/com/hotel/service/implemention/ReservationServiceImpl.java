package com.hotel.service.implemention;

import com.hotel.dao.GuestRepository;
import com.hotel.dao.ReservationRepository;
import com.hotel.dao.RoomRepository;
import com.hotel.dto.reservation.ReservationGridDTO;
import com.hotel.dto.reservation.InsertReservationDTO;
import com.hotel.dto.reservation.UpdateReservationDTO;
import com.hotel.dto.utility.DropdownDTO;
import com.hotel.entity.Reservation;
import com.hotel.service.abstraction.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ReservationServiceImpl implements ReservationService {

    ReservationRepository reservationRepository;

    RoomRepository roomRepository;

    GuestRepository guestRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, RoomRepository roomRepository, GuestRepository guestRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
    }

    private final int rowsPerPage = 10;

    @Override
    public Page<ReservationGridDTO> getGrid(Long id, String guestId, String roomId, String reservationMethod, Integer page) {
        var pagination = PageRequest.of(page - 1, rowsPerPage, Sort.by("id"));
        var data = reservationRepository
                .findAllGrid(id, guestId, roomId, reservationMethod, pagination);
        var replace = data.map(x -> new ReservationGridDTO(
                x.getId(),
                DropdownDTO.getReservationMethod().stream().filter(y -> y.getValue().equals(x.getReservationMethod())).findFirst().get().getText(),
                x.getRoomId(),
                x.getGuestId(),
                x.getBookDate(),
                x.getCheckIn(),
                x.getCheckOut(),
                x.getCost(),
                x.getPaymentDate(),
                DropdownDTO.getPaymentMethod().stream().filter(y -> y.getValue().equals(x.getPaymentMethod())).findFirst().get().getText(),
                x.getRemark()
        ));

        if (replace.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No data found");
        }
        return replace;
    }

    @Override
    public UpdateReservationDTO getUpdate(Long id) {
        var reservation = reservationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Reservation not found"));
        var dto = new UpdateReservationDTO(
                reservation.getId(),
                reservation.getReservationMethod(),
                reservation.getRoomNumber().getId(),
                reservation.getGuestIdNumber().getId(),
                reservation.getBookDate(),
                reservation.getCheckIn(),
                reservation.getCheckOut(),
                reservation.getCost(),
                reservation.getPaymentDate(),
                reservation.getPaymentMethod(),
                reservation.getRemark()
        );
        return dto;
    }

    @Override
    public ReservationGridDTO saveItem(InsertReservationDTO dto) {
        var room = roomRepository.findById(dto.getRoomId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Room not found"));
        var guest = guestRepository.findById(dto.getGuestId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Guest not found"));
        var cost = room.getCost().multiply(BigDecimal.valueOf(durationDay(dto.getCheckIn(), dto.getCheckOut()) == 0 ? 1 : durationDay(dto.getCheckIn(), dto.getCheckOut())));
        var reservation = new Reservation(
                dto.getReservationMethod(),
                room,
                guest,
                dto.getBookDate(),
                dto.getCheckIn(),
                dto.getCheckOut(),
                cost,
                dto.getPaymentDate(),
                dto.getPaymentMethod(),
                dto.getRemark()
        );
        var entity = reservationRepository.save(reservation);
        var response = new ReservationGridDTO(
                entity.getId(),
                entity.getReservationMethod(),
                entity.getRoomNumber().getId(),
                entity.getGuestIdNumber().getId(),
                entity.getBookDate(),
                entity.getCheckIn(),
                entity.getCheckOut(),
                entity.getCost(),
                entity.getPaymentDate(),
                entity.getPaymentMethod(),
                entity.getRemark()
        );
        return response;
    }

    @Override
    public ReservationGridDTO updateItem(UpdateReservationDTO dto) {
        var room = roomRepository.findById(dto.getRoomId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Room not found"));
        var guest = guestRepository.findById(dto.getGuestId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Guest not found"));
        var cost = room.getCost().multiply(BigDecimal.valueOf(durationDay(dto.getCheckIn(), dto.getCheckOut()) == 0 ? 1 : durationDay(dto.getCheckIn(), dto.getCheckOut())));
        var reservation = new Reservation(
                dto.getId(),
                dto.getReservationMethod(),
                room,
                guest,
                dto.getBookDate(),
                dto.getCheckIn(),
                dto.getCheckOut(),
                cost,
                dto.getPaymentDate(),
                dto.getPaymentMethod(),
                dto.getRemark()
        );
        var entity = reservationRepository.save(reservation);
        var response = new ReservationGridDTO(
                entity.getId(),
                entity.getReservationMethod(),
                entity.getRoomNumber().getId(),
                entity.getGuestIdNumber().getId(),
                entity.getBookDate(),
                entity.getCheckIn(),
                entity.getCheckOut(),
                entity.getCost(),
                entity.getPaymentDate(),
                entity.getPaymentMethod(),
                entity.getRemark()
        );
        return response;
    }

    @Override
    public Long deleteOne(Long id) {
        var reservation = reservationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Reservation not found"));
        reservationRepository.delete(reservation);
        return id;
    }

    @Override
    public Boolean checkExistingReservation(Long id) {
        var check = reservationRepository.existsById(id);
        return check;
    }

    @Override
    public Long durationDay(LocalDateTime start, LocalDateTime finish) {
        var duration = Duration.between(start, finish).toDays();
        return duration;
    }

    @Override
    public Boolean checkRoomAvailability(String roomId, LocalDateTime checkIn) {
        var room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Room not found");
        }
        if (room.isPresent()) {
            var data = reservationRepository.findByRoomNumberIdAndCheckOut(roomId);
            if (data.isEmpty()) {
                return true;
            }

            for (var item : data) {
                if (checkIn.isAfter(item.getCheckOut())) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
