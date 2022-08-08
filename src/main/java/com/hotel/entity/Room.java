package com.hotel.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Rooms")
public class Room {
    @Id
    @Column(name = "number", nullable = false, length = 10)
    private String id;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Column(name = "room_type", nullable = false, length = 3)
    private String roomType;

    @Column(name = "guest_limit", nullable = false)
    private Integer guestLimit;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal cost;

    @OneToMany(mappedBy = "roomNumber")
    private Set<Reservation> reservations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "roomNumber")
    private Set<RoomInventory> roomInventories = new LinkedHashSet<>();

    public Room(String id, Integer floor, String roomType, Integer guestLimit, String description, BigDecimal cost) {
        this.id = id;
        this.floor = floor;
        this.roomType = roomType;
        this.guestLimit = guestLimit;
        this.description = description;
        this.cost = cost;
    }
}