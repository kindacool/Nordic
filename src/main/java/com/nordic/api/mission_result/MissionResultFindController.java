package com.nordic.api.mission_result;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.mission_result.MissionHistoryDetailDto;
import com.nordic.dto.mission_result.MissionStatusDto;
import com.nordic.dto.mission_result.PointHistoryDto;
import com.nordic.service.mission_result.MissionResultFindService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import springfox.documentation.spring.web.json.Json;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/mission/result")
public class MissionResultFindController {
	
	private final MissionResultFindService mrfservice;
	
	/* 미승인된 미션 수 */
	@GetMapping("/count")
	public ResponseDto countNewMission() {
		int count = mrfservice.countNewResult();
		return new ResponseDto("미승인된 미션 수",count);
	}
	
	/* 미션 수행 등록 리스트 */
	@GetMapping("/{page}/{checked}")
	public ResponseDto getMissionList(@PathVariable int page, 
									  @PathVariable Boolean checked,
									  @RequestParam(required = false) String start_date ,
									  @RequestParam(required = false) String end_date) {
		
		String message = "";
		
		if(checked == true) {
			message = "미승인건 미션 리스트 출력 완료";
		} else {
			message = "미션 현황 리스트 출력 완료";
		}
		
		List<MissionStatusDto> missionList = mrfservice.findAllMission(page, checked, start_date, end_date);
		return new ResponseDto(message, PageInfo.of(missionList));
	}
	
	/* 미션 수행 상세 정보 */
	@GetMapping("/{mission_history_no}/detail")
	public ResponseDto getMissionDetail(@PathVariable int mission_history_no) {
		MissionHistoryDetailDto detail = mrfservice.findOneMission(mission_history_no);
		return new ResponseDto("미션 상세 정보 출력 완료",detail);
	}
	
	/* 미션 승인 거절 체크 후 저장 */
	@PostMapping("/{mission_history_no}/{check}")
	public ResponseDto confirmMission(@PathVariable int mission_history_no, 
									  @PathVariable int check,
									  @RequestBody String remark)  throws Exception {
		
		String member_code = "MASTER";
		return mrfservice.confirmMission(mission_history_no, member_code, check, remark);
	}
	
	/* 포인트 지급 */
	@PostMapping("/{mission_history_no}/point/add")
	public ResponseDto addPoint(@PathVariable int mission_history_no){
		
		String member_code = "MASTER";
		PointHistoryDto point_history = mrfservice.addPoint(mission_history_no, member_code);
		return new ResponseDto("포인트 지급 완료", point_history);
	}
	
	/* 이미지 불러오기 */
	@GetMapping( value="/image/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] ImageView(@PathVariable String fileName) throws Exception {
		System.out.println("fileName = "+fileName);
		String res = System.getProperty("user.dir") + "/src/main/resources/static/img/mission/"+fileName;
		InputStream in = new FileInputStream(res);
		return IOUtils.toByteArray(in);
	}
}
