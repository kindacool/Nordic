package com.nordic.repository.report;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.nordic.dto.report.ReplyDto;
import com.nordic.dto.report.TopCommentMemberDto;

@Mapper
public interface TopCommentMemberMapper {
	
	/* 조회-최대 댓글 쓴 유저 */
	List<TopCommentMemberDto> getTopCommentMember();
	
	/* 댓글 상세 정보 */
	List<ReplyDto> getCommentList(String create_member);
	
}
