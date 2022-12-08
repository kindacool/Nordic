package com.nordic.repository.mission_result;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.nordic.dto.mission_result.MissionHistoryDto;
import com.nordic.dto.mission_result.MissionHistoryImageDto;
import com.nordic.dto.mission_result.MissionMasterDto;
import com.nordic.dto.mission_result.MissionMasterImageDto;
import com.nordic.dto.mission_result.MissionStatusDto;
import com.nordic.dto.mission_result.PointHistoryDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MissionResultFindDao {
	
	private final MissionResultFindMapper mrfmapper;
	
	/* 미션 수행 등록 전체 리스트 */
	public List<MissionStatusDto> findAllMission(Map period) {
		return mrfmapper.findAllMission(period);
	}
	
	/* 미션 수행 미승인 리스트 */
	public List<MissionStatusDto> findNotConfirm(Map period) {
		return mrfmapper.findNotConfirm(period);
	}
	
	/* 미션 수행 상세 정보 */
	public MissionHistoryDto selectHistoryByNo(int mission_history_no) {
		return mrfmapper.selectHistoryByNo(mission_history_no);
	}
	
	/* 미션 수행 이미지 */
	public MissionHistoryImageDto selectHistoryImg(int mission_history_no) {
		return mrfmapper.selectHistoryImg(mission_history_no);
	}
	
	/* 미션 상세 정보 */
	public MissionMasterDto selectMasterByNo(int mission_no) {
		return mrfmapper.selectMasterByNo(mission_no);
	}
	
	/* 미션 이미지 */
	public MissionMasterImageDto selectMasterImg(int mission_no) {
		return mrfmapper.selectMasterImg(mission_no);
	}
	
	/* 유저 미션 수행 승인 */
	public MissionHistoryDto confirmMission(MissionHistoryDto updateMission) {
		mrfmapper.confirmMission(updateMission);
		return mrfmapper.selectHistoryByNo(updateMission.getMission_history_no()); 
	}
	
	/* 유저 미션 수행 거절 */
	public MissionHistoryDto refuseMission(MissionHistoryDto updateMission) {
		mrfmapper.refuseMission(updateMission);
		return mrfmapper.selectHistoryByNo(updateMission.getMission_history_no());
	}
	
	/* 포인트 지급 - point_history 테이블 */
	public PointHistoryDto insertPoint(PointHistoryDto point_history) {
		mrfmapper.insertPoint(point_history);
		return mrfmapper.selectPoint(point_history.getPoint_no());
	}
	
	/* 포인트 지급 - member 테이블 */
	public void updateMemberPoint(MissionHistoryDto mission) {
		mrfmapper.updateMemberPoint(mission);
	}
	
	/* 전체 기간 조회 */
	public Map getPeriod() {
		return mrfmapper.getPeriod();
	}
	
	/* 미승인된 미션수 */
	public int countNewResult() {
		return mrfmapper.countNewResult();
	}


}
