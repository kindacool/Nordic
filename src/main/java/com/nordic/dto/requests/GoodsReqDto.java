package com.nordic.dto.requests;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Alias("goodsreq")
public class GoodsReqDto {
	private int request_no;
	private int goods_no;
	private String member_code;
	private char confirm_yn;
	private String confirm_member;
	private int point;
	private char refuse_yn;
	private char use_yn;
	private String remark;
	private String create_member;
	@JsonFormat(pattern = "yyyy-MM-dd a HH:mm:ss", timezone = "Asia/Seoul")
	private Timestamp create_date;
	private String update_member;
	@JsonFormat(pattern = "yyyy-MM-dd a HH:mm:ss", timezone = "Asia/Seoul")
	private Timestamp update_date;
}
