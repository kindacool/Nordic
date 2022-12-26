package com.nordic.service.missionmaster;

import com.github.pagehelper.PageHelper;
import com.nordic.dto.member.MemberDto;
import com.nordic.dto.missionmasterbean.MissionMasterBean;
import com.nordic.dto.missionmasterbean.MissionMasterListBean;
import com.nordic.dto.missionmasterbean.MissionSearchBean;
import com.nordic.repository.missionmasterrepo.MissionMasterImp;
import com.nordic.service.member.MemberService;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class MissionMasterService {

    @Autowired
    private MissionMasterImp mmd;

    @Autowired
    private MemberService ms;

    public List<MissionMasterBean> getAllData() {
        List<MissionMasterBean> list = mmd.getAllData();
        return list;
    }

    //정보 입력
    public void insertData(MissionMasterBean mmb) {
        MemberDto md =  ms.findOne(mmb.getCreate_member());
        mmb.setCreate_member(md.getMember_name());
        mmb.setUpdate_member(md.getMember_name());

        mmd.insertData(mmb);

    }

    public void updateData(int mission_no, MissionMasterBean mmb) {
        mmb.setMission_no(mission_no);
        mmd.updateData(mmb);
    }

    public void deleteDataByMissionNo(int mission_no, String update_member) {
        mmd.deleteDataByMissionNo(mission_no, update_member);
    }

    public MissionMasterBean getDataByMissionNo(int mission_no) {
        MissionMasterBean missionMasterBean = mmd.getDataByMissionNo(mission_no);
        return missionMasterBean;
    }


    public List<MissionMasterListBean> list(MissionSearchBean msb){

        PageHelper.startPage(msb.getPageNum(), msb.getPageSize());

        JSONParser jsonParser = new JSONParser();


        List<MissionMasterListBean> listBeans = mmd.list(msb);
        for (int a = 0; a < listBeans.size(); a++) {
            MissionMasterListBean member = listBeans.get(a);
            Object o = null;
            try {
                o = jsonParser.parse(member.getConfirm_file());
            } catch (ParseException e) {
                e.getMessage();
            }

            JSONObject object = (JSONObject) o;
            JSONArray array = (JSONArray) object.get("picture");
            JSONObject test = (JSONObject) array.get(0);
            String fileName = (String) test.get("0");
            String path1 = System.getProperty("user.dir");
            String path = path1+"\\src\\main\\resources\\static\\img\\missionmaster\\";
            String fullName = path + fileName;
            //변환
            InputStream in = null;
            try {
                in = new FileInputStream(fullName);
            } catch (FileNotFoundException e) {
                e.getMessage();
            }
            byte[] byteArray = new byte[0];
            try {
                byteArray = IOUtils.toByteArray(in);
            } catch (IOException e) {
                e.getMessage();
            }
            member.setConfirm_file2(Arrays.toString(byteArray));
        }
        return listBeans;
    }

    public List<MissionMasterBean> topfive(){
        return mmd.topfive();
    }

    public List<MissionMasterBean> search(MissionSearchBean msb){
        PageHelper.startPage(msb.getPageNum(), msb.getPageSize());

        if(msb.getSecond().equals("undefined")){
            msb.setSecond(null);
        }

        List<MissionMasterBean> list = mmd.search(msb);

        return list;
    }
}
