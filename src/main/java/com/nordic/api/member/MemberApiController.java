package com.nordic.api.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nordic.dto.common.ExceptionResponseDto;
import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.member.MemberDto;
import com.nordic.dto.member.MemberModifyDto;
import com.nordic.dto.member.SearchDto;
import com.nordic.service.member.MemberService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequiredArgsConstructor
@WebServlet
@CrossOrigin
@RequestMapping("api/member")
public class MemberApiController {
	
//	private final CustomUserDetailsService customeservice;
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	/******************************** 회원 전체 목록 조회 ********************************/
	@ApiOperation(value = "회원 전체목록 조회", notes="모든 회원을 조회할 수 있다.")
	@GetMapping("/members/{pageNum}")
	public ResponseDto MemberDtoList(@ApiParam(value = "페이지 번호")
									 @PathVariable int pageNum) throws Exception {
		
//		String member_code = (String) customerservice.getUserInfo().get("member_code");
		String member_code = (String) memberService.getUserInfo().get("member_code");
		log.info("member_code : " + member_code);
		
		Map<String, Object> memberObj = new HashMap<>();
		memberObj.put("data", memberService.findAll(pageNum));
		ResponseDto result = new ResponseDto("회원 전체목록 구하기 성공", memberObj.get("data"));
		
		log.info("result: "+result);
		log.info("회원 전체목록 조회 완료");
		
		return result;
	}
	
	
	/******************************** 포인트 정렬 ********************************/
	@ApiOperation(value = "포인트 정렬", notes = "전체포인트, 가용포인트, 사용포인트를 오름차순/내림차순 정렬할 수 있다.")
	@ApiImplicitParams({
	@ApiImplicitParam(name = "pointArrange", value = "totalasc, totaldesc, reqasc, reqdesc, useasc, usedesc", required = true),
	@ApiImplicitParam(name = "pageNum", value = "페이지 번호", required = true)})
	@GetMapping(value = "/members/arg/{pointArrange}/{pageNum}")
	public ResponseDto PointArrange (@PathVariable String pointArrange,
									 @PathVariable int pageNum) throws Exception {
		
		log.info(pointArrange);
		Map<String, Object> memberObj = new HashMap<>();
		memberObj.put("data", memberService.pointArrange(pageNum, pointArrange));
		ResponseDto result = new ResponseDto("포인트 정렬 성공", memberObj.get("data"));
		
		return result;
	}
	
	/******************************** 회원 활동 상태별 목록 불러오기  ********************************/
	@ApiOperation(value = "회원 활동 상태", notes = "가입미승인 / 가입승인 / 탈퇴 상태로 나누어 조회할 수 있다.")
	@ApiImplicitParams({
	@ApiImplicitParam(name = "memberState", value = "approvaly, approvaln, delmember", required = true),
	@ApiImplicitParam(name = "pageNum", value = "페이지 번호", required = true)})
	@GetMapping(value = "/members/mst/{memberState}/{pageNum}")
	public ResponseDto MemberState (@PathVariable String memberState,
									@PathVariable int pageNum) throws Exception {
		log.info(memberState);
		Map<String, Object> memberObj = new HashMap<>();
		memberObj.put("data", memberService.memberState(pageNum, memberState));
		ResponseDto result = new ResponseDto("회원 활동 상태 불러오기 성공", memberObj.get("data"));
		
		return result;
	}
	
	/******************************** 가입 승인 실행 ********************************/
	@ApiOperation(value = "가입 승인 실행 (approval_yn = 'Y')", 
				  notes = "가입 미승인 상태의 회원을 가입 승인 상태로 변경할 수 있다.")
	@PostMapping (value = "/members/doApproval/{mC}")
	public ResponseDto DoApproval (@ApiParam(value = "member_code") 
								   @PathVariable(value="mC") String mC) throws Exception {
		
		MemberDto memberDto = memberService.findOne(mC);
		//가입 승인 실행 - approval_yn, approval_date 컬럼 변경 update SQL문 실행
		int result = memberService.doApproval(memberDto);
		ResponseDto responseResult = new ResponseDto(memberDto.getMember_name()+ " 가입 승인 완료" , result);
		
		return responseResult;
		
	}
	
	/******************************** 회원 탈퇴 실행 ********************************/
	@ApiOperation(value = "회원 탈퇴 실행 (stop_yn = 'N')", 
				  notes = "현재 활동 중인 회원을 탈퇴 상태로 컬럼 값을 변경한다.")
	@PostMapping(value="/del/{mC}")
	public ResponseDto DelOne (@ApiParam(value = "member_code")
							   @PathVariable(value="mC") String mC) throws Exception {
		
		log.info("회원 탈퇴 진입");
		
		MemberDto memberDto = memberService.findOne(mC);
		//회원 탈퇴 실행 - stop_yn, stop_date 컬럼 변경 update SQL문 실행
		int result = memberService.delOne(memberDto);
		ResponseDto responseResult = new ResponseDto(memberDto.getMember_name()+ " 탈퇴 완료" , result);
		
		return responseResult;
	}
	
