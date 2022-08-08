package com.hotel.validation;

import com.hotel.service.abstraction.ReservationService;
import com.hotel.service.abstraction.RoomService;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class RoomStatusNotBookedValidator implements ConstraintValidator<RoomStatusNotBooked, Object> {

    @Autowired
    private ReservationService service;

    String roomId;
    String checkIn;

    @Override
    public void initialize(RoomStatusNotBooked constraintAnnotation) {
        roomId = constraintAnnotation.roomID();
        checkIn = constraintAnnotation.checkIn();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        String roomId = (new BeanWrapperImpl(value).getPropertyValue(this.roomId)).toString();
        LocalDateTime checkIn = (LocalDateTime) (new BeanWrapperImpl(value).getPropertyValue(this.checkIn));
        if (checkIn == null) {
            return true;
        }
        return service.checkRoomAvailability(roomId, checkIn);
    }
}
