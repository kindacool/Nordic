package com.nordic.api;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import com.nordic.dto.missionmasterbean.MissionMasterImageBean;
import com.nordic.dto.missionmasterbean.MissionMasterListBean;
import com.nordic.dto.missionmasterbean.MissionSearchBean;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nordic.dto.missionmasterbean.MissionMasterBean;
import com.nordic.service.MissionMasterService;
import springfox.documentation.spring.web.json.Json;

@CrossOrigin("*")
@RestController
public class MissionMasterController {

    @Autowired
    private MissionMasterService mms;

    //모든 정보 가져오기
    @GetMapping("/mission")
    public List<MissionMasterBean> getAllData() {
        List<MissionMasterBean> list = mms.getAllData();
        return list;
    }

    @GetMapping(value = "/missionByNo/{mission_no}"   )
    public MissionMasterBean getDataByMissionNo(@PathVariable int mission_no) {
        MissionMasterBean missionMasterBean  = mms.getDataByMissionNo(mission_no);

        return missionMasterBean;
    }

    @PostMapping("/mission")
    public void insertData(@RequestBody MissionMasterBean mmb){
        mms.insertData(mmb);
    }

    @PutMapping("/mission/{mission_no}")
    public void updateData(@PathVariable int mission_no ,@RequestBody MissionMasterBean mmb){
        mms.updateData(mission_no , mmb);
    }

    @DeleteMapping("/mission/{mission_no}/{update_member}")
    public void deleteDataByMissionNo(@PathVariable int mission_no,@PathVariable String update_member){
        mms.deleteDataByMissionNo(mission_no , update_member);
    }

    @GetMapping(value = "/list")
    public List<MissionMasterListBean> list(MissionSearchBean msb) throws ParseException, IOException {
        return mms.list(msb);
    }


}
