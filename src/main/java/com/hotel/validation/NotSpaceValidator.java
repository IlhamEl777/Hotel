package com.hotel.validation;

import com.hotel.service.abstraction.GuestService;
import com.hotel.service.abstraction.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotSpaceValidator implements ConstraintValidator<NotSpace, String> {

    @Autowired
    private GuestService service;

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {
        return service.notSpace(input);
    }
}
