package com.nordic.dto.requests;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Alias("acceptedreq")
public class AcceptedRequestsDto {
	private int request_no;
	@JsonFormat(pattern = "yyyy-MM-dd a HH:mm:ss", timezone = "Asia/Seoul")
	private Timestamp create_date;
	private int member_code;
	private String member_name;
	private int goods_no;
	private String goods_name;
	private int point;
	private String confirm_member;
}
