package com.nordic.api.missionmaster;

import com.nordic.service.missionmaster.MissionMasterImageService;
import io.swagger.annotations.ApiOperation;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class MissionMasterImageController {

    @Autowired
    private MissionMasterImageService mmis;

    //이미지 insert
    @ApiOperation(value = "미션 이미지 등록", notes = "미션이 등록되면 전달받은 이미지를 입력한다.")
    @PostMapping(value = "/image/{create_member}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void insertImage(@RequestPart MultipartFile[] uploadfiles, @PathVariable String create_member) throws Exception {
        System.out.println(uploadfiles);
        mmis.insertImage(uploadfiles, create_member);
    }

    //미션 번호에 따른 이미지 수량
    @ApiOperation(value = "미션 이미지 수량" , notes = "미션 상세 정보에 따른 이미지를 수량을 가져온다")
    @GetMapping("/countImage/{mission_no}")
    public @ResponseBody int countImage(@PathVariable int mission_no) throws ParseException {
        return mmis.countImage(mission_no);
    }
    //미션 번호에 따른 이미지정보 가져오기(2개 이상)
    @ApiOperation(value = "미션이미지 2개 이상 출력" , notes = "해당 미션이 가지고 있는 이미지를 순서대로 출력한다.")
    @GetMapping(value = "/image/{mission_no}/{count}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImgByMissionNoCount(@PathVariable int mission_no,@PathVariable int count) throws Exception {
        return mmis.getImgByMissionNoCount(mission_no , count);
    }

    //미션 번호에 따른 이미지정보 가져오기(단일)
    @ApiOperation(value ="미션 이미지 단일 출력" ,notes = "미션이 가지고 있는 가장 첫번째 이미지를 출력한다")
    @GetMapping(value = "/image/{mission_no}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImgByMissionNo(@PathVariable int mission_no) throws Exception {
        return mmis.getImgByMissionNo(mission_no);
    }

    //미션 번호에 따른 이미지정보 변경
    @ApiOperation(value = "미션 이미지 수정" , notes = "기존에 가지고 있는 이미지를 삭제하고, 새로운 이미지를 저장한다.")
    @PutMapping(value = "/image/{update_member}/{mission_no}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void updateImgByMissionNo(@RequestParam("uploadfiles") MultipartFile[] uploadfiles, @PathVariable String
            update_member, @PathVariable int mission_no) throws IOException, ParseException {
        mmis.updateImgByMissionNo(uploadfiles, update_member, mission_no);
    }

    //삭제
    @DeleteMapping("/image/{mission_no}")
    @ApiOperation(value ="미션 이미지 삭제" , notes = "미션 번호에 따른 이미지를 삭제한다.")
    public void deleteImgByMissionNo(@PathVariable int mission_no) {
        mmis.deleteImgByMissionNo(mission_no);
    }

}