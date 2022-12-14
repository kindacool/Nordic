package com.nordic.exception;

public class ExpireException extends RuntimeException {
	public static final String MR_0003 = "마감기한이 지난 미션입니다.";
			
	public ExpireException(String msg) {
		super(msg);
	}
}
