package com.hotel.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code", nullable = false)
    private Long id;

    @Column(name = "reservation_method", nullable = false, length = 5)
    private String reservationMethod;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_number", nullable = false)
    private Room roomNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "guest_id_number", nullable = false)
    private Guest guestIdNumber;

    @Column(name = "book_date", nullable = false)
    private LocalDateTime bookDate;

    @Column(name = "check_in", nullable = false)
    private LocalDateTime checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDateTime checkOut;

    @Column(name = "cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal cost;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "payment_method", nullable = false, length = 5)
    private String paymentMethod;

    @Column(name = "remark", length = 500)
    private String remark;

    public Reservation(String reservationMethod, Room roomNumber, Guest guestIdNumber, LocalDateTime bookDate, LocalDateTime checkIn, LocalDateTime checkOut, BigDecimal cost, LocalDateTime paymentDate, String paymentMethod, String remark) {
        this.reservationMethod = reservationMethod;
        this.roomNumber = roomNumber;
        this.guestIdNumber = guestIdNumber;
        this.bookDate = bookDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.cost = cost;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.remark = remark;
    }
}