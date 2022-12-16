package com.nordic.repository.points;

import org.apache.ibatis.annotations.Mapper;

import com.nordic.dto.member.MemberDto;
import com.nordic.dto.points.PointsDto;

@Mapper
public interface PointsMapper {

	void minusPoint(PointsDto pointsDto);

	void updateMemberReqPoint(PointsDto pointsDto);

	MemberDto getMember(String member_code);

	void updateMemberTotalPoint(PointsDto pointsDto);

	void usedMemberPoints(PointsDto pointDto);

	void returnMemberPoints(PointsDto pointDto);

	void deletePointHistory(PointsDto pointDto);

}
