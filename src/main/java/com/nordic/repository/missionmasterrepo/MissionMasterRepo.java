package com.nordic.repository.missionmasterrepo;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nordic.dto.missionmasterbean.MissionMasterBean;

@Repository
public class MissionMasterRepo {

    @Autowired
    private SqlSession ss;
    public List<MissionMasterBean> getAllData() {
        List<MissionMasterBean> list = ss.selectList("getAllData");
        return list;
    }

    public void insertData(MissionMasterBean mmb) {
        System.out.println("dao : " + mmb);
        ss.insert("insertData", mmb);
    }

    public void updateData(MissionMasterBean mmb) {
        ss.update("updateData", mmb);
    }

    public void deleteData(MissionMasterBean mmb) {
        ss.delete("deleteData", mmb);
    }

    public MissionMasterBean getsingleData(int mission_no) {
        MissionMasterBean missionMasterBean = ss.selectOne("getsingleData", mission_no);
        return missionMasterBean;
    }


}
