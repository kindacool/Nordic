package com.nordic.api.missionmaster;

import com.nordic.dto.missionmasterbean.MissionMasterBean;
import com.nordic.dto.missionmasterbean.MissionMasterListBean;
import com.nordic.dto.missionmasterbean.MissionSearchBean;
import com.nordic.service.missionmaster.MissionMasterService;
import io.swagger.annotations.ApiOperation;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class MissionMasterController {

    @Autowired
    private MissionMasterService mms;

    //미션 번호에 따른 미션 정보
    @ApiOperation(value = "미션 상세 정보" , notes = "미션에 대한 상세 정보를 불러온다")
    @GetMapping(value = "/mission/{mission_no}")
    public MissionMasterBean getDataByMissionNo(@PathVariable int mission_no) {
        MissionMasterBean missionMasterBean = mms.getDataByMissionNo(mission_no);
        return missionMasterBean;
    }

    //미션 insert
    @ApiOperation(value = "미션 등록", notes = "새로운 미션 정보를 저장한다.")
    @PostMapping("/mission")
    public void insertData(@RequestBody MissionMasterBean mmb) {
        System.out.println("mmb : "+mmb);
        mms.insertData(mmb);
    }

    //mission update
    @ApiOperation(value = "미션 수정" , notes = "기존 미션 정보를 수정한다")
    @PutMapping("/mission/{mission_no}")
    public void updateData(@PathVariable int mission_no, @RequestBody MissionMasterBean mmb) {
        mms.updateData(mission_no, mmb);
    }

    //missiondelete
    @ApiOperation(value = "미션 삭제" , notes = "기존 미션 정보를 삭제한다.")
    @DeleteMapping("/mission/{mission_no}/{update_member}")
    public void deleteDataByMissionNo(@PathVariable int mission_no, @PathVariable String update_member) {
        mms.deleteDataByMissionNo(mission_no, update_member);
    }

    //mission list
    @ApiOperation(value = "모든 미션 리스트" , notes = "모든 미션 정보를 page단위로 가져온다")
    @GetMapping(value = "/list")
    public List<MissionMasterListBean> list(MissionSearchBean msb) throws ParseException, IOException {
        List list  = mms.list(msb);
        return list;
    }

    //get top five
    @ApiOperation(value = "미션 조회" , notes = "키워드에 따른 검색결과를 나타낸다.")
    @GetMapping(value = "/search")
    public List<MissionMasterBean> search(MissionSearchBean msb) {
        return mms.search(msb);
    }

}
