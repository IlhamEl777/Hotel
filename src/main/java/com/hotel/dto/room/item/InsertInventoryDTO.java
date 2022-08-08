package com.hotel.dto.room.item;

import com.hotel.validation.StockLimitInventory;
import com.hotel.validation.UniqueRoomInventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@UniqueRoomInventory(roomNumber = "roomNumber", inventoryName = "inventoryName", message = "Inventory is already exist")
@StockLimitInventory(name = "inventoryName", quantity = "quantity", message = "Stock inventory not enough or Inventory not exist")
public class InsertInventoryDTO {
    @NotBlank(message = "Room number is required")
    private String roomNumber;
    @NotBlank(message = "Inventory name is required")
    private String inventoryName;
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;
}
