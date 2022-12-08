package com.nordic.api.board;

import com.nordic.dto.common.ResponseDto;
import com.nordic.service.board.BoardImgUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${file.dir}")
    private String PATH;
    private final BoardImgUploadService service;

    @GetMapping(value = "/img/{imgName}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public @ResponseBody byte[] getImg(@PathVariable String imgName) throws IOException {
        InputStream in = new FileInputStream(PATH + imgName);
        return IOUtils.toByteArray(in);
    }

    @DeleteMapping("/img/{update_member}/{board_image_no}")
    public ResponseDto deleteImg(@PathVariable("update_member") String update_member,
                                 @PathVariable("board_image_no") int board_image_no) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("update_member", update_member);
        param.put("board_image_no", board_image_no);

        service.delete(param);

        return new ResponseDto("삭제완료!");
    }
}
