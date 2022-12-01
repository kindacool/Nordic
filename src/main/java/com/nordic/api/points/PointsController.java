package com.nordic.api.points;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.goods.GoodsDto;
import com.nordic.dto.goods.GoodsReqDto;
import com.nordic.dto.member.MemberDto;
import com.nordic.dto.points.PointsDto;
import com.nordic.service.goods.GoodsService;
import com.nordic.service.points.PointsService;
import com.nordic.service.requests.RequestsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/points")
public class PointsController {
	private final PointsService pointsService;
	private final RequestsService requestsService;

	// 굿즈신청 후 포인트 빼기
	@PostMapping("/{reqNo}/minus")
	public ResponseDto minusPoints(@PathVariable int reqNo) {
		
		PointsDto pointDto = new PointsDto();
		
		GoodsReqDto goodsReqDto = requestsService.findOneRequest(reqNo);
		pointDto.setPoint(goodsReqDto.getPoint());
		
		String buyer = goodsReqDto.getMember_code();
		pointDto.setMember_code(buyer);
		pointDto.setCreate_member(buyer);
		pointDto.setUpdate_member(buyer);
		
		pointDto.setMake_gubun("사용");
		pointDto.setRequest_no(reqNo);
		pointDto.setUse_yn('Y');
		pointsService.minusPoint(pointDto);
		
		// member 테이블에서도 반영
		// 처리할 포인트와 멤버코드를 가져감
		pointsService.updateMemberPoint(pointDto); // -- req, ++ total
		
		return new ResponseDto("포인트 테이블 등록 완료");
	}
	
	// 굿즈 신청 승인 후 포인트 req -> use 로 옮기기
	@PostMapping("/{reqNo}/y")
	public ResponseDto usedMemberPoints(@PathVariable int reqNo) {
		
		PointsDto pointDto = new PointsDto();
		GoodsReqDto goodsReqDto = requestsService.findOneRequest(reqNo);
		pointDto.setPoint(goodsReqDto.getPoint());
		pointDto.setMember_code(goodsReqDto.getMember_code());

		// member 테이블에 반영
		// 처리할 포인트와 멤버코드를 가져감
		pointsService.usedMemberPoints(pointDto);
		
		return new ResponseDto("req-> use 완료");
	}
	
	// 굿즈 신청 거절 후 포인트 req -> total 로 옯기기
	@PostMapping("/{reqNo}/n")
	public ResponseDto returnMemberPoints(@PathVariable int reqNo) {
		
		PointsDto pointDto = new PointsDto();
		GoodsReqDto goodsReqDto = requestsService.findOneRequest(reqNo);
		pointDto.setPoint(goodsReqDto.getPoint());
		pointDto.setMember_code(goodsReqDto.getMember_code());

		// member 테이블에 반영
		// 처리할 포인트와 멤버코드를 가져감
		pointsService.returnMemberPoints(pointDto);
		
		// 포인트 이력 테이블에도 반영
		// use_yn y -> n
		pointsService.deletePointHistory(reqNo);
		
		return new ResponseDto("req-> total 완료");
	}
}
