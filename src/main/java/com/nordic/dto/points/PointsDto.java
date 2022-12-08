package com.nordic.dto.points;


import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Alias("points")
public class PointsDto {
	private int point_no;
	private String make_gubun;
	private String member_code;
	private int point;
	private int mission_history_no;
	private int request_no;
	private char use_yn;
	private String remark;
	private String create_member;
	@JsonFormat(pattern = "yyyy-MM-dd a HH:mm:ss", timezone = "Asia/Seoul")
	private Timestamp create_date;
	private String update_member;
	@JsonFormat(pattern = "yyyy-MM-dd a HH:mm:ss", timezone = "Asia/Seoul")
	private Timestamp update_date;
}
