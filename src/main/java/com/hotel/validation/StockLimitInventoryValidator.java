package com.hotel.validation;

import com.hotel.service.abstraction.RoomService;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StockLimitInventoryValidator implements ConstraintValidator<StockLimitInventory, Object> {


    @Autowired
    private RoomService service;

    private String name;
    private String qty;


    @Override
    public void initialize(StockLimitInventory constraintAnnotation) {
        name = constraintAnnotation.name();
        qty = constraintAnnotation.quantity();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String name = (new BeanWrapperImpl(value).getPropertyValue(this.name)).toString();
        Integer qty = (Integer) (new BeanWrapperImpl(value).getPropertyValue(this.qty));
        return service.checkExistingInventoryLimitAvailable(name, qty);
    }


}
