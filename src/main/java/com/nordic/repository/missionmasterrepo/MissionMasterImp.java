package com.nordic.repository.missionmasterrepo;

import com.nordic.dto.missionmasterbean.MissionMasterBean;
import com.nordic.dto.missionmasterbean.MissionMasterListBean;
import com.nordic.dto.missionmasterbean.MissionSearchBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MissionMasterImp {

    List<MissionMasterBean> getAllData();
    void insertData(MissionMasterBean mmb);
    void updateData(MissionMasterBean mmb);
    void deleteDataByMissionNo(int mission_no , String update_member) ;
    MissionMasterBean getDataByMissionNo(int mission_no);
    List<MissionMasterListBean> list(MissionSearchBean msb);
    List<MissionMasterBean> topfive();
    List<MissionMasterBean> search(MissionSearchBean msb);

}
