package com.hotel.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomGridDTO implements Serializable {
    private String id;
    private Integer floor;
    private String roomType;
    private Integer guestLimit;
    private BigDecimal cost;
    private String status;
}
