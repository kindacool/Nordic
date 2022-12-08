package com.nordic.repository.missionmasterrepo;

import com.nordic.dto.missionmasterbean.MissionMasterImageBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface MissionMasterImageImp {

    @Select("select confirm_file from mission_master_image where mission_no = #{mission_no}")
    String getImgByMissionNo(int mission_no);

    void insertImage(String confirm_file, String create_member);

    void updateImgByMissionNo(String confirm_file, String update_member, int mission_no);

    @Update("update mission_master_image set use_yn= 'N' where mission_no = #{mission_no}")
    void deleteImgByMissionNo(int mission_no);

    @Select("select * from mission_master_image")
    List<MissionMasterImageBean> getImgAll();

}