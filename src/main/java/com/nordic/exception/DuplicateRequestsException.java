package com.nordic.exception;

public class DuplicateRequestsException extends RuntimeException{

    public static final String ERR_0003 = "0003,중복 요청입니다";

    public DuplicateRequestsException(String msg) {
        super(msg);
    }
}
