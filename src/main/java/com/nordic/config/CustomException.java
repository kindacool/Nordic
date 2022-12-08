package com.nordic.config;

public class CustomException extends Exception {

    public static final String ERR_0001 = "0001,알 수 없는 문제가 발생했습니다. 잠시 후에 다시 시도해주세요.";
    public static final String ERR_0002 = "0002,알 수 없는 문제가 발생했습니다. 잠시 후에 다시 시도해주세요.";

    public static final String ERR_1201 = "1201,비밀번호 변경이 실패하였습니다.";
    public static final String ERR_8977 = "8977,기존 비밀번호와 새 비밀번호가 일치합니다";

    public static final String ERR_8999 = "8999,아이디 또는 비밀번호를 확인해주세요";
    public static final String ERR_8900 = "8900,비밀번호를 확인해주세요";

    public static final String ERR_9998 = "9998,정의되지않은 에러코드.";
    public static final String ERR_9999 = "9999,서버 에러입니다.";

    public static final String NOT_FOUND_MEMBER = "3002,회원 데이터가 없습니다.";

    public static final String ERR_9787 = "9787,일치하는 정보가업습니다.";
    public static final String ERR_9788 = "9788,토큰 인증정보가 없습니다.";

    public static final String PC04_A15 = "0415,인증정보가 일치하지 않습니다.";
    public static final String NOT_FOUND_TEST = "NOT_FOUND_TEST,테스트 데이터를 찾을 수 없습니다.";

    public CustomException (String msg) {
        super(msg);
    }
	

}

