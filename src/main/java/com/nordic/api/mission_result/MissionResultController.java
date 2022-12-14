package com.nordic.api.mission_result;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.mission_result.MissionHistoryDetailDto;
import com.nordic.dto.mission_result.MissionHistoryImageDto;
import com.nordic.jwt.JwtFilter;
import com.nordic.service.login.CustomUserDetailsService;
import com.nordic.service.mission_result.MissionResultService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mission/result")
public class MissionResultController {

	private final MissionResultService mrservice;
	private final CustomUserDetailsService customeservice;

	/* 미션 상세 정보 (미션 등록폼) */
	@ApiOperation(value = "미션 상세 정보")
	@GetMapping("/{mission_no}")
	public ResponseDto uploadResltForm(@PathVariable int mission_no, HttpServletRequest request) {
		log.info("=== 미션 등록창 === ");
		
		String member_code = (String) customeservice.getUserInfo().get("member_code");
		log.info("=== member_code : "+member_code+" ===");
		
		MissionHistoryDetailDto mission = mrservice.getDetail(mission_no, member_code);
		
		return new ResponseDto("미션 상세 정보", mission);
	}
	
	/* 미션 수행 등록  */
	@ApiOperation(value = "미션 수행 등록")
	@PostMapping("/{mission_no}")
	public ResponseDto uploadResult(@PathVariable int mission_no, @RequestParam MultipartFile file) throws IOException {
		log.info("=== 미션 수행 등록 === ");

		String member_code = (String) customeservice.getUserInfo().get("member_code");
		Map mission = mrservice.uploadResult(member_code, mission_no, file);
		
		return new ResponseDto("미션 사진 등록 성공",mission);
	}
	
	/* 미션 수행 사진 수정  */
	@ApiOperation(value = "미션 수행 수정")
	@PutMapping("/{mission_no}")
	public ResponseDto updateResult(@PathVariable int mission_no, @RequestParam MultipartFile file) throws Exception {
		log.info("=== 미션 수행 수정 === ");
		
		String member_code = (String) customeservice.getUserInfo().get("member_code");
		MissionHistoryImageDto mission = mrservice.updateResult(member_code, mission_no, file);
		
		return new ResponseDto("미션 사진 수정 성공",mission);
	}
	
	@ApiOperation(value = "미션 수행 삭제")
	/* 미션 수행 사진 삭제 */
	@DeleteMapping ("/{mission_no}")
	public ResponseDto deleteResult(@PathVariable int mission_no) throws Exception {
		log.info("=== 미션 수행 삭제 === ");
		
		String member_code = (String) customeservice.getUserInfo().get("member_code");
		MissionHistoryImageDto mission = mrservice.deleteResult(member_code, mission_no);
		
		return new ResponseDto("미션 사진 삭제 성공",mission);
	}
}
