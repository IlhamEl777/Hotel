package com.hotel.dto.reservation;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UpdateReservationDTO implements Serializable {
    @NotNull(message = "Reservation ID is required")
    private final Long id;
    @NotBlank(message = "Reservation method is required")
    private final String reservationMethod;
    @NotBlank(message = "Room is required")
    private final String roomId;
    @NotBlank(message = "Guest is required")
    private final String guestId;
    @NotNull(message = "Book date is required")
    private final LocalDateTime bookDate;
    @NotNull(message = "Check in date is required")
    private final LocalDateTime checkIn;
    @NotNull(message = "Check out date is required")
    private final LocalDateTime checkOut;
    @NotNull(message = "Cost is required")
    @DecimalMin(value = "1.00", message = "Cost must be greater than 1")
    private final BigDecimal cost;
    @NotNull(message = "Payment date is required")
    private final LocalDateTime paymentDate;
    @NotBlank(message = "Payment method is required")
    private final String paymentMethod;
    private final String remark;
}
