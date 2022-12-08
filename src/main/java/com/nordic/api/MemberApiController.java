package com.nordic.api;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.nordic.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@WebServlet
@CrossOrigin
@RequestMapping("api/member")
public class MemberApiController {
	
	private final MemberService memberService;
	
	/******************************** 회원 전체목록 조회 ********************************/
	@ApiOperation(value = "회원 전체목록 조회")
	@GetMapping("/members/{pageNum}")
	public ResponseDto MemberDtoList(@PathVariable int pageNum) throws Exception {
		
		Map<String, Object> memberObj = new HashMap<>();
		memberObj.put("data", memberService.findAll(pageNum));
		ResponseDto result = new ResponseDto("회원 전체목록 구하기 성공", memberObj.get("data"));
		
		log.info("result: "+result);
		log.info("회원 전체목록 조회 완료");
		
		return result;
	}
	
	/******************************** 전체포인트 오름차순 정렬 ********************************/
	@ApiOperation("전체포인트 오름차순 정렬")
	@GetMapping(value="/members/totalAsc/{pageNum}")
	public ResponseDto TotalPointAsc(@PathVariable int pageNum) throws Exception {
		
		Map<String, Object> memberObj = new HashMap<>();
		memberObj.put("data", memberService.totalPointAsc(pageNum));
		ResponseDto result = new ResponseDto("전체포인트 오름차순 정렬 성공", memberObj.get("data"));
		
		log.info("result: "+result);
		log.info("전체포인트 오름차순 정렬 완료");
		
		return result;
		
	}
	
	/******************************** 전체포인트 내림차순 정렬 ********************************/
	@ApiOperation("전체포인트 내림차순 정렬")
	@GetMapping(value="/members/totalDesc/{pageNum}")
	public ResponseDto TotalPointDesc(@PathVariable int pageNum) throws Exception {
		
		Map<String, Object> memberObj = new HashMap<>();
		memberObj.put("data", memberService.totalPointDesc(pageNum));
		ResponseDto result = new ResponseDto("전체포인트 내림차순 정렬 성공", memberObj.get("data"));
		
		log.info("result: "+result);
		log.info("전체포인트 내림차순 정렬 완료");
		
		return result;
		
	}
	
	/******************************** 가용포인트 오름차순 정렬 ********************************/
	@ApiOperation("가용포인트 오름차순 정렬")
	@GetMapping(value="/members/reqAsc/{pageNum}")
	public ResponseDto ReqPointAsc(@PathVariable int pageNum) throws Exception {
		
		Map<String, Object> memberObj = new HashMap<>();
		memberObj.put("data", memberService.reqPointAsc(pageNum));
		ResponseDto result = new ResponseDto("가용포인트 오름차순 정렬 성공", memberObj.get("data"));
		
		log.info("result: "+result);
		log.info("가용포인트 오름차순 정렬 완료");
		
		return result;
		
	}
	
	/******************************** 가용포인트 내림차순 정렬 ********************************/
	@ApiOperation("가용포인트 내림차순 정렬")
	@GetMapping(value="/members/reqDesc/{pageNum}")
	public ResponseDto ReqPointDesc (@PathVariable int pageNum) throws Exception {
		
		Map<String, Object> memberObj = new HashMap<>();
		memberObj.put("data", memberService.reqPointDesc(pageNum));
		ResponseDto result = new ResponseDto("가용포인트 내림차순 정렬 성공", memberObj.get("data"));
		
		log.info("result: "+result);
		log.info("가용포인트 내림차순 정렬 완료");
		
		return result;
		
	}
	
	/******************************** 사용포인트 오름차순 정렬 ********************************/
	@ApiOperation("사용포인트 오름차순 정렬")
	@GetMapping(value="/members/useAsc/{pageNum}")
	public ResponseDto UsePointAsc(@PathVariable int pageNum) throws Exception {
		
		Map<String, Object> memberObj = new HashMap<>();
		memberObj.put("data", memberService.usePointAsc(pageNum));
		ResponseDto result = new ResponseDto("사용포인트 오름차순 정렬 성공", memberObj.get("data"));
		
		log.info("result: "+result);
		log.info("사용포인트 오름차순 정렬 완료");
		
		return result;
		
	}
	
	/******************************** 사용포인트 내림차순 정렬 ********************************/
	@ApiOperation("사용포인트 내림차순 정렬")
	@GetMapping(value="/members/useDesc/{pageNum}")
	public ResponseDto UeqPointDesc(@PathVariable int pageNum) throws Exception {
		
		Map<String, Object> memberObj = new HashMap<>();
		memberObj.put("data", memberService.usePointDesc(pageNum));
		ResponseDto result = new ResponseDto("사용포인트 내림차순 정렬 성공", memberObj.get("data"));
		
		log.info("result: "+result);
		log.info("사용포인트 내림차순 정렬 완료");
		
		return result;
		
	}
	
	/******************************** 가입 승인 회원 조회  ********************************/
	@ApiOperation("가입 승인 회원 조회")
	@GetMapping(value="/members/approvalY/{pageNum}")
	public ResponseDto approvalYList (@PathVariable int pageNum) throws Exception {
		
		Map<String, Object> memberObj = new HashMap<>();
		memberObj.put("data", memberService.approvalYList(pageNum));
		ResponseDto result = new ResponseDto("가입 승인 회원 조회 성공", memberObj.get("data"));
		
		log.info("result: "+result);
		log.info("가입 승인 회원 조회 완료");
		
		return result;
		
	}
	
