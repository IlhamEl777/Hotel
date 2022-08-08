package com.hotel.dto.utility;

import lombok.Data;

@Data
public class RestResponse<T> {
    private final T data;
    private final String message;
    private final Integer status;
}
