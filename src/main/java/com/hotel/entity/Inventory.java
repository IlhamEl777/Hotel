package com.hotel.entity;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Inventories")
public class Inventory {
    @Id
    @Column(name = "name", nullable = false, length = 50)
    private String id;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @OneToMany(mappedBy = "inventoryName")
    private Set<RoomInventory> roomInventories = new LinkedHashSet<>();

    public Inventory(String id, String description, Integer stock) {
        this.id = id;
        this.description = description;
        this.stock = stock;
    }
}