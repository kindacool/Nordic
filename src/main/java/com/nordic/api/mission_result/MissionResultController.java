package com.nordic.api.mission_result;

import java.io.IOException;
import java.util.Map;

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
import com.nordic.service.mission_result.MissionResultService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mission/result")
public class MissionResultController {

	private final MissionResultService mrservice;

	/* 미션 상세 정보 (미션 등록폼) */
	@GetMapping("/{mission_no}")
	public ResponseDto uploadResltForm(@PathVariable int mission_no) {
		String member_code = "7";
		MissionHistoryDetailDto mission = mrservice.getDetail(mission_no, member_code);
		
		return new ResponseDto("미션 상세 정보", mission);
	}
	
	/* 미션 수행 등록  */
	@PostMapping("/{mission_no}")
	public ResponseDto uploadResult(@PathVariable int mission_no, @RequestParam MultipartFile file) throws IOException {
		String member_code = "7";
		Map mission = mrservice.uploadResult(member_code, mission_no, file);
		
		return new ResponseDto("미션 사진 등록 성공",mission);
	}
	
	/* 미션 수행 사진 수정  */
	@PutMapping("/{mission_no}")
	public ResponseDto updateResult(@PathVariable int mission_no, @RequestParam MultipartFile file) throws IOException {
		String member_code = "7";
		MissionHistoryImageDto mission = mrservice.updateResult(member_code, mission_no, file);
		
		return new ResponseDto("미션 사진 수정 성공",mission);
	}
}
