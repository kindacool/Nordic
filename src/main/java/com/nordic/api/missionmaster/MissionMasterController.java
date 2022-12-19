package com.nordic.api.missionmaster;

import com.nordic.dto.missionmasterbean.MissionMasterBean;
import com.nordic.dto.missionmasterbean.MissionMasterListBean;
import com.nordic.dto.missionmasterbean.MissionSearchBean;
import com.nordic.service.missionmaster.MissionMasterService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.channels.AcceptPendingException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class MissionMasterController {

    @Autowired
    private MissionMasterService mms;

    //미션 번호에 따른 미션 정보
    @GetMapping(value = "/mission/{mission_no}")
    public MissionMasterBean getDataByMissionNo(@PathVariable int mission_no) {
        MissionMasterBean missionMasterBean = mms.getDataByMissionNo(mission_no);
        return missionMasterBean;
    }

    //미션 insert
    @PostMapping("/mission")
    public void insertData(@RequestBody MissionMasterBean mmb) {
        mms.insertData(mmb);
    }

    //mission update
    @PutMapping("/mission/{mission_no}")
    public void updateData(@PathVariable int mission_no, @RequestBody MissionMasterBean mmb) {
        mms.updateData(mission_no, mmb);
    }

    //missiondelete
    @DeleteMapping("/mission/{mission_no}/{update_member}")
    public void deleteDataByMissionNo(@PathVariable int mission_no, @PathVariable String update_member) {
        mms.deleteDataByMissionNo(mission_no, update_member);
    }

    //mission list
    @GetMapping(value = "/list")
    public List<MissionMasterListBean> list(MissionSearchBean msb) throws ParseException, IOException {
        System.out.println(msb);
        System.out.println(msb.getPageSize());
        System.out.println(msb.getPageNum());
        return mms.list(msb);
    }

    //get top five
    @GetMapping(value = "/search")
    public List<MissionMasterBean> search(MissionSearchBean msb) {
        System.out.println("msb : " + msb);
        return mms.search(msb);
    }

    @GetMapping("/topfive")
    public List<MissionMasterBean> topfive() {
        return mms.topfive();
    }

    // 사용 안할 예정--------------------------------------------------------------------------------------------------------------------
    //모든 정보 가져오기
    @GetMapping("/mission")
    public List<MissionMasterBean> getAllData() {
        List<MissionMasterBean> list = mms.getAllData();
        return list;
    }

}
