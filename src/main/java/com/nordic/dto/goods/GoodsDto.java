package com.nordic.dto.goods;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("goods")
public class GoodsDto {
	private int goods_no;
	private String goods_name;
	private int point;
	private String goods_desc;
	private String image1;
	private String image2;
	private String image3;
	private String image4;
	private String image5;
	private char use_yn;
	private String remark;
	private String create_member;
	private Timestamp create_date;
	private String update_member;
	private Timestamp update_date;
	
	private byte[] byte_image;
	
}
