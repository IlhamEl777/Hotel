package com.hotel.dto.guest;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UpdateGuestDto implements Serializable {
    @NotBlank(message = "Identity Number is required")
    private final String id;
    @NotBlank(message = "First Name is required")
    private final String firstName;

    private final String middleName;
    private final String lastName;
    @NotNull(message = "Birth Date is required")
    private final LocalDate birthDate;
    @NotBlank(message = "Gender is required")
    private final String gender;
    @NotBlank(message = "Citizenship is required")
    private final String citizenship;
}
