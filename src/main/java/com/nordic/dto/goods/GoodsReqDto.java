package com.nordic.dto.goods;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

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
	private Timestamp create_date;
	private String update_member;
	private Timestamp update_date;
}
