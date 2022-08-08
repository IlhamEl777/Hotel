package com.hotel.dto.Inventory;

import lombok.Data;

import java.io.Serializable;

@Data
public class InventoryGridDTO implements Serializable {
    private final String id;
    private final Integer stock;
    private final String description;
}
