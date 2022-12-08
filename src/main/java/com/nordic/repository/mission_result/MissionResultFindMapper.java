package com.nordic.repository.mission_result;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.nordic.dto.mission_result.MissionHistoryDto;
import com.nordic.dto.mission_result.MissionHistoryImageDto;
import com.nordic.dto.mission_result.MissionMasterDto;
import com.nordic.dto.mission_result.MissionMasterImageDto;
import com.nordic.dto.mission_result.MissionStatusDto;
import com.nordic.dto.mission_result.PointHistoryDto;

@Mapper
public interface MissionResultFindMapper {
	
	/* 미션 수행 등록 전체 리스트 */
	public List<MissionStatusDto> findAllMission(Map period);
	
	/* 미션 수행 미승인 리스트 */
	public List<MissionStatusDto> findNotConfirm(Map period);
	
	/* 미션 수행 상세 정보 */
	public MissionHistoryDto selectHistoryByNo(int mission_history_no);
	
	/* 미션 수행 이미지 */
	public MissionHistoryImageDto selectHistoryImg(int mission_history_no);
	
	/* 미션 상세 정보 */
	public MissionMasterDto selectMasterByNo(int mission_no);

	/* 미션 이미지 */
	public MissionMasterImageDto selectMasterImg(int mission_no);
	
	/* 유저 미션 수행 승인 */
	public void confirmMission(MissionHistoryDto updateMission);
	
	/* 유저 미션 수행 거절 */
	public void refuseMission(MissionHistoryDto updateMission);
	
	/* 포인트 정보 */
	public PointHistoryDto selectPoint(int point_no);

	/* 포인트 지급 */
	public void insertPoint(PointHistoryDto point_history);
	
	/* 포인트 정보 업데이트 */
	public void updateMemberPoint(MissionHistoryDto mission);
	
	/* 리스트 기간 */
	public Map getPeriod();
	
	/* 미승인된 미션수 */
	public int countNewResult();

}
