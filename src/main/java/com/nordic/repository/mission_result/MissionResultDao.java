package com.nordic.repository.mission_result;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.nordic.dto.mission_result.MissionHistoryDto;
import com.nordic.dto.mission_result.MissionHistoryImageDto;
import com.nordic.dto.mission_result.MissionMasterDto;
import com.nordic.dto.mission_result.MissionMasterImageDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MissionResultDao {
	private final MissionResultMapper mrmapper;
	
	/* 미션 수행 등록 */
	public MissionHistoryDto insertUserMission(MissionHistoryDto mission) {
		mrmapper.insertMission(mission);
		return mrmapper.getHistory(mission.getMission_history_no());
	}
	
	/* 미션 수행 이미지 등록 */
	public MissionHistoryImageDto insertUserMissionImage(MissionHistoryImageDto image) {
		mrmapper.insertMissionImage(image);
		return mrmapper.getHistoryImg(image.getImage_no());
	}
	
	/* 미션 포인트 조회 */
	public int getPoint(int mission_no) {
		return mrmapper.getPoint(mission_no);
	}
	
	/* 미션 상세 정보 조회 */
	public MissionMasterDto getMaster(int mission_no) {
		return mrmapper.getMaster(mission_no);
	}
	
	/* 미션 상세 정보 이미지 조회 */
	public MissionMasterImageDto getMasterImg(int mission_no) {
		return mrmapper.getMasterImg(mission_no);
	}
	
	/* 작성자로 미션 수행 이미지 조회 */
	public MissionHistoryImageDto confirmHistoryImg(Map confirm) {
		return mrmapper.getHistoryImgByMember(confirm);
	}  
	
	/* 미션 사진 수정 */
	public MissionHistoryImageDto updateUserMissionImage(MissionHistoryImageDto image) {
		mrmapper.updateMissionImage(image);
		return mrmapper.getHistoryImg(image.getImage_no());
	}
	
	/* 미션 사진 삭제 */
	public MissionHistoryImageDto deleteUserMissionImage(MissionHistoryImageDto image) {
		mrmapper.deleteMissionImage(image);
		return mrmapper.getHistoryImg(image.getImage_no());
	}

	public void deleteUserMission(MissionHistoryImageDto image) {
		mrmapper.deleteMission(image);
	}
	
}
