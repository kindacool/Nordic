package com.nordic.exception;

public class CancleRequestException extends RuntimeException{
	
    public static final String ERR_0004 = "0004,이미 취소된 요청입니다";
    public static final String ERR_0005 = "0005,이미 처리된 요청은 취소하실 수 없습니다";
    public static final String ERR_0006 = "0006,취소된 요청은 수락 또는 거절하실수없습니다";
    
    public CancleRequestException(String msg) {
        super(msg);
    }
}
