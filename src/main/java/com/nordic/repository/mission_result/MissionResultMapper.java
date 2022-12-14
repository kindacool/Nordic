package com.nordic.repository.mission_result;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.nordic.dto.mission_result.MissionHistoryDto;
import com.nordic.dto.mission_result.MissionHistoryImageDto;
import com.nordic.dto.mission_result.MissionMasterDto;
import com.nordic.dto.mission_result.MissionMasterImageDto;

@Mapper
public interface MissionResultMapper {
	
	/* 미션 수행 등록 */
	public void insertMission(MissionHistoryDto mission);
	
	/* 미션 수행 이미지 등록 */
	public void insertMissionImage(MissionHistoryImageDto image);
	
	/* 미션 포인트 조회 */
	public int getPoint(int mission_no);
	
	/* 미션 상세 정보 조회 */
	public MissionMasterDto getMaster(int mission_no);

	/* 미션 상세 이미지 조회 */
	public MissionMasterImageDto getMasterImg(int mission_no);

	/* 미션 수행 정보 조회 */
	public MissionHistoryDto getHistory(int mission_history_no);
	
	/* 미션 수행 이미지 조회 */
	public MissionHistoryImageDto getHistoryImg(int image_no);

	/* 작성자로 미션 수행 이미지 조회 */
	public MissionHistoryImageDto getHistoryImgByMember(Map confirm);
	
	/* 미션 수행 이미지 수정 */
	public int updateMissionImage(MissionHistoryImageDto image);
	
	/* 미션 수행 이미지 삭제 */
	public int deleteMissionImage(MissionHistoryImageDto image);

	public void deleteMission(MissionHistoryImageDto image);
}
