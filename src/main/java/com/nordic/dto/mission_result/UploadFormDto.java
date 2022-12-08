package com.nordic.dto.mission_result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFormDto {
	private MissionMasterDto master;
	private MissionMasterImageDto image;
}
