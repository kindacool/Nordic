package com.nordic.exception;

public class NotConfirmException extends RuntimeException{
	public static final String MRF_0001 = "처리 완료된 미션입니다.";
	public static final String MRF_0002 = "거절 사유 미입력";
			
	public NotConfirmException(String msg) {
		super(msg);
	}
}
