package com.nordic.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.nordic.dto.report.TopCommentMemberDto;
import com.nordic.repository.report.TopCommentMemberDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopCommentMemberService {
	
	private final TopCommentMemberDao tcmdao;
	
	/* 조회-최대 댓글 쓴 유저 */
	public List<String> getTopCommentMember(Map date) {
		return tcmdao.getTopCommentMember(date);
	}
	
}
