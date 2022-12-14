package com.nordic.api.missionmaster;

import com.nordic.dto.missionmasterbean.MissionMasterImageBean;
import com.nordic.service.missionmaster.MissionMasterImageService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
public class MissionMasterImageController {

    @Autowired
    private MissionMasterImageService mmis;

    //이미지 insert
    @PostMapping(value = "/image/{create_member}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void insertImage(@RequestPart MultipartFile[] uploadfiles, @PathVariable String create_member) throws Exception {
        mmis.insertImage(uploadfiles, create_member);
    }

    //미션 번호에 따른 이미지 수량
    @GetMapping("/countImage/{mission_no}")
    public @ResponseBody int countImage(@PathVariable int mission_no) throws ParseException {
        return mmis.countImage(mission_no);
    }
    //미션 번호에 따른 이미지정보 가져오기(2개 이상)
    @GetMapping(value = "/image/{mission_no}/{count}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImgByMissionNoCount(@PathVariable int mission_no,@PathVariable int count) throws Exception {
        return mmis.getImgByMissionNoCount(mission_no , count);
    }

    //미션 번호에 따른 이미지정보 가져오기(단일)
    @GetMapping(value = "/image/{mission_no}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImgByMissionNo(@PathVariable int mission_no) throws Exception {
        return mmis.getImgByMissionNo(mission_no);
    }

    //미션 번호에 따른 이미지정보 변경
    @PutMapping(value = "/image/{update_member}/{mission_no}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void updateImgByMissionNo(@RequestParam("uploadfiles") MultipartFile[] uploadfiles, @PathVariable String
            update_member, @PathVariable int mission_no) throws IOException, ParseException {
        mmis.updateImgByMissionNo(uploadfiles, update_member, mission_no);
    }

    //삭제
    @DeleteMapping("/image/{mission_no}")
    public void deleteImgByMissionNo(@PathVariable int mission_no) {
        mmis.deleteImgByMissionNo(mission_no);
    }

    // 사용 안할 예정--------------------------------------------------------------------------------------------------------------------
    @GetMapping("/imageA")
    public List<MissionMasterImageBean> getImgAll() {
        return mmis.getImgAll();
    }

    //test
    @GetMapping("/plzwork/{mission_no}")
    public String[] plzwork(@PathVariable int mission_no) throws Exception {
        return mmis.plzwork(mission_no);
    }

    //test2
    @GetMapping(value = "/plzwork2/{mission_no}" )
    public String plzwork2(@PathVariable int mission_no) throws Exception {
        System.out.println("contoller : "+mission_no);
        System.out.println(mmis.plzwork2(mission_no));
        return mmis.plzwork2(mission_no);
    }

}