package com.hotel.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "RoomInventories")
public class RoomInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_number", nullable = false)
    private Room roomNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_name", nullable = false)
    private Inventory inventoryName;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public RoomInventory(Room roomNumber, Inventory inventoryName, Integer quantity) {
        this.roomNumber = roomNumber;
        this.inventoryName = inventoryName;
        this.quantity = quantity;
    }
}