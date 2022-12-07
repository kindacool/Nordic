package com.nordic.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.nordic.config.CustomException;
import com.nordic.dto.common.ExceptionResponseDto;


@Slf4j
@RestControllerAdvice(annotations = {RestController.class, Service.class})
public class ControllerAdvice {

    @ExceptionHandler(value = {Exception.class, CustomException.class})
    @ResponseStatus
    public ResponseEntity<ExceptionResponseDto> errorHandler(Exception e) {
        ExceptionResponseDto exceptionResponseDto ;
        System.out.println("e = " + e);
        try {
            String[] errSplit = e.getMessage().split(",");
            
            if (errSplit.length >1)
                throw new Exception(e.getMessage());
            exceptionResponseDto = new ExceptionResponseDto(errSplit[0], errSplit[1]);
 
        }
        catch (Exception innerE){
            exceptionResponseDto = new ExceptionResponseDto("XXXXXX", e.getMessage());
        }

        System.out.println("exceptionResponseDto = " + exceptionResponseDto);
        return new ResponseEntity(exceptionResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(NoHandlerFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<ExceptionResponseDto> handleNoHandlerFoundException(NoHandlerFoundException e) {
//
//        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto("BAD_REQUEST", e.getMessage());
//        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ResponseEntity<ExceptionResponseDto> handleUnauthorizedException(NoHandlerFoundException e) {
//
//        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto("Unauthorized", e.getMessage());
//        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.UNAUTHORIZED);
//    }
}
