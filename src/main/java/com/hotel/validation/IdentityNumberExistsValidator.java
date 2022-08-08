package com.hotel.validation;

import com.hotel.service.abstraction.GuestService;
import com.hotel.service.abstraction.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdentityNumberExistsValidator implements ConstraintValidator<IdentityNumberExists, String> {

    @Autowired
    private GuestService service;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        if (id.isEmpty()){
            return true;
        }
        return !service.checkExistingGuest(id);
    }
}
