package com.hotel.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueRoomInventoryValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueRoomInventory {

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public String roomNumber();
    public String inventoryName();

    public String message();

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        UniqueRoomInventory[] value();
    }

}
