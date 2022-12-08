package com.nordic.api.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.report.TopCommentMemberDto;
import com.nordic.service.report.TopCommentMemberService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class TopCommentMemberController {
	
	private final TopCommentMemberService tcmservice;
	
	/* 조회-최대 댓글 쓴 유저 */
	@GetMapping("/users/top-comment")
	public ResponseDto getTopCommentMember(@RequestParam String start_date, @RequestParam String end_date) {
		Map date = new HashMap();
		System.out.println(start_date);
		System.out.println(end_date);
		date.put("start_date", start_date);
		date.put("end_date", end_date);
		List<String> top_list = tcmservice.getTopCommentMember(date);
		return new ResponseDto("해당 기간 내에 최대 댓글 작성 유저 리스트",top_list);
	}
	
}
