package com.nordic.dto.report;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.nordic.dto.member.MemberDto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("TopMemberPointDto")
@Alias("TopMemberPointDto")
public class TopMemberPointDto {
	
	private String member_code;		// 1. 아이디
	private String member_name;		// 2. 이름
	private int mission_count;		// 3. 미션 수행 횟수
	private int sum_point;			// 4. 총 획득 포인트
	private String start_date;		// 5. 미션 시작일자
	private String end_date;		// 6. 미션 종료일자
	private String refuse_yn;		// 7. 거절 여부 (N)
	
}