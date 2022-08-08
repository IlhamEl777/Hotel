package com.hotel.validation;

import com.hotel.service.abstraction.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InventoryExistsValidator implements ConstraintValidator<InventoryExists, String> {

    @Autowired
    private RoomService service;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !service.checkExistingInventory(name);
    }
}
