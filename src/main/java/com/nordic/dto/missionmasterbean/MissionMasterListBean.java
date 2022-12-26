package com.nordic.dto.missionmasterbean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Data
public class MissionMasterListBean {

    private int mission_no;
    private String mission_name;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern ="yyyy-MM-dd")
    private java.sql.Date start_date;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern ="yyyy-MM-dd")
    private java.sql.Date end_date;
    private String level_code;
    private int point;
    private String confirm_file;
    private String confirm_file2;
    private int pagepart;
    private final String alert = "Mission 이 없습니다.";

}
