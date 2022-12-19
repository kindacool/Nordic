package com.nordic.dto.missionmasterbean;

import lombok.Data;

@Data
public class MissionSearchBean {

    private int pageNum;
    private int pageSize = 8;
    private String first;
    private String second;

}
