package com.nordic.config;

public class CustomException extends Exception {

    public static final String ERR_1234 = "1234,잔액이 부족합니다";

    public CustomException(String msg) {
        super(msg);
    }
}