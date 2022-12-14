package com.nordic.dto.missionmasterbean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Data
public class MissionMasterBean {

    private int mission_no;
    private String mission_name;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern ="yyyy-MM-dd")
    private java.sql.Date start_date;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern ="yyyy-MM-dd")
    private java.sql.Date end_date;
    private String level_code;
    private int point;
    private String zip_code;
    private String address1;
    private String address2;
    private char use_yn;
    private String remark;
    private String create_member;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern ="yyyy-MM-dd")
    private java.sql.Date create_date;
    private String update_member;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern ="yyyy-MM-dd")
    private java.sql.Date update_date;


}
