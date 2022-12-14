package com.nordic.api;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.nordic.config.CustomException;
import com.nordic.dto.common.ExceptionResponseDto;
import com.nordic.exception.NoBalanceException;
import com.nordic.exception.ExpireException;
import com.nordic.exception.NotConfirmException;

import lombok.extern.slf4j.Slf4j;


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
    
  @ExceptionHandler(NoBalanceException.class)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<ExceptionResponseDto> handleNoBalanceException(NoBalanceException e) {

      ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto("NoBalance", e.getMessage());
      return new ResponseEntity<>(exceptionResponseDto, HttpStatus.OK);
  }
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ExceptionResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
	    String errorMessage = e.getBindingResult()
	    	    .getAllErrors()
	    	    .get(0)
	    	    .getDefaultMessage();
	  System.out.println(errorMessage);

      ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto("빈값 허용 불가", errorMessage);
      return new ResponseEntity<>(exceptionResponseDto, HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(FileNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ExceptionResponseDto> handleNoBalanceException(FileNotFoundException e) {

      ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto("파일을 찾을 수 없습니다", e.getMessage());
      return new ResponseEntity<>(exceptionResponseDto, HttpStatus.NOT_FOUND);
  }

	  @ExceptionHandler(NoHandlerFoundException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  public ResponseEntity<ExceptionResponseDto> handleNoHandlerFoundException(NoHandlerFoundException e) {
	
	      ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto("BAD_REQUEST", e.getMessage());
	      return new ResponseEntity<>(exceptionResponseDto, HttpStatus.BAD_REQUEST);
	  }
	
	  @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
	  @ResponseStatus(HttpStatus.UNAUTHORIZED)
	  public ResponseEntity<ExceptionResponseDto> handleUnauthorizedException(NoHandlerFoundException e) {
	
	      ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto("Unauthorized", e.getMessage());
	      return new ResponseEntity<>(exceptionResponseDto, HttpStatus.UNAUTHORIZED);
	  }
	  
	  @ExceptionHandler(NotConfirmException.class)
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  public ResponseEntity<ExceptionResponseDto> handleNotConfirmException(NotConfirmException e) {
		  ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto("NotConfirm", e.getMessage());
		  return new ResponseEntity<>(exceptionResponseDto, HttpStatus.BAD_REQUEST);
	  }
	  
	  @ExceptionHandler(ExpireException.class)
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  public ResponseEntity<ExceptionResponseDto> handleExpireException(ExpireException e) {
		  ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto("ExpireMission", e.getMessage());
		  return new ResponseEntity<>(exceptionResponseDto, HttpStatus.BAD_REQUEST);
	  }
}
