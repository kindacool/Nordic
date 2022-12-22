package com.nordic.dto.report;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReplyDto {
	private int reply_no; 
	private int board_no;
	private String reply_desc; 
	private int up_reply_no; 
	private String remark; 
	private String create_member; 
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date create_date; 
	private String update_member; 
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date update_date;
	private String board_object;
}
