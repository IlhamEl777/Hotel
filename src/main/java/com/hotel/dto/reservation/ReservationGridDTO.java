package com.hotel.dto.reservation;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReservationGridDTO implements Serializable {
    private final Long id;
    private final String reservationMethod;
    private final String roomId;
    private final String guestId;
    private final LocalDateTime bookDate;
    private final LocalDateTime checkIn;
    private final LocalDateTime checkOut;
    private final BigDecimal cost;
    private final LocalDateTime paymentDate;
    private final String paymentMethod;
    private final String remark;
}