	/******************************** 회원 탈퇴 철회  ********************************/
	@ApiOperation(value = "회원 탈퇴 철회 (stop_yn = 'Y')", 
				  notes = "현재 탈퇴 상태인 회원을 가입 미승인 상태로 컬럼 값을 변경한다.")
	@PostMapping(value="members/undoDelete/{mC}")
	public ResponseDto UndoDelete (@ApiParam(value = "member_code")
								   @PathVariable(value="mC") String mC) throws Exception {
		
		log.info("회원 탈퇴 철회 진입");
		
		MemberDto memberDto = memberService.findOne(mC);
		//회원 탈퇴 실행 - stop_yn, stop_date 컬럼 변경 update SQL문 실행
		int result = memberService.undoDelete(memberDto);
		ResponseDto responseResult = new ResponseDto(memberDto.getMember_name()+ " 탈퇴 철회 완료" , result);
		
		return responseResult;
	}
	
	/******************************** 관리자 승인 실행 ********************************/
	@ApiOperation(value = "관리자 승인 실행 (admin_yn = 'Y')",
				  notes = "가입 승인된 회원을 관리자로 변경한다.")
	@PostMapping (value = "/members/doAdmin/{mC}")
	public ResponseDto DoAdmin (@ApiParam(value = "member_code")
								@PathVariable(value="mC") String mC) throws Exception {
		
		MemberDto memberDto = memberService.findOne(mC);
		//관리자 승인 실행 - admin_yn, admin_date 컬럼 변경 update SQL문 실행
		int result = memberService.doAdmin(memberDto);
		ResponseDto responseResult = new ResponseDto(memberDto.getAdmin_yn()+ " 관리자 승인 완료" , result);
		
		return responseResult;
		
	}
	
	/******************************** 관리자 해제 실행 ********************************/
	@ApiOperation(value = "관리자 해제 실행 (admin_yn = 'N')",
				  notes = "관리자 회원을 가입 승인 상태의 회원으로 변경한다.")
	@PostMapping (value = "/members/doUnadmin/{mC}")
	public ResponseDto DoUnadmin (@ApiParam(value = "member_code")
								  @PathVariable(value="mC") String mC) throws Exception {
		
		log.info("관리자 해제 진입");
		
		MemberDto memberDto = memberService.findOne(mC);

		//관리자 해제 실행 - admin_yn, admin_date 컬럼 변경 update SQL문 실행
		int result = memberService.doUnadmin(memberDto);
		log.info("int result : "+result);
		ResponseDto responseResult = new ResponseDto(memberDto.getAdmin_yn()+ " 관리자 해제 완료" , result);
		log.info("관리자 해제");
		
		return responseResult;
		
	}
	
	/******************************** 관리자 전체목록 조회 ********************************/
	@ApiOperation(value="관리자 조회", notes = "모든 관리자 회원을 조회한다.")
	@GetMapping("/admins/{pageNum}")
	public ResponseDto FindAdmins (@ApiParam("페이지 번호")
								   @PathVariable int pageNum) throws Exception {
		
		Map<String, Object> adminObj = new HashMap<>();
		adminObj.put("data", memberService.findAdmins(pageNum));
		ResponseDto result = new ResponseDto("관리자 목록 구하기 성공", adminObj.get("data"));
		
		log.info("result: "+result);
		log.info("관리자 전체목록 조회 완료");
		
		return result;
	}
	
	/******************************** 특정 회원 1명 조회 ********************************/
	@ApiOperation(value="특정 회원 1명 조회", 
				  notes = "member_code를 이용해 특정 회원 1명을 조회한다.")
	@GetMapping("/{member_code}")
	public ResponseDto FindOne (@ApiParam("member_code")
								@PathVariable String member_code) throws Exception {
		
		MemberDto memberDto = memberService.findOne(member_code);
		ResponseDto result = new ResponseDto("회원"+memberDto.getMember_name()+" 정보 : ", memberDto);
		
		log.info("result: "+result);
		log.info(memberDto.getMember_code()+" 조회하기");
		
		return result;
	}
	
	/******************************** 특정 회원 1명 조회 (사용자)********************************/
	@ApiOperation(value="특정 회원 1명 조회 (사용자)", 
			notes = "member_code를 이용해 특정 회원 1명을 조회한다.")
	@GetMapping("user/{member_code}")
	public ResponseDto FindMe (@ApiParam("member_code")
	@PathVariable String member_code) throws Exception {
		
		MemberDto memberDto = memberService.findMe(member_code);
		ResponseDto result = new ResponseDto("회원"+memberDto.getMember_name()+" 정보 : ", memberDto);
		
		log.info("result: "+result);
		log.info(memberDto.getMember_code()+" 조회하기");
		
		return result;
	}
	
