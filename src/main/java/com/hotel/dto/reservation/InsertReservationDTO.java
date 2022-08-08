package com.hotel.dto.reservation;

import com.hotel.validation.RoomStatusNotBooked;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@RoomStatusNotBooked(roomID = "roomId", checkIn = "checkIn", message = "Room is already booked")
public class InsertReservationDTO implements Serializable {
    @NotBlank(message = "Reservation method is required")
    private final String reservationMethod;
    @NotBlank(message = "Room ID is required")
    private final String roomId;
    @NotBlank(message = "Guest ID is required")
    private final String guestId;
    @NotNull(message = "Book date is required")
    private final LocalDateTime bookDate;
    @NotNull(message = "Check in date is required")
    private final LocalDateTime checkIn;
    @NotNull(message = "Check out date is required")
    private final LocalDateTime checkOut;
    @NotNull(message = "Payment date is required")
    private final LocalDateTime paymentDate;
    @NotBlank(message = "Payment method is required")
    private final String paymentMethod;
    private final String remark;
}
