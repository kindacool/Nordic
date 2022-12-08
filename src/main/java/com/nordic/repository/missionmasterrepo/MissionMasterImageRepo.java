package com.nordic.repository.missionmasterrepo;

import com.nordic.dto.missionmasterbean.MissionMasterBean;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MissionMasterImageRepo {

    @Autowired
    private SqlSession ss;

    public void uploadImg(MissionMasterBean mmb){
        ss.insert("uploadImg",mmb);
    }


}
