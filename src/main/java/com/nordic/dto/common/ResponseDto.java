package com.nordic.dto.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ResponseDto<T> {

    private final boolean success = true;
    private final String error = null;
    private String message;
    private T data;
    
    public ResponseDto(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
