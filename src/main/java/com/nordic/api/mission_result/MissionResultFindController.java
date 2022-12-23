package com.nordic.api.mission_result;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.util.RequestPayload;
import com.github.pagehelper.PageInfo;
import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.mission_result.MissionHistoryDetailDto;
import com.nordic.dto.mission_result.MissionStatusDto;
import com.nordic.dto.mission_result.PointHistoryDto;
import com.nordic.jwt.TokenProvider;
import com.nordic.service.login.CustomUserDetailsService;
import com.nordic.service.mission_result.MissionResultFindService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.spring.web.json.Json;

@Slf4j
@ApiOperation(value = "미션 등록 현황")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/mission/result")
public class MissionResultFindController {
	
	private final MissionResultFindService mrfservice;
	private final CustomUserDetailsService customeservice;
	
	/* 미승인된 미션 수 */
	@ApiOperation(value = "미승인 미션 수")
	@GetMapping("/count")
	public ResponseDto countNewMission() {
		String member_code = (String) customeservice.getUserInfo().get("member_code");
		int count = mrfservice.countNewResult();
		log.info("=== 미승인된 미션수 : "+count+" ===");
		
		return new ResponseDto("미승인된 미션 수",count);
	}
	
	/* 유저가 등록한 미션 수행 리스트*/
	@ApiOperation(value = "유저가 등록한 미션 수행 리스트", notes = "미승인 건만 조회, 날짜 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name="checked", value="미승인(true) / 전체(false)")
	})
	@GetMapping("")
	public ResponseDto getMissionList(@RequestParam int page, 
									  @RequestParam Boolean checked,
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
	@ApiOperation(value = "미션 수행 상세 정보")
	@GetMapping("/{mission_history_no}")
	public ResponseDto getMissionDetail(@PathVariable int mission_history_no) {
		MissionHistoryDetailDto detail = mrfservice.findOneMission(mission_history_no);
		log.info("=== 상세 정보 출력 ===");
		return new ResponseDto("미션 상세 정보 출력 완료",detail);
	}
	
	/* 미션 승인 거절 체크 후 저장 */
	@ApiOperation(value = "미션 승인/거절")
	@ApiImplicitParams({
		@ApiImplicitParam(name="check", value="승인(1) / 거절(2)"),
		@ApiImplicitParam(name="remark", value="거절사유")
	})
	@ApiResponses({
		@ApiResponse(responseCode = "400", description = "미션 승인 불가")
	})
	@PostMapping("/{mission_history_no}/{check}")
	public ResponseDto confirmMission(@PathVariable int mission_history_no, 
									  @PathVariable int check,
									  @RequestBody String remark)  throws Exception {
		
		log.info("=== 승인(1) / 거절(2) : "+check+" ===");
		String member_code = (String) customeservice.getUserInfo().get("member_code");
		return mrfservice.confirmMission(mission_history_no, member_code, check, remark);
	}
	
	/* 포인트 지급 */
	@ApiOperation(value = "포인트 지급")
	@PostMapping("/point/add")
	public ResponseDto addPoint(@RequestParam int mission_history_no, @RequestParam String member_code){
		System.out.println("============"+mission_history_no);
		System.out.println("============"+member_code);
		PointHistoryDto point_history = mrfservice.addPoint(mission_history_no, member_code);
		
		log.info("=== 포인트 지급 : "+point_history.getPoint()+" ===");
		return new ResponseDto("포인트 지급 완료", point_history);
	}
}
