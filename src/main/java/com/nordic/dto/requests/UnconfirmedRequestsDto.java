package com.nordic.dto.requests;


import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Alias("unconfirmedreq")
public class UnconfirmedRequestsDto extends GoodsReqDto{
	private String member_name;
	private String goods_name;
	private int available_point;
}