	/******************************** 가입 미승인 회원 조회  ********************************/
	@ApiOperation("가입 미승인 회원 조회")
	@GetMapping(value="/members/approvalN/{pageNum}")
	public ResponseDto approvalNList (@PathVariable int pageNum) throws Exception {
		
		Map<String, Object> memberObj = new HashMap<>();
		memberObj.put("data", memberService.approvalNList(pageNum));
		ResponseDto result = new ResponseDto("가입 미승인 회원 조회 성공", memberObj.get("data"));
		
		log.info("result: "+result);
		log.info("가입 미승인 회원 조회 완료");
		
		return result;
		
	}
	
	/******************************** 관리자 전체목록 조회 ********************************/
	@ApiOperation(value="관리자 조회")
	@GetMapping("/admins/{pageNum}")
	public ResponseDto FindAdmins (@PathVariable int pageNum) throws Exception {
		
		Map<String, Object> adminObj = new HashMap<>();
		adminObj.put("data", memberService.findAdmins(pageNum));
		ResponseDto result = new ResponseDto("관리자 목록 구하기 성공", adminObj.get("data"));
		
		log.info("result: "+result);
		log.info("관리자 전체목록 조회 완료");
		
		return result;
	}
	
	/******************************** 특정 회원 1명 조회 ********************************/
	@ApiOperation(value="특정 회원 1명 조회")
	@GetMapping("/{member_code}")
	public ResponseDto FindOne (@PathVariable String member_code) throws Exception {
		
		MemberDto memberDto = memberService.findOne(member_code);
		ResponseDto result = new ResponseDto("회원"+memberDto.getMember_name()+" 정보 : ", memberDto);
		
		log.info("result: "+result);
		log.info(memberDto.getMember_code()+" 조회하기");
		
		return result;
	}
	
	/******************************** 회원정보수정 폼 이동 ********************************/
	@ApiOperation(value = "회원 정보 수정 폼")
	@GetMapping(value="/modifyForm/{member_code}")
	public ResponseDto ModifyForm (@PathVariable String member_code) throws Exception {
		
		//특정 회원 정보 db에서 찾아오기
		MemberDto memberDto = memberService.findOne(member_code);
		ResponseDto result = new ResponseDto("회원 "+memberDto.getMember_name()+" 정보 구하기 성공", memberDto);
		
		log.info("result: "+result);
		log.info(memberDto.getMember_code()+" 수정 폼 실행");
		
		return result;
	}
	
	/******************************** 회원정보수정 실행 ********************************/
	@ApiOperation(value = "회원 정보 수정")
	@PostMapping(value="/modify/{mC}")
	public ResponseDto ModifyOne (@PathVariable(value = "mC") String mC, 
								  @RequestBody MemberModifyDto memberModifyDto) 
										  						throws Exception {	// json을 Dto 형태로 받기
				
		log.info("프론트에서 넘어온 수정할 정보↓");
		log.info(memberModifyDto.toString());
//		
		//memberDto.getMember_name()으로 update SQL문 실행하기
		int result = memberService.modifyOne(memberModifyDto);
		ResponseDto responseResult = new ResponseDto(memberModifyDto.getMember_code()+" 수정 완료", result);
		
		log.info("mC: "+mC);
		log.info("responseResult: "+responseResult);
		
		return responseResult;
		
	}
	
	/******************************** 회원 탈퇴 (stop_yn, stop_date) ********************************/
	@ApiOperation(value = "회원 탈퇴")
	@PostMapping(value="/del/{member_code}")
	public ResponseDto DelOne (@PathVariable String member_code) throws Exception {
		
		log.info("진입");
		
		//회원 탈퇴 실행 - stop_yn, stop_date 컬럼 변경 update SQL문 실행
		MemberDto memberDto = memberService.findOne(member_code);
		int result = memberService.delOne(memberDto);
		ResponseDto responseResult = new ResponseDto(memberDto.getMember_name()+ " 탈퇴 완료" , result);
		
		return responseResult;
	}
	
	
	/******************************** 회원가입 폼 이동 ********************************/
	@ApiOperation(value = "회원가입폼 이동")
	@GetMapping("/registerForm")
	public String RegisterForm ( ) throws Exception {
		return null;	// 뷰 페이지와 연결을 어떻게??
	}
	
	/******************************** 아이디 중복 검사 ********************************/
	@ApiOperation(value = "아이디 중복 검사")
	@GetMapping("/registerForm/idCheck/{member_code}")
	public ResponseDto IdCheck (@PathVariable String member_code) throws Exception {
		
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
	@ApiOperation(value = "회원가입 실행")
	@PostMapping(value="/register")
	public ResponseDto NordicRegister(@RequestBody MemberDto memberDto) 
														throws Exception {
		
		int mbrRegister = memberService.mbrRegister(memberDto);
		log.info("회원이름 : " + memberDto.getMember_name());
		
		return new ResponseDto ("회원가입성공", memberDto.getMember_name());
	}
	
	/******************************** 관리자 등록  ********************************/
	/***************admin_yn, admin_date, update_member, update_date  ********/

	
//	@ApiOperation(value = "관리자 등록")
//	@PostMapping(value= "/members/admRegister/{}")
//	public ResponseDto admRegister (MemberDto memberDto)
	
//	@ApiOperation(value="검색으로 회원 조회")
//	@GetMapping("/{keyword}")
//	public ResponseDto SearchMember(@PathVariable SearchDto searchDto) {
//		log.info("serachMember");
//		
//		List<MemberDto> memberDto = memberService.searchMember(searchDto);
//		
//		return new ResponseDto();
//		
//	}
	
}
