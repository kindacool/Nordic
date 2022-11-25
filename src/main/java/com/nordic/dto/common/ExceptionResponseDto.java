package com.nordic.dto.common;

import lombok.Data;

@Data
public class ExceptionResponseDto<T> {

    private final boolean success = false;
    private String error;
    private String message;
    private final T data = null;

    public ExceptionResponseDto(String error, String message) {
        this.error = error;
        this.message = message;
    }
}
