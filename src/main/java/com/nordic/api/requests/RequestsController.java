package com.nordic.api.requests;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nordic.service.login.CustomUserDetailsService;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.nordic.config.CustomException;
import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.goods.GoodsDto;
import com.nordic.dto.points.PointsDto;
import com.nordic.dto.requests.ConfirmedRequestsDto;
import com.nordic.dto.requests.GoodsReqDto;
import com.nordic.dto.requests.UnconfirmedRequestsDto;
import com.nordic.exception.CancleRequestException;
import com.nordic.exception.DuplicateRequestsException;
import com.nordic.exception.NoBalanceException;
import com.nordic.repository.requests.RequestsDao;
import com.nordic.service.goods.GoodsService;
import com.nordic.service.points.PointsService;
import com.nordic.service.requests.RequestsService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/requests")
public class RequestsController {
	private final RequestsService requestsService;
	private final GoodsService goodsService;
	private final PointsService pointsService;
	private final CustomUserDetailsService customeservice;
	
	// 요청 중복검사(같은사람이 같은 상품 구매한적있는지 확인)
	@ApiOperation(value="포인트 상품 구매 신청 중복검사", notes="이용자 / 같은 상품을 구매 신청한 적 있는지, 그 신청이 미확인 신청인지 검사")
	@ApiImplicitParams({
		@ApiImplicitParam(name="no", value="포인트 상품 번호")
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "요청 중복 / 요청 중복 아님 모두 OK 반환")
	})
	@GetMapping("/check/{no}")
	public ResponseDto duplicateRequestsCheck(@PathVariable int no) throws Exception{
		log.info("중복 요청 체크 Controller 도착");
		
		String buyer = //"10007"; // 토큰 구현전까지 일시로
		(String) customeservice.getUserInfo().get("member_code");
		
		GoodsReqDto goodsReqDto = new GoodsReqDto();
		goodsReqDto.setMember_code(buyer);
		goodsReqDto.setGoods_no(no);
		
		GoodsReqDto checkGoodsReqDto = requestsService.duplicateRequestsCheck(goodsReqDto);
		
		if(checkGoodsReqDto != null) {
			// 예외처리 : 같은 사람이 같은 상품을 신청한적 있는지 확인(미확인 상태인 신청만 확인)
			throw new DuplicateRequestsException(DuplicateRequestsException.ERR_0003);
		} else {
			return new ResponseDto("중복 없음");
		}
	}
	
	@ApiOperation(value="포인트 상품 구매 신청", notes="이용자 / 포인트 상품 구매 신청")
	@ApiImplicitParams({
		@ApiImplicitParam(name="no", value="포인트 상품 번호")
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "구매 신청 성공 / 잔액 부족 실패 모두 OK 반환")
	})
	@PostMapping("goods/{no}")
	public ResponseDto createRequest(@PathVariable int no) throws Exception{
		log.info("굿즈 요청 Controller 도착");
		
		GoodsReqDto goodsReqDto = new GoodsReqDto();
		goodsReqDto.setGoods_no(no);
		GoodsDto old = goodsService.readOneGoods(no);
		int oldPoint = old.getPoint();
		
		goodsReqDto.setPoint(oldPoint);
		
		String buyer = //"10007"; // 토큰 구현전까지 일시로
		(String) customeservice.getUserInfo().get("member_code");
		goodsReqDto.setMember_code(buyer);
		goodsReqDto.setCreate_member(buyer);
		goodsReqDto.setUse_yn('Y');
		
		if(pointsService.getAvailablePoints(buyer) < oldPoint) {
			// 예외처리 : 잔액이 부족합니다
			throw new NoBalanceException(NoBalanceException.ERR_0001);
		} else {
			requestsService.createRequest(goodsReqDto);
		}
		return new ResponseDto("굿즈가 신청되었습니다.",goodsReqDto);
	}
	
	@ApiOperation(value="포인트 상품 신청 상세정보", notes="관리자 / 포인트 상품 신청 1개 상세정보")
	@ApiImplicitParams({
		@ApiImplicitParam(name="reqNo", value="포인트 상품 구매 신청 번호")
	})
	@GetMapping("/{reqNo}")
	public ResponseDto findOneRequest(@PathVariable int reqNo) throws Exception{
		log.info("요청 1개 상세정보 Controller 도착");
		
		GoodsReqDto goodsReqDto = requestsService.findOneRequest(reqNo);
		System.out.println(goodsReqDto);
		return new ResponseDto("굿즈 신청 상세정보",goodsReqDto);
	}
	
	@ApiOperation(value="모든 포인트 상품 구매 신청 목록", notes="관리자 / 모든 포인트 상품 구매 신청 목록 보기")
	@ApiImplicitParams({
		@ApiImplicitParam(name="pageNum", value="페이지 번호", required = false, defaultValue = "1")
	})
	@GetMapping
	public ResponseDto findAllRequest(@RequestParam(value = "pageNum",
	required = false,
	defaultValue = "1") int pageNum) throws Exception{
		log.info("모든 요청 Controller 도착");
		
		List<GoodsReqDto> requestList = requestsService.findAllRequest(pageNum);
		return new ResponseDto("모든 요청", PageInfo.of(requestList));
	}
	
	@ApiOperation(value="모든 미확인 포인트 구매 신청 목록", notes="관리자 / 포인트 구매 신청 목록 중 미확인 신청만 보기")
	@ApiImplicitParams({
		@ApiImplicitParam(name="all", value="취소된 신청 제외 모든 미확인 구매 신청 () / 취소된 신청 포함한 모든 미확인 구매 신청(all)", required = false),
		@ApiImplicitParam(name="pageNum", value="페이지 번호", required = false, defaultValue = "1"),
		@ApiImplicitParam(name="search", value="검색기준", required = false),
		@ApiImplicitParam(name="keyword", value="검색어", required = false)
	})
	@GetMapping(value = {"/unconfirmed", "/unconfirmed/{all}"})
	public ResponseDto findAllUnconfirmedRequest(@RequestParam(value = "pageNum",
			required = false,
			defaultValue = "1") int pageNum,
			@RequestParam(value="search", required = false) String search,
			@RequestParam(value="keyword", required = false) String keyword,
			@PathVariable(value="all", required = false) String all) throws Exception{
		log.info("확인 안된 모든 요청 Controller 도착");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("keyword", keyword);
		map.put("all", all);
		List<UnconfirmedRequestsDto> requestList = requestsService.findAllUnconfirmedRequest(pageNum,map);
		return new ResponseDto("확인 안된 모든 요청", PageInfo.of(requestList));
	}
	
	@ApiOperation(value="모든 확인한 포인트 구매 신청 목록", notes="관리자 / 포인트 구매 신청 목록 중 확인 신청만 보기")
	@ApiImplicitParams({
		@ApiImplicitParam(name="yn", value="모든 확인 구매 신청 () / 모든 수락한 구매 신청 (y) / 모든 거절한 구매 신청 (n)", required = false),
		@ApiImplicitParam(name="pageNum", value="페이지 번호", required = false, defaultValue = "1"),
		@ApiImplicitParam(name="search", value="검색기준", required = false),
		@ApiImplicitParam(name="keyword", value="검색어", required = false),
		@ApiImplicitParam(name="start", value="시작 날짜", required = false),
		@ApiImplicitParam(name="end", value="끝 날짜", required = false),
	})
	@GetMapping(value= {"/confirmed","/confirmed/{yn}"})
	public ResponseDto findAllConfirmedRequest(@RequestParam(value = "pageNum",
			required = false,
			defaultValue = "1") int pageNum,
			@RequestParam(value="start", required = false) String start,
			@RequestParam(value="end", required = false) String end,
			@RequestParam(value="search", required = false) String search,
			@RequestParam(value="keyword", required = false) String keyword,
			@PathVariable(value="yn",required = false) String yn) throws Exception{
		log.info("확인 된 모든 요청 Controller 도착");
		//System.out.println(yn);
		log.info(search + " : " + keyword);
		

		if(start == "" || end == "") {
			start = "0001-01-01";
			end = "9999-12-31";
		} 
		// String으로 들어오는 날짜 데이터 변환
		LocalDateTime startDateTime = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(0, 0, 0);
		LocalDateTime endDateTime = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(23, 59, 59);
		
		
		System.out.println(startDateTime);
		System.out.println(endDateTime);
		List<ConfirmedRequestsDto> requestList;
		
		Map<String, Object> map = new HashMap<>();
		map.put("search", search);
		map.put("keyword", keyword);
		map.put("yn", yn);
		map.put("start", startDateTime);
		map.put("end", endDateTime);
		requestList = requestsService.findAllConfirmedRequest(pageNum, map);

		return new ResponseDto("확인된 모든 요청", PageInfo.of(requestList));
	}
	
	// 요청 수락
	@ApiOperation(value="포인트 상품 구매 신청 수락", notes = "관리자 / 포인트 상품 구매 신청 수락")
	@ApiImplicitParams({
		@ApiImplicitParam(name="reqNo", value="포인트 상품 구매 신청 번호")
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "수락 성공 / 이미 취소된 요청입니다. 모두 OK")
	})
	@PostMapping("/{reqNo}/y")
	public ResponseDto acceptRequest(@PathVariable int reqNo) throws IOException{
		log.info("요청 수락 Controller 도착");
		String master = //"Yoo"; // 토큰 구현전까지 일시로
		(String) customeservice.getUserInfo().get("member_code");
		
		GoodsReqDto old = requestsService.findOneRequest(reqNo);
		if(old.getUse_yn() == 'N') {
			// 예외 처리 : 이미 취소된 요청입니다
			throw new CancleRequestException(CancleRequestException.ERR_0006);
		} 
		
		GoodsReqDto goodsReqDto = new GoodsReqDto();
		goodsReqDto.setConfirm_yn('Y');
		goodsReqDto.setConfirm_member(master);
		goodsReqDto.setUpdate_member(master);
		goodsReqDto.setRequest_no(reqNo);
		
		requestsService.acceptRequest(goodsReqDto);
		
		// 포인트 req -> use
		PointsDto pointDto = new PointsDto();
		pointDto.setPoint(old.getPoint());
		pointDto.setMember_code(old.getMember_code());

		// member 테이블에 반영
		// 처리할 포인트와 멤버코드를 가져감
		pointsService.usedMemberPoints(pointDto);
		
		return new ResponseDto("요청 수락");
	}
	
	// 요청 거절
	@ApiOperation(value="포인트 상품 구매 신청 거절", notes = "관리자 / 포인트 상품 구매 신청 거절")
	@ApiImplicitParams({
		@ApiImplicitParam(name="reqNo", value="포인트 상품 구매 신청 번호")
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "거절 성공 / 이미 취소된 요청입니다. 모두 OK")
	})
	@PostMapping("/{reqNo}/n")
	public ResponseDto rejectRequest(@PathVariable int reqNo, @RequestParam(value="remark",required = false) String remark) throws Exception{
		log.info("요청 거절 Controller 도착");
		
		GoodsReqDto old = requestsService.findOneRequest(reqNo);
		if(old.getUse_yn() == 'N') {
			// 예외 처리 : 이미 취소된 요청입니다
			throw new CancleRequestException(CancleRequestException.ERR_0006);
		} 
		
		String master = //"Yoo"; // 토큰 구현전까지 일시로
		(String) customeservice.getUserInfo().get("member_code");
		
		GoodsReqDto goodsReqDto = new GoodsReqDto();
		goodsReqDto.setConfirm_yn('Y');
		goodsReqDto.setConfirm_member(master);
		goodsReqDto.setUpdate_member(master);
		goodsReqDto.setRequest_no(reqNo);
		goodsReqDto.setRefuse_yn('Y');
		goodsReqDto.setRemark(remark);
		
		requestsService.rejectRequest(goodsReqDto);
		
		// 포인트 req -> total
		PointsDto pointDto = new PointsDto();
		pointDto.setPoint(old.getPoint());
		pointDto.setMember_code(old.getMember_code());
		pointDto.setUpdate_member(master);

		// member 테이블에 반영
		// 처리할 포인트와 멤버코드를 가져감
		pointsService.returnMemberPoints(pointDto);
		
		// 포인트 이력 테이블에도 반영
		// use_yn y -> n
		pointDto.setRequest_no(reqNo);
		pointDto.setRemark("요청 거절");
		pointsService.deletePointHistory(pointDto);
		
		return new ResponseDto("요청 거절");
	}
	
	// 굿즈별 요청 목록
	@ApiOperation(value="포인트 상품 별 구매 신청 목록", notes = "관리자 / 포인트 상품별 구매 신청 보기")
	@ApiImplicitParams({
		@ApiImplicitParam(name="no", value="포인트 상품 번호"),
		@ApiImplicitParam(name="pageNum", value="페이지 번호", required = false, defaultValue = "1")
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "수락 성공 / 이미 취소된 요청입니다. 모두 OK")
	})
	@GetMapping("/goods/{no}")
	public ResponseDto findRequestsByGoods(@PathVariable int no,
	@RequestParam(value = "pageNum",
	required = false,
	defaultValue = "1") int pageNum) throws Exception{
		log.info("굿즈별 요청 목록 Controller 도착");
		
		List<GoodsReqDto> requestList = requestsService.findRequestsByGoods(no, pageNum);
		return new ResponseDto("굿즈별 요청 목록", PageInfo.of(requestList));
	}
	
	// 내 요청 목록
	// 굿즈별 요청 목록
	@ApiOperation(value=" 내 요청 목록", notes = "이용자 / 내 요청 모두 보기")
	@ApiImplicitParams({
		@ApiImplicitParam(name="pageNum", value="페이지 번호", required = false, defaultValue = "1")
	})
	@GetMapping("/my")
	public ResponseDto myRequests(@RequestParam(value = "pageNum",
			required = false,
			defaultValue = "1") int pageNum) throws Exception{
		
		log.info("내 요청 목록 Controller 도착");
		
		String member_code = //"10007"; // 토큰 구현전까지 일시로
		(String) customeservice.getUserInfo().get("member_code");
		
		List<ConfirmedRequestsDto> requestList = requestsService.myRequests(member_code, pageNum);
		return new ResponseDto("내 요청 목록", PageInfo.of(requestList));
	}	
	
	// 요청 취소
	@ApiOperation(value="포인트 상품 구매 신청 취소", notes = "이용자 / 포인트 상품 구매 신청 취소")
	@ApiImplicitParams({
		@ApiImplicitParam(name="reqNo", value="포인트 상품 구매 신청 번호")
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "취소 성공 / 이미 취소된 요청입니다. / 이미 처리된 요청은 취소하실 수 없습니다. 모두 OK")
	})
	@DeleteMapping("/{reqNo}")
	public ResponseDto cancelRequest(@PathVariable int reqNo) {
		log.info("요청 취소 Controller 도착");
		
		GoodsReqDto old = requestsService.findOneRequest(reqNo);
		if(old.getUse_yn() == 'N') {
			// 예외 처리 : 이미 취소된 요청입니다
			throw new CancleRequestException(CancleRequestException.ERR_0004);
		} else {
			if(old.getConfirm_yn() == 'Y') {
				// 예외 처리 throw 이미 처리된 요청은 취소하실 수 없습니다
				throw new CancleRequestException(CancleRequestException.ERR_0005);
			} else {
				// 요청 테이블 요청 N 으로 바꾸기
				GoodsReqDto goodsReqDto = new GoodsReqDto();
				goodsReqDto.setRequest_no(reqNo);
				goodsReqDto.setUpdate_member(old.getMember_code());
				goodsReqDto.setRemark("요청 취소");
				requestsService.cancelRequest(goodsReqDto);
				
				// 멤버테이블 req -> total 로 돌리기
				PointsDto pointDto = new PointsDto();
				pointDto.setPoint(old.getPoint());
				pointDto.setMember_code(old.getMember_code());
				pointDto.setUpdate_member(old.getMember_code());
				pointsService.returnMemberPoints(pointDto);
				
				pointDto.setRequest_no(reqNo);
				pointDto.setRemark("요청 취소");
				// 포인트 테이블 포인트 히스토리 'N' 로 바꾸기
				pointsService.deletePointHistory(pointDto);
				
				return new ResponseDto("요청 취소 완료");
			}
		}
	}
}
