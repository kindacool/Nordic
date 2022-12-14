package com.nordic.service.mission_result;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nordic.dto.mission_result.MissionHistoryDetailDto;
import com.nordic.dto.mission_result.MissionHistoryDto;
import com.nordic.dto.mission_result.MissionHistoryImageDto;
import com.nordic.dto.mission_result.MissionMasterDto;
import com.nordic.dto.mission_result.MissionMasterImageDto;
import com.nordic.exception.ExpireException;
import com.nordic.repository.mission_result.MissionResultDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MissionResultService {
	
	private final MissionResultDao mrdao;
	
	/* 미션 등록 폼 */
	public MissionHistoryDetailDto getDetail(int mission_no, String member_code) {
		MissionMasterDto master = mrdao.getMaster(mission_no);
		MissionMasterImageDto master_img = mrdao.getMasterImg(mission_no);
		
		/* 이미 등록한 미션이 있는지 확인 */
		Map confirm = new HashMap();
		confirm.put("mission_no", mission_no);
		confirm.put("member_code", member_code);
		
		MissionHistoryImageDto history_img = new MissionHistoryImageDto();
		
		try {
			history_img = mrdao.confirmHistoryImg(confirm);
		} catch (NullPointerException e) {}
		
		return new MissionHistoryDetailDto(master, master_img, history_img);
	}
	
	/* 미션 수행 등록 (mission_history) */
	public MissionHistoryDto insertMissionHistory(String member_code, int mission_no) {
		int point = mrdao.getPoint(mission_no);
		
		MissionHistoryDto mission = new MissionHistoryDto();
		mission.setMember_code(member_code);
		mission.setMission_no(mission_no);
		mission.setPoint(point);
		
		return mrdao.insertUserMission(mission);
	}
	
	/* 사진 업로드 */
	public String uploadMissionResultImage(MultipartFile file) throws IOException {
		String path = System.getProperty("user.dir") + "/src/main/resources/static/img/mission";
		String filename = file.getOriginalFilename();
		UUID uuid = UUID.randomUUID();
		String newfilename = uuid.toString()+"_"+filename;
		file.transferTo(new File(path+"/"+newfilename));

		return newfilename;
	}
	
	/* 미션 사진 등록 (mission_history_image) */
	public MissionHistoryImageDto insertMissionHistoryImg(MissionHistoryDto mission,String newfilename) {
		MissionHistoryImageDto image = new MissionHistoryImageDto();
		image.setMission_no(mission.getMission_no());
		image.setMission_history_no(mission.getMission_history_no());
		image.setCreate_member(mission.getMember_code());
		image.setConfirm_file(newfilename);
		
		return mrdao.insertUserMissionImage(image);
	}
	
	/* 미션 수행 등록 */
	public Map uploadResult(String member_code, int mission_no, MultipartFile file) throws IOException {
		
		/* 미션 수행 등록 (mission_history) */
		MissionHistoryDto mission = insertMissionHistory(member_code, mission_no);
		
		/* 사진 업로드 */
		String newfilename = uploadMissionResultImage(file);
		
		/* 미션 사진 등록 (mission_history_image) */
		MissionHistoryImageDto image = insertMissionHistoryImg(mission, newfilename);
		
		Map mission_history = new HashMap();
		mission_history.put("mission", mission);
		mission_history.put("image", image);
		
		return mission_history;
 	}
	
	/* 날짜 비교 */
	public int compareDate(int mission_no) {
		Date deadline = mrdao.getMaster(mission_no).getEnd_date();
		Date today = new Date();
		
		return deadline.compareTo(today);
	}
	
	/* 미션 수행 사진 수정 */
	public MissionHistoryImageDto updateResult(String member_code, int mission_no, MultipartFile file) throws Exception {	
		int result = compareDate(mission_no);
		if (result < 0) {
			throw new ExpireException(ExpireException.MR_0003);
		} 
		
		/* 사진 업로드 */
		String newfilename = uploadMissionResultImage(file);
		
		MissionHistoryImageDto image = new MissionHistoryImageDto();
		image.setMission_no(mission_no);
		image.setUpdate_member(member_code);
		image.setConfirm_file(newfilename);
		
		return mrdao.updateUserMissionImage(image);
	}
	
	/* 미션 수행 사진 삭제 */
	public MissionHistoryImageDto deleteResult(String member_code, int mission_no) throws Exception {
		int result = compareDate(mission_no);
		if (result < 0) {
			throw new ExpireException(ExpireException.MR_0003);
		}
		
		MissionHistoryImageDto image = new MissionHistoryImageDto();
		image.setMission_no(mission_no);
		image.setUpdate_member(member_code);
		
		mrdao.deleteUserMission(image);
		image = mrdao.deleteUserMissionImage(image);
		
		return image;
	}

}
