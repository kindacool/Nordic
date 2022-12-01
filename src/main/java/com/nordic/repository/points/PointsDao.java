package com.nordic.repository.points;

import org.springframework.stereotype.Repository;

import com.nordic.dto.member.MemberDto;
import com.nordic.dto.points.PointsDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PointsDao implements PointsMapper{
	private final PointsMapper pointsMapper;

	public void minusPoint(PointsDto pointsDto) {
		pointsMapper.minusPoint(pointsDto);
	}

	public void updateMemberReqPoint(PointsDto pointsDto) {
		pointsMapper.updateMemberReqPoint(pointsDto);
	}

	public MemberDto getMember(String member_code) {
		return pointsMapper.getMember(member_code);
	}
	
	public void updateMemberTotalPoint(PointsDto pointsDto) {
		pointsMapper.updateMemberTotalPoint(pointsDto);
	}

	public void usedMemberPoints(PointsDto pointDto) {
		pointsMapper.usedMemberPoints(pointDto);
	}

	public void returnMemberPoints(PointsDto pointDto) {
		pointsMapper.returnMemberPoints(pointDto);
	}

	public void deletePointHistory(int reqNo) {
		pointsMapper.deletePointHistory(reqNo);
	}

}
