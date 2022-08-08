package com.hotel.validation;

import com.hotel.service.abstraction.RoomService;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueRoomInventoryValidator implements ConstraintValidator<UniqueRoomInventory, Object> {
    @Autowired
    private RoomService service;

    private String roomNumber;
    private String inventoryName;


    @Override
    public void initialize(UniqueRoomInventory constraintAnnotation) {
        roomNumber = constraintAnnotation.roomNumber();
        inventoryName = constraintAnnotation.inventoryName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String roomNumber = (new BeanWrapperImpl(value).getPropertyValue(this.roomNumber)).toString();
        String inventoryName = (new BeanWrapperImpl(value).getPropertyValue(this.inventoryName)).toString();
        return !service.checkExistingRoomInventory(roomNumber, inventoryName);
    }


}
