package com.nordic.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nordic.dto.report.ReplyDto;
import com.nordic.dto.report.TopCommentMemberDto;
import com.nordic.repository.report.TopCommentMemberDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopCommentMemberService {
	
	private final TopCommentMemberDao tcmdao;
	
	/* 조회-최대 댓글 쓴 유저 */
	public List<TopCommentMemberDto> getTopCommentMember() {
		List<TopCommentMemberDto> userList = tcmdao.getTopCommentMember();
		return userList;
	}

	public List<ReplyDto> getCommentList(int page, String member_code) {
		PageHelper.startPage(page,10);
		List<ReplyDto> reply = tcmdao.getCommentList(member_code);
		return reply;
	}
	
}
