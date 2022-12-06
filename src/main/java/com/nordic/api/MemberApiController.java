package com.nordic.api;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.log.Log;
import com.nordic.dto.common.ExceptionResponseDto;
import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.member.MemberDto;
import com.nordic.dto.member.MemberModifyDto;
import com.nordic.dto.member.SearchDto;
import com.nordic.service.MemberService;

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
	public ResponseDto FindOne (@PathVariable String member_code) {
		
		MemberDto memberDto = memberService.findOne(member_code);
		ResponseDto result = new ResponseDto("회원"+memberDto.getMember_name()+" 정보 : ", memberDto);
		
		log.info("result: "+result);
		log.info(memberDto.getMember_code()+" 조회하기");
		
		return result;
	}
	
	/******************************** 회원정보수정 폼 이동 ********************************/
	@ApiOperation(value = "회원 정보 수정 폼")
	@GetMapping(value="/modifyForm/{member_code}")
	public ResponseDto ModifyForm (@PathVariable String member_code) {
		
		//특정 회원 정보 db에서 찾아오기
		MemberDto memberDto = memberService.findOne(member_code);
		ResponseDto result = new ResponseDto("회원 "+memberDto.getMember_name()+" 정보 구하기 성공", memberDto);
		
		log.info("result: "+result);
		log.info(memberDto.getMember_code()+" 수정 폼 실행");
		
		return result;
	}
	
	/******************************** 회원정보수정 실행 ********************************/
	@ApiOperation(value = "회원 정보 수정")
//	@PutMapping(value="/modify")
	@PutMapping(value="/modify/{member_code}")
	public ResponseDto ModifyOne (@PathVariable String member_code, @RequestBody MemberModifyDto memberDto) {
//	public ResponseDto ModifyOne (@RequestBody MemberModifyDto memberDto) {
				
		if (member_code.equals(memberDto.getMember_code())) {
		//memberDto.getMember_name()으로 update SQL문 실행하기
		int result = memberService.modifyOne(memberDto);
		ResponseDto responseResult = new ResponseDto(memberDto.getMember_code()+" 수정 완료", result);
			
		log.info("responseResult: "+responseResult);

		return responseResult;
		} else {
			ExceptionResponseDto excResDto = new ExceptionResponseDto<>("잘못된 접근입니다", HttpStatus.BAD_REQUEST);
			
			return excResDto;
		}
		
	}
	
	/******************************** 회원가입 폼 이동 ********************************/
	@ApiOperation(value = "회원가입폼 이동")
	@GetMapping("/registerForm")
	public String RegisterForm ( ) {
		return null;	// 뷰 페이지와 연결을 어떻게??
	}
	
	/******************************** 아이디 중복 검사 ********************************/
	@ApiOperation(value = "아이디 중복 검사")
	@GetMapping("/registerForm/idCheck/{member_code}")
	public ResponseDto IdCheck (@PathVariable String member_code) {
		
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
	public ResponseDto NordicRegister(@RequestBody MemberDto memberDto) {
//	public ResponseDto NordicRegister(@Validated @RequestBody MemberDto memberDto,
//									  BindingResult result) {
//
//		if(result.hasErrors()) {
//			ResponseDto response = new ResponseDto(result.toString(), HttpStatus.BAD_REQUEST);
//			return response;
//		}
		
		int mbrRegister = memberService.mbrRegister(memberDto);
		log.info("회원이름 : " + memberDto.getMember_name());
		
		return new ResponseDto ("회원가입성공", memberDto.getMember_name());
	}
	
	
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
