package com.nordic.api.board;

import com.nordic.dto.common.ResponseDto;
import com.nordic.service.board.BoardImgUploadService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardImgController {

    private final BoardImgUploadService service;
    // private final CustomUserDetailsService customeservice;

    @ApiOperation(value = "이미지 불러오기", notes = "이미지 이름 값을 받아 바이너리 코드로 전송한다.")
    @GetMapping(value = "/img/{imgName}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public @ResponseBody byte[] getImg(@PathVariable String imgName) throws IOException {
        String PATH = System.getProperty("user.dir") + "/src/main/resources/static/img/board/";
        log.info("path - {}", PATH);
        InputStream in = new FileInputStream(PATH + imgName);
        return IOUtils.toByteArray(in);
    }

    @ApiOperation(value = "이미지 삭제", notes = "이미지 고유넘버와 수정자의 값을 받아 삭제한다.")
    @DeleteMapping("/img/{update_member}/{board_image_no}")
    public ResponseDto deleteImg(@PathVariable("update_member") String update_member,
                                 @PathVariable("board_image_no") int board_image_no) throws Exception {
        // String writer = customeservice.getUserInfo().get("member_code");

        log.info("member - {}", update_member);
        log.info("no - {}", board_image_no);
        Map<String, Object> param = new HashMap<>();
        param.put("update_member", update_member);
        param.put("board_image_no", board_image_no);

        service.delete(param);

        return new ResponseDto("삭제완료!");
    }
}
