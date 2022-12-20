package com.nordic.api.report;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nordic.dto.common.ResponseDto;
import com.nordic.service.report.TopCommentMemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class TopCommentMemberController {
	
	private final TopCommentMemberService tcmservice;
	
	/* 조회-최대 댓글 쓴 유저 */
	@GetMapping("/users/top-comment")
	public ResponseDto getTopCommentMember() {
		List<String> top_list = tcmservice.getTopCommentMember();
		return new ResponseDto("해당 기간 내에 최대 댓글 작성 유저 리스트",top_list);
	}
}
