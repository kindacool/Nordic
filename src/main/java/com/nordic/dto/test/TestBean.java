package com.nordic.dto.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestBean {

    private String MEMBER_CODE;
    private String MEMBER_NAME;
    private String MOBILE_NO;
    private String ADDRESS;
    private int AGE;
    private String SEX;
    private char AGREE_YN;
    private String PASSWORD;
    private char APPROVAL_YN;
    private Date APPROVAL_DATE;
    private char STOP_YN;
    private Date STOP_DATE;
    private char ADMIN_YN;
    private String REMARK;
    private String CREATE_MEMBER;
    private Date CREATE_DATE;
    private String UPDATE_MEMBER;
    private Date UPDATE_DATE;
}
