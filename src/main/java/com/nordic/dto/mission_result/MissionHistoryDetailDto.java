package com.nordic.dto.mission_result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MissionHistoryDetailDto {
	
	public MissionHistoryDetailDto(MissionMasterDto master, MissionMasterImageDto master_img,
			MissionHistoryImageDto history_img) {
		this.master = master;
		this.master_img = master_img;
		this.history_img = history_img;
	}
	
	private MissionMasterDto master;
	private MissionMasterImageDto master_img;
	private MissionHistoryDto history;
	private MissionHistoryImageDto history_img;
}
