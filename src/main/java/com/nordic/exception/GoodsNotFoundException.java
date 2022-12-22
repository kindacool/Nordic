package com.nordic.exception;

public class GoodsNotFoundException extends RuntimeException{
	
    public static final String ERR_0002 = "0002,없는 상품입니다";
    public static final String ERR_0010 = "0010,이미 삭제된 상품입니다";

    public GoodsNotFoundException(String msg) {
        super(msg);
    }
}
