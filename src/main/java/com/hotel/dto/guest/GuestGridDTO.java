package com.hotel.dto.guest;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class GuestGridDTO implements Serializable {
    private final String id;
    private final String fullName;
    private final LocalDate birthDate;
    private final String gender;
    private final String citizenship;
}
