package com.nordic.dto.member;

import org.apache.ibatis.type.Alias;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("SearchDto")
@Alias("SearchDto")
public class SearchDto {
	
	private String search;
	private String keyword;

}
