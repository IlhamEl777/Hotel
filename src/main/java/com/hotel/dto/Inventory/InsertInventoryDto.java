package com.hotel.dto.Inventory;

import com.hotel.validation.InventoryExists;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class InsertInventoryDto implements Serializable {
    @NotBlank(message = "Name is required")
    @InventoryExists(message = "Inventory already exists")
    private final String id;
    @NotNull(message = "Stock is required")
    @Min(value = 1, message = "Stock must be greater than 1")
    private final Integer stock;
    @Size(max = 500, message = "Description must be less than 500 characters")
    private final String description;
}
