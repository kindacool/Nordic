package com.nordic.exception;

public class ImageInvalidFormatException extends Exception{
    public static final String ERR_0006 = "0006,사진 파일만 입력 가능합니다";
    public static final String ERR_0007 = "0007,크기가 1MB 이하인 사진만 입력 가능합니다";

    public ImageInvalidFormatException(String msg) {
        super(msg);
    }
}
