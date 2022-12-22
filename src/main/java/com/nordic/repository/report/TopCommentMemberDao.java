package com.nordic.repository.report;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.nordic.dto.report.ReplyDto;
import com.nordic.dto.report.TopCommentMemberDto;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TopCommentMemberDao {
	
	private final TopCommentMemberMapper tcmmapper;
	
	/* 조회-최대 댓글 쓴 유저 */
	public List<TopCommentMemberDto> getTopCommentMember() {
		return tcmmapper.getTopCommentMember();
	}

	public List<ReplyDto> getCommentList(String member_code) {
		return tcmmapper.getCommentList(member_code);
	}
	
	

}
