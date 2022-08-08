package com.hotel.dto.room;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UpsertRoomDto implements Serializable {
    @NotBlank(message = "Room number is required")
    private final String id;
    @NotNull(message = "Room type is required")
    private final Integer floor;
    @NotBlank(message = "Room type is required")
    private final String roomType;
    @NotNull(message = "Room price is required")
    private final Integer guestLimit;
    @Size(max = 500, message = "Max character is 500")
    private final String description;
    @NotNull(message = "Room price is required")
    @DecimalMin(value = "0", message = "Room price cannot be negative")
    private final BigDecimal cost;
}
