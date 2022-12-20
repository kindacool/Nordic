package com.nordic.repository.report;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.nordic.dto.report.TopCommentMemberDto;

@Mapper
public interface TopCommentMemberMapper {
	
	/* 조회-최대 댓글 쓴 유저 */
	List<String> getTopCommentMember();
	
}
