package com.nordic.dto.missionmasterbean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MissionSearchBean {

    private int pageNum;
    private int pageSize = 8;
    private String first;
    private String second;


}
