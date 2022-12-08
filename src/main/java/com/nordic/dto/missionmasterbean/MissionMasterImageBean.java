package com.nordic.dto.missionmasterbean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Getter
@Setter
public class MissionMasterImageBean {

    private int image_no;
    private int mission_no;
//    private String confirm_file;
//    private MultipartFile confirm_file;
    private char use_yn;
    private String remark;
    private String create_member;
    private Date create_date;
    private String update_member;
    private Date update_date;

}
