package com.nordic.dto.goods;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Alias("goods")
public class GoodsDto {
	private int goods_no;
	@NotBlank(message="굿즈명을 입력해주세요")
	private String goods_name;
	@Positive(message="포인트는 0 보다 커야합니다")
	private int point;
	@NotBlank(message="상세설명을 입력해주세요")
	private String goods_desc;
	private String image1;
	private String image2;
	private String image3;
	private String image4;
	private String image5;
	private char use_yn;
	private String remark;
	private String create_member;
	@JsonFormat(pattern = "yyyy-MM-dd a HH:mm:ss", timezone = "Asia/Seoul")
	private Timestamp create_date;
	private String update_member;
	@JsonFormat(pattern = "yyyy-MM-dd a HH:mm:ss", timezone = "Asia/Seoul")
	private Timestamp update_date;
	
}
