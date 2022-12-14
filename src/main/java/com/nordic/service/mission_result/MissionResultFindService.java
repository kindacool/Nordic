package com.nordic.service.mission_result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.relational.core.sql.Not;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.nordic.api.mission_result.MissionResultFindController;
import com.nordic.dto.common.ResponseDto;
import com.nordic.dto.mission_result.MissionHistoryDetailDto;
import com.nordic.dto.mission_result.MissionHistoryDto;
import com.nordic.dto.mission_result.MissionHistoryImageDto;
import com.nordic.dto.mission_result.MissionMasterDto;
import com.nordic.dto.mission_result.MissionMasterImageDto;
import com.nordic.dto.mission_result.MissionStatusDto;
import com.nordic.dto.mission_result.PointHistoryDto;
import com.nordic.exception.NotConfirmException;
import com.nordic.repository.mission_result.MissionResultFindDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MissionResultFindService {
	private final MissionResultFindDao msdao;
	
	/* 전체 기간 조회  */
	public Map getPeriod() {
		return msdao.getPeriod();
	}
	
	/* 미션 수행 등록 리스트 */
	public List<MissionStatusDto> findAllMission(int page, Boolean checked, String start_date, String end_date) {
		Map total = getPeriod();
		
		/* 기간 조회 */
		if (start_date==null || start_date.isEmpty()) {
			start_date = (String) total.get("min");
		} 
		
		if (end_date==null || end_date.isEmpty()) {
			end_date = (String) total.get("max");
		}
		
		log.info("=== start_date : "+start_date+" ~ end_date : "+end_date+" ===");
		
		Map period = new HashMap();
		period.put("start_date", start_date);
		period.put("end_date", end_date);
		
		/* 미승인건 검색 */
		if (checked.equals(true)) {
			PageHelper.startPage(page,10);
			return msdao.findNotConfirm(period);
		} 
		
		PageHelper.startPage(page,10);
		return msdao.findAllMission(period);
	}
	
	/* 미승인된 미션수 */
	public int countNewResult() {
		return msdao.countNewResult();
	}
	
	/* 미션 수행 상세 정보 */
	public MissionHistoryDetailDto findOneMission(int mission_history_no) {

		MissionHistoryDto history = msdao.selectHistoryByNo(mission_history_no);
		MissionHistoryImageDto history_img = msdao.selectHistoryImg(mission_history_no);
		
		int mission_no = history.getMission_no();
		MissionMasterDto master = msdao.selectMasterByNo(mission_no);
		MissionMasterImageDto master_img = msdao.selectMasterImg(mission_no);
		
		MissionHistoryDetailDto detail = new MissionHistoryDetailDto(master, master_img, history, history_img);
		
		return detail;
	}
	
	/* 미션 수행 승인 거절 */
	public ResponseDto confirmMission(int mission_history_no, String member_code, int check, String remark)  throws Exception {
		
		// 이미 처리가 완료된 건
		MissionHistoryDto historyMission = msdao.selectHistoryByNo(mission_history_no);
		if (historyMission.getConfirm_member() != null) {
			throw new NotConfirmException(NotConfirmException.MRF_0001);
		}
		
		// 거절 사유가 입력되지 않은 건
		if(check==2 && remark.equals("")) {
			throw new NotConfirmException(NotConfirmException.MRF_0002);
		}
		
		MissionHistoryDto updateMission = new MissionHistoryDto();
		updateMission.setMission_history_no(mission_history_no);
		updateMission.setConfirm_member(member_code);
		updateMission.setRemark(remark);
		
		String message = "";
		MissionHistoryDto mission = null;
		
		/* 승인  */
		if (check==1) {
			message = "승인 성공";
			mission = msdao.confirmMission(updateMission);
			
		/* 거절 */
		} else if (check==2) {
			message = "승인 거절";
			mission = msdao.refuseMission(updateMission);
		}
		
		return new ResponseDto(message, mission);
	}
	
	/* 포인트 지급 */
	public PointHistoryDto addPoint(int mission_history_no, String member_code) {
		MissionHistoryDto mission = msdao.selectHistoryByNo(mission_history_no);
		
		PointHistoryDto point_history = new PointHistoryDto();
		point_history.setPoint(mission.getPoint());
		point_history.setMember_code(mission.getMember_code());
		point_history.setCreate_member(member_code);
		point_history.setMission_history_no(mission_history_no);
		point_history = msdao.insertPoint(point_history);
		
		msdao.updateMemberPoint(mission);
		
		return point_history;
	}
	
}