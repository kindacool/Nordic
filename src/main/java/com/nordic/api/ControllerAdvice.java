package com.nordic.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.stereotype.Service;

import com.nordic.config.ExceptionConfig;
import com.nordic.dto.common.ExceptionResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//RestController 에서 발생하는 예외를 처리하는 핸들러 클래스
@RestControllerAdvice(annotations = { RestController.class, Service.class } )
public class ControllerAdvice {
	
	@ExceptionHandler(value = {Exception.class, ExceptionConfig.class} )
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
			exceptionResponseDto = new ExceptionResponseDto(errSplit[0], errSplit[1]);
			
		} catch (Exception innerE) {
			exceptionResponseDto = new ExceptionResponseDto<>("에러입니다", e.getMessage());
		}
		
		System.out.println("exceptionResponseDto = " + exceptionResponseDto);
		return new ResponseEntity(exceptionResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}
