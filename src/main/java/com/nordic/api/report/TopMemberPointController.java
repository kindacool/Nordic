package com.nordic.api.report;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nordic.dto.common.ResponseDto;
import com.nordic.service.report.TopMemberPointService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Api(tags = {"TopMemberPointController"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/report")
public class TopMemberPointController {
	
	private final TopMemberPointService topMemberPointService;
	
	@ApiOperation(value = "최다 적립 회원 TOP 10", 
				  notes = "포인트를 가장 많이 적립한 회원 상위 10명의 리스트를 출력한다.")
	@GetMapping ("/top10")
	public ResponseDto TopRanking () throws Exception {
		
		Map<String, Object> topRankingObj = new HashMap<>();
		topRankingObj.put("data", topMemberPointService.topRanking());
		
		ResponseDto result = new ResponseDto<>("TOP 10 구하기 성공", topRankingObj.get("data"));
		
		log.info("result : "+ result);
		
		return result;
		
	}
	

}
