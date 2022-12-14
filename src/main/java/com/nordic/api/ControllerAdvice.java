package com.nordic.api;

import org.apache.ibatis.binding.BindingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import com.nordic.config.CustomException;
import com.nordic.dto.common.ExceptionResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//RestController 에서 발생하는 예외를 처리하는 핸들러 클래스
@RestControllerAdvice(annotations = { RestController.class, Service.class } )
public class ControllerAdvice {
	
	@ExceptionHandler(value = {Exception.class, CustomException.class} )
	@ResponseStatus
	public ResponseEntity<ExceptionResponseDto> errorHandler (Exception e) {
		
		ExceptionResponseDto exceptionResponseDto;
		log.info("e : "+e);
		System.out.println("e : " + e);
		
		try {
			String[] errSplit = e.getMessage().split(",");
			if (errSplit.length > 1) {
				throw new Exception (e.getMessage());
			}
			
			log.info("common exception");
			exceptionResponseDto = new ExceptionResponseDto(errSplit[0], errSplit[1]);
			
		} catch (Exception innerE) {
			exceptionResponseDto = new ExceptionResponseDto<>("에러입니다", e.getMessage());
		}
		
		System.out.println("exceptionResponseDto = " + exceptionResponseDto);
		return new ResponseEntity(exceptionResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	  @ExceptionHandler(NoHandlerFoundException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  public ResponseEntity<ExceptionResponseDto> handleNoHandlerFoundException(NoHandlerFoundException e) {
		  
		  log.info("NoHandlerFoundException");
	      ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto("BAD_REQUEST", e.getMessage());
	      return new ResponseEntity<>(exceptionResponseDto, HttpStatus.BAD_REQUEST);
	  }
	
	  @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
	  @ResponseStatus(HttpStatus.UNAUTHORIZED)
	  public ResponseEntity<ExceptionResponseDto> handleUnauthorizedException(NoHandlerFoundException e) {
	
		  log.info("UNAUTHORIZED");
	      ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto("Unauthorized", e.getMessage());
	      return new ResponseEntity<>(exceptionResponseDto, HttpStatus.UNAUTHORIZED);
	  }
	  
	  @ExceptionHandler(BindingException.class)
	  public ResponseEntity<ExceptionResponseDto> handleBeanPropertyBindingResult (BindingException e) {
		  
		  log.info("BindingException");
		  ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto("값을 입력해주세요", e.getMessage());
		  return new ResponseEntity<>(exceptionResponseDto, HttpStatus.METHOD_NOT_ALLOWED);
	  }
}
