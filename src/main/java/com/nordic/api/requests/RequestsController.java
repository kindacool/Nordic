package com.nordic.api.requests;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.goods.GoodsDto;
import com.nordic.dto.goods.GoodsReqDto;
import com.nordic.service.goods.GoodsService;
import com.nordic.service.points.PointsService;
import com.nordic.service.requests.RequestsService;

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
	
	@PostMapping("/{no}")
	public ResponseDto createRequest(@PathVariable int no) throws IOException{
		log.info("굿즈 요청 Controller 도착");
		
		GoodsReqDto goodsReqDto = new GoodsReqDto();
		goodsReqDto.setGoods_no(no);
		GoodsDto old = goodsService.readOneGoods(no);
		int oldPoint = old.getPoint();
		
		goodsReqDto.setPoint(oldPoint);
		
		String buyer = "10007"; // 토큰 구현전까지 일시로
		goodsReqDto.setMember_code(buyer);
		goodsReqDto.setCreate_member(buyer);
		goodsReqDto.setUpdate_member(buyer);
		goodsReqDto.setUse_yn('Y');
		if(pointsService.getAvailablePoints(buyer) < oldPoint) {
			log.info("잔액 부족으로 굿즈 신청한다고 메세지 뿌리기");
			return new ResponseDto("잔액 부족으로 실패");
		} else {
			requestsService.createRequest(goodsReqDto);
		}
		return new ResponseDto("굿즈가 신청되었습니다.",goodsReqDto);
	}
	
	@GetMapping("/{reqNo}")
	public ResponseDto findOneRequest(@PathVariable int reqNo) throws IOException{
		log.info("요청 1개 상세정보 Controller 도착");
		
		GoodsReqDto goodsReqDto = requestsService.findOneRequest(reqNo);
		System.out.println(goodsReqDto);
		return new ResponseDto("굿즈 신청 상세정보",goodsReqDto);
	}
	
	@GetMapping
	public ResponseDto findAllRequest() throws Exception{
		log.info("모든 요청 Controller 도착");
		
		List<GoodsReqDto> requestList = requestsService.findAllRequest();
		return new ResponseDto("모든 요청", requestList);
	}
	
	@GetMapping("/unconfirmed")
	public ResponseDto findAllUnconfirmedRequest() throws Exception{
		log.info("확인 안된 모든 요청 Controller 도착");
		
		List<GoodsReqDto> requestList = requestsService.findAllUnconfirmedRequest();
		return new ResponseDto("확인 안된 모든 요청", requestList);
	}
	
	@GetMapping("/confirmed")
	public ResponseDto findAllConfirmedRequest() throws Exception{
		log.info("확인 된 모든 요청 Controller 도착");
		
		List<GoodsReqDto> requestList = requestsService.findAllConfirmedRequest();
		return new ResponseDto("확인된 모든 요청", requestList);
	}
	
	@GetMapping("/confirmed/y")
	public ResponseDto findAllAcceptedRequest() throws Exception{
		log.info("지급된 모든 요청 Controller 도착");
		
		List<GoodsReqDto> requestList = requestsService.findAllAcceptedRequest();
		return new ResponseDto("지급된 모든 요청", requestList);
	}
	
	// 요청 수락
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
		return new ResponseDto("요청 수락");
	}
	
	// 요청 거절
	@PostMapping("/{reqNo}/n")
	public ResponseDto rejectRequest(@PathVariable int reqNo, @RequestParam String remark) throws IOException{
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
		
		return new ResponseDto("요청 거절");
	}
}