	/******************************** 회원정보수정 폼 이동 ********************************/
	@ApiOperation(value = "회원 정보 수정 폼",
				  notes = "member_code 회원을 조회하여 수정 폼으로 이동한다.")
	@GetMapping(value="/modifyForm/{member_code}")
	public ResponseDto ModifyForm (@ApiParam("member_code")
								   @PathVariable String member_code) throws Exception {
		
		//특정 회원 정보 db에서 찾아오기
		log.info("member_code: "+member_code);
		MemberDto memberDto = memberService.findOne(member_code);
		log.info("member_code로 구해오기 완료");
		ResponseDto result = new ResponseDto("회원 "+memberDto.getMember_name()+" 정보 구하기 성공", memberDto);
		
		log.info("result: "+result);
		log.info(memberDto.getMember_code()+" 수정 폼 실행");
		
		return result;
	}
	
	/******************************** 회원정보수정 실행 ********************************/
	@ApiOperation(value = "회원 정보 수정", notes = "수정 폼에서 입력받은 값으로 회원 정보를 수정한다.")
	@PostMapping(value="/modify/{mC}")
	public ResponseDto ModifyOne (@ApiParam("member_code") @PathVariable(value = "mC") String mC, 
								  @ApiIgnore @RequestBody MemberModifyDto memberModifyDto) 
										  						throws Exception {	// json을 Dto 형태로 받기
				
		log.info("프론트에서 넘어온 수정할 정보↓");
		log.info(memberModifyDto.toString());
		
		String inputPassword = memberModifyDto.getPassword();
		memberModifyDto.setPassword(passwordEncoder.encode(inputPassword));

		//memberDto.getMember_name()으로 update SQL문 실행하기
		int result = memberService.modifyOne(memberModifyDto);
		ResponseDto responseResult = new ResponseDto(memberModifyDto.getMember_code()+" 수정 완료", result);
		
		log.info("mC: "+mC);
		log.info("responseResult: "+responseResult);
		
		return responseResult;
		
	}
	
	/******************************** 아이디 중복 검사 ********************************/
	@ApiOperation(value = "아이디 중복 검사", 
				  notes = "중복되는 member_code가 있는지 조회하여 중복 여부에 따라 값을 반환한다.")
	@GetMapping("/registerForm/idCheck/{member_code}")
	public ResponseDto IdCheck (@ApiParam("member_code")
								@PathVariable String member_code) throws Exception {
		
		log.info("아이디 중복 검사 진입");
		MemberDto memberDto = memberService.findOne(member_code);
		
		if(memberDto != null) {									// DB에 중복 ID가 존재
			ResponseDto result = new ResponseDto("중복 ID 존재", 1);
			log.info("결과:"+result);
			
			return result;
		} 
		else {													// 중복 없음
			ResponseDto result = new ResponseDto("ID 생성 가능", 2);
			log.info("결과:"+result);
			
			return result;
		}
	}
	
	/******************************** 회원가입 실행 ********************************/
	@ApiOperation(value = "회원가입 실행", notes = "폼에서 입력받은 정보로 새로운 회원을 등록한다.")
	@PostMapping(value="/register")
	public ResponseDto NordicRegister(@ApiIgnore @RequestBody MemberDto memberDto) throws Exception {
		
		String inputPassword = memberDto.getPassword();
		log.info("inputPassword : "+inputPassword);
		log.info("memberDto : "+memberDto);
		memberDto.setPassword(passwordEncoder.encode(inputPassword));
		log.info("setPassword : "+memberDto.getPassword());
		
		int mbrRegister = memberService.mbrRegister(memberDto);
		System.out.println("mbrRegister: "+mbrRegister);
		log.info("회원이름 : " + memberDto.getMember_name());
		
		return new ResponseDto ("회원가입성공", memberDto.getMember_name());
	}
	
	/******************************** 검색으로 회원 조회 ********************************/
	@ApiOperation(value="검색으로 회원 조회", 
				  notes = "분류와 키워드를 이용해 페이징 처리 된 검색 결과를 출력한다.")
	
	@ApiImplicitParams({
	@ApiImplicitParam(name = "pageNum", value = "페이지 번호", required = true),
	@ApiImplicitParam(name = "search", value = "member_code, member_name, mobile_no, address, age, sex", required = true),
	@ApiImplicitParam(name = "keyword", value = "키워드", required = true)})
	@GetMapping("/members/{search}/{keyword}/{pageNum}")
	public ResponseDto SearchMember(@PathVariable int pageNum, 
									@PathVariable String search,
									@PathVariable String keyword) throws Exception {
		
		SearchDto searchDto = new SearchDto();
		searchDto.setSearch(search); searchDto.setKeyword(keyword);
		log.info(searchDto.toString());
		
		Map<String, Object> adminObj = new HashMap<>();
		adminObj.put("data", memberService.doSearch(pageNum, searchDto));
		ResponseDto result = new ResponseDto("검색 목록 구하기 성공", adminObj.get("data"));
		
		log.info("result: "+result);
		log.info("검색 목록 구하기 완료");
		
		return result;
		
	}
	
}
