package com.nordic.dto.mission_result;

import java.util.Date;

import lombok.Data;

@Data
public class PointHistoryDto {
	private int point_no;
	private String make_gubun;
	private String member_code;
	private int point;
	private int mission_history_no;
	private int request_no;
	private char use_yn;
	private String remark;
	private String create_member;
	private Date create_date;
	private String update_member;
	private Date update_date;
}	
