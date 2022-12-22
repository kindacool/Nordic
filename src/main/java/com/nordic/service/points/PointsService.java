package com.nordic.service.points;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.nordic.dto.member.MemberDto;
import com.nordic.dto.points.PointsDto;
import com.nordic.repository.points.PointsDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointsService {

	private final PointsDao pointsDao;

	public void minusPoint(PointsDto pointsDto) {
		pointsDao.minusPoint(pointsDto);
	}

	public void updateMemberPoint(PointsDto pointsDto) {
		pointsDao.updateMemberReqPoint(pointsDto); // req 에 + 시키기
		//pointsDao.updateMemberTotalPoint(pointsDto); //
	}
	
	// 멤버별 가용 포인트 구하기
	public int getAvailablePoints(String member_code) {
		MemberDto old = pointsDao.getMember(member_code);
		int availablePoints = old.getTotal_point() - old.getReq_point() - old.getUse_point();
		return availablePoints;
	}

	public void usedMemberPoints(PointsDto pointDto) {
		pointsDao.usedMemberPoints(pointDto);
	}

	public void returnMemberPoints(PointsDto pointDto) {
		pointsDao.returnMemberPoints(pointDto);
	}

	public void deletePointHistory(PointsDto pointDto) {
		pointsDao.deletePointHistory(pointDto);
	}

}
