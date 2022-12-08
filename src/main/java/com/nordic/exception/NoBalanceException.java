package com.nordic.exception;

public class NoBalanceException extends RuntimeException{

    public static final String ERR_0001 = "0001,잔액이 부족합니다";

    public NoBalanceException(String msg) {
        super(msg);
    }
}
