package com.hotel;

import com.hotel.dto.utility.ErrorDTO;
import org.springframework.validation.ObjectError;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Helper {

    private static Object getFieldValue(Object object, Integer index){
        return ((Object[])object)[index];
    }

    private static <T> Object getFieldValue(Object object, String fieldName){
        try{
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            var value = field.get(object);
            return value;
        }catch (Exception exception){
        }
        return null;
    }

    private static <T> String getStringField(T object, String defaultMessage) {
        try {
            return getFieldValue(object, defaultMessage).toString();
        } catch (Exception exception) {
            return null;
        }
    }

    public static List<ErrorDTO> getErrors(List<ObjectError> allErrors) {
        var dto = new ArrayList<ErrorDTO>();
        for (var error : allErrors) {
            var fieldName = getStringField(error.getArguments()[0], "defaultMessage");
            fieldName = (fieldName.equals("")) ? "object" : fieldName;
            dto.add(new ErrorDTO(fieldName, error.getDefaultMessage()));
        }
        return dto;
    }


}
