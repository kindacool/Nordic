package com.nordic.dto.mission_result;

import java.util.Date;

import lombok.Data;

@Data
public class MissionMasterImageDto {
	private int image_no;
	private int mission_no;
	private String confirm_file;
	private char use_yn;
	private String remark;
	private String create_member;
	private Date create_date;
	private String update_member;
	private Date update_date;
}
