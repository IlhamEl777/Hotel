package com.hotel.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDateTime;

@Constraint(validatedBy = RoomStatusNotBookedValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoomStatusNotBooked {

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public String roomID();
    public String checkIn();

    public String message();

}
