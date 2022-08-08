package com.hotel.dto.room.item;

import lombok.Data;

@Data
public class InventoryGridDTO {
    private final Long id;
    private final String inventoryName;
    private final Integer stock;
    private final Integer quantity;
}
