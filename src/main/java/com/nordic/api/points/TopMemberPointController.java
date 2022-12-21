package com.nordic.api.points;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nordic.dto.common.ResponseDto;
import com.nordic.service.points.TopMemberPointService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/points")
public class TopMemberPointController {
	
	private final TopMemberPointService topMemberPointService;
	
	@ApiOperation(value = "포인트 최다 적립 TOP 10")
	@GetMapping ("/top10")
	public ResponseDto TopRanking () throws Exception {
		
		log.info("진입");
		Map<String, Object> topRankingObj = new HashMap<>();
		topRankingObj.put("data", topMemberPointService.topRanking());
		ResponseDto result = new ResponseDto<>("TOP 10 구하기 성공", topRankingObj.get("data"));
		
		log.info("result : "+ result);
		
		return result;
		
	}
	

}
