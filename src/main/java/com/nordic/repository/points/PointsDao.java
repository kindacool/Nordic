package com.nordic.repository.points;

import org.springframework.stereotype.Repository;

import com.nordic.dto.member.MemberDto;
import com.nordic.dto.points.PointsDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PointsDao implements PointsMapper{
	private final PointsMapper pointsMapper;

	@Override
	public void minusPoint(PointsDto pointsDto) {
		pointsMapper.minusPoint(pointsDto);
	}
	@Override
	public void updateMemberReqPoint(PointsDto pointsDto) {
		pointsMapper.updateMemberReqPoint(pointsDto);
	}
	@Override
	public MemberDto getMember(String member_code) {
		return pointsMapper.getMember(member_code);
	}
	@Override
	public void updateMemberTotalPoint(PointsDto pointsDto) {
		pointsMapper.updateMemberTotalPoint(pointsDto);
	}
	@Override
	public void usedMemberPoints(PointsDto pointDto) {
		pointsMapper.usedMemberPoints(pointDto);
	}
	@Override
	public void returnMemberPoints(PointsDto pointDto) {
		pointsMapper.returnMemberPoints(pointDto);
	}
	@Override
	public void deletePointHistory(PointsDto pointDto) {
		pointsMapper.deletePointHistory(pointDto);
	}

}
