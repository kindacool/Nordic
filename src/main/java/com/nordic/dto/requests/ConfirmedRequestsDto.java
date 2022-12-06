package com.nordic.dto.requests;
import org.apache.ibatis.type.Alias;
import lombok.Data;

@Data
@Alias("confirmedreq")
public class ConfirmedRequestsDto extends GoodsReqDto{
	private String goods_name;
	private String member_name;
}
