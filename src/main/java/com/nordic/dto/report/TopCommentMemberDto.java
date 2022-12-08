package com.nordic.dto.report;

import java.util.Date;
import lombok.Data;

@Data
public class TopCommentMemberDto {
	private String start_date;
	private String end_date;
	
	private int reply_no; 
	private int board_no; 
	private String reply_desc; 
	private int up_reply_no; 
	private String remark; 
	private String create_member; 
	private Date create_date; 
	private String update_member; 
	private Date update_date;
}
