package com.nordic.dto.mission_result;

import java.util.Date;

import lombok.Data;

@Data
public class MissionHistoryDto {
	private int mission_history_no;
	private int mission_no;
	private String member_code;
	private char confirm_yn;
	private String confirm_member;
	private int point;
	private char refuse_yn;
	private char use_yn;
	private String remark;
	private String create_member;
	private Date create_date;
	private String update_member;
	private Date update_date;
}
