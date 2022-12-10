package com.nordic.api.requests;

import java.io.IOException;
import java.util.List;

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
import com.nordic.service.goods.GoodsService;
import com.nordic.service.points.PointsService;
import com.nordic.service.requests.RequestsService;

import io.swagger.annotations.ApiOperation;
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
	
	// 요청 중복검사(같은사람이 같은 상품 구매한적있는지 확인)
	@ApiOperation("요청 중복검사")
	@GetMapping("/check/{no}")
	public ResponseDto duplicateRequestsCheck(@PathVariable int no) throws Exception{
		log.info("중복 요청 체크 Controller 도착");
		
		String buyer = "10007"; // 토큰 구현전까지 일시로
		
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
	
	@ApiOperation("굿즈 구매 요청")
	@PostMapping("/{no}")
	public ResponseDto createRequest(@PathVariable int no) throws Exception{
		log.info("굿즈 요청 Controller 도착");
		
		GoodsReqDto goodsReqDto = new GoodsReqDto();
		goodsReqDto.setGoods_no(no);
		GoodsDto old = goodsService.readOneGoods(no);
		int oldPoint = old.getPoint();
		
		goodsReqDto.setPoint(oldPoint);
		
		String buyer = "10007"; // 토큰 구현전까지 일시로
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
	
	@GetMapping("/{reqNo}")
	public ResponseDto findOneRequest(@PathVariable int reqNo) throws Exception{
		log.info("요청 1개 상세정보 Controller 도착");
		
		GoodsReqDto goodsReqDto = requestsService.findOneRequest(reqNo);
		System.out.println(goodsReqDto);
		return new ResponseDto("굿즈 신청 상세정보",goodsReqDto);
	}
	
	@GetMapping
	public ResponseDto findAllRequest(@RequestParam(value = "pageNum",
	required = false,
	defaultValue = "1") int pageNum) throws Exception{
		log.info("모든 요청 Controller 도착");
		
		List<GoodsReqDto> requestList = requestsService.findAllRequest(pageNum);
		return new ResponseDto("모든 요청", PageInfo.of(requestList));
	}
	
	@GetMapping("/unconfirmed")
	public ResponseDto findAllUnconfirmedRequest(@RequestParam(value = "pageNum",
			required = false,
			defaultValue = "1") int pageNum) throws Exception{
		log.info("확인 안된 모든 요청 Controller 도착");
		
		List<UnconfirmedRequestsDto> requestList = requestsService.findAllUnconfirmedRequest(pageNum);
		return new ResponseDto("확인 안된 모든 요청", PageInfo.of(requestList));
	}
	
	@GetMapping(value= {"/confirmed","/confirmed/{yn}"})
	public ResponseDto findAllConfirmedRequest(@RequestParam(value = "pageNum",
			required = false,
			defaultValue = "1") int pageNum,
			@PathVariable(value="yn",required = false) String yn) throws Exception{
		log.info("확인 된 모든 요청 Controller 도착");
		System.out.println(yn);
		List<ConfirmedRequestsDto> requestList;
		
		if(yn == null){
			requestList = requestsService.findAllConfirmedRequest(pageNum, yn);
		} else if(yn.equals("n")) {
			requestList = requestsService.findAllRejectedRequest(pageNum);
		} else if(yn.equals("y")) {
			requestList = requestsService.findAllAcceptedRequest(pageNum);
		} else {
			throw new Exception("bad requests");
		}
		return new ResponseDto("확인된 모든 요청", PageInfo.of(requestList));
	}
	
//	@GetMapping("/confirmed/y")
//	public ResponseDto findAllAcceptedRequest(@RequestParam(value = "pageNum",
//			required = false,
//			defaultValue = "1") int pageNum) throws Exception{
//		log.info("지급된 모든 요청 Controller 도착");
//		
//		List<ConfirmedRequestsDto> requestList = requestsService.findAllAcceptedRequest(pageNum);
//		return new ResponseDto("지급된 모든 요청", PageInfo.of(requestList));
//	}
//	
//	@GetMapping("/confirmed/n")
//	public ResponseDto findAllRejectedRequest(@RequestParam(value = "pageNum",
//			required = false,
//			defaultValue = "1") int pageNum) throws Exception{
//		log.info("지급된 모든 요청 Controller 도착");
//		
//		List<ConfirmedRequestsDto> requestList = requestsService.findAllRejectedRequest(pageNum);
//		return new ResponseDto("거절된 모든 요청", PageInfo.of(requestList));
//	}
	
	// 요청 수락
	@ApiOperation("굿즈 구매 요청 수락")
	@PostMapping("/{reqNo}/y")
	public ResponseDto acceptRequest(@PathVariable int reqNo) throws IOException{
		log.info("요청 수락 Controller 도착");
		String master = "Yoo"; // 토큰 구현전까지 일시로
		
		GoodsReqDto goodsReqDto = new GoodsReqDto();
		goodsReqDto.setConfirm_yn('Y');
		goodsReqDto.setConfirm_member(master);
		goodsReqDto.setUpdate_member(master);
		goodsReqDto.setRequest_no(reqNo);
		
		requestsService.acceptRequest(goodsReqDto);
		
		// 포인트 req -> use
		PointsDto pointDto = new PointsDto();
		GoodsReqDto old = requestsService.findOneRequest(reqNo);
		pointDto.setPoint(old.getPoint());
		pointDto.setMember_code(old.getMember_code());
		pointDto.setUpdate_member(old.getMember_code());

		// member 테이블에 반영
		// 처리할 포인트와 멤버코드를 가져감
		pointsService.usedMemberPoints(pointDto);
		
		return new ResponseDto("요청 수락");
	}
	
	// 요청 거절
	@ApiOperation("굿즈 구매 요청 거절")
	@PostMapping("/{reqNo}/n")
	public ResponseDto rejectRequest(@PathVariable int reqNo, @RequestParam(value="remark",required = false) String remark) throws IOException{
		log.info("요청 거절 Controller 도착");
		String master = "Yoo"; // 토큰 구현전까지 일시로
		
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
		GoodsReqDto old = requestsService.findOneRequest(reqNo);
		pointDto.setPoint(old.getPoint());
		pointDto.setMember_code(old.getMember_code());
		pointDto.setUpdate_member(old.getMember_code());

		// member 테이블에 반영
		// 처리할 포인트와 멤버코드를 가져감
		pointsService.returnMemberPoints(pointDto);
		
		// 포인트 이력 테이블에도 반영
		// use_yn y -> n
		pointsService.deletePointHistory(reqNo);
		
		return new ResponseDto("요청 거절");
	}
	
	// 굿즈별 요청 목록
	@ApiOperation("굿즈별 요청 목록")
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
	@ApiOperation("내 요청 목록")
	@GetMapping("/my")
	public ResponseDto myRequests(@RequestParam(value = "pageNum",
			required = false,
			defaultValue = "1") int pageNum) throws Exception{
		
		log.info("내 요청 목록 Controller 도착");
		
		String member_code = "10007"; // 토큰 구현전까지 일시로
		
		List<GoodsReqDto> requestList = requestsService.myRequests(member_code, pageNum);
		return new ResponseDto("내 요청 목록", requestList);
	}	
	
	// 요청 취소
	@ApiOperation("요청 취소")
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
				requestsService.cancelRequest(reqNo);
				
				// 멤버테이블 req -> total 로 돌리기
				PointsDto pointDto = new PointsDto();
				pointDto.setPoint(old.getPoint());
				pointDto.setMember_code(old.getMember_code());
				pointDto.setUpdate_member(old.getMember_code());
				pointsService.returnMemberPoints(pointDto);
				
				
				// 포인트 테이블 포인트 히스토리 'N' 로 바꾸기
				pointsService.deletePointHistory(reqNo);
				
				return new ResponseDto("요청 취소 완료");
			}
		}
	}
}