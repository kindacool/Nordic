package com.nordic.dto.mission_result;

import java.util.Date;

import lombok.Data;

@Data
public class MissionMasterDto {
	private int mission_no;
	private String mission_name; 
	private Date start_date;
	private Date end_date;
	private String level_code;
	private int point;
	private String address1;
	private String address2;
	private char use_yn;
	private String remark;
	private String create_member;
	private Date create_date;
	private String update_member;
	private Date update_date;
}
