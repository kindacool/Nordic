package com.nordic.api.main;

import com.nordic.dto.board.BoardMasterDto;
import com.nordic.dto.common.ResponseDto;
import com.nordic.service.board.BoardImgUploadService;
import com.nordic.service.board.BoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OriginController {

    private final BoardService service;
    private final BoardImgUploadService uploadService;

    // 기원 작성
    @ApiOperation(value = "기원 작성하기", notes = "작성에 필요한 값을 받아 기원 글을 작성한다.")
    @PostMapping("/origin")
    public ResponseDto inputIntro(@RequestPart("board") BoardMasterDto board,
                                  @RequestPart("files") List<MultipartFile> files) throws Exception{
        log.info("board - {}", board);
        log.info("files - {}", files);

        List<String> image_file;
        List<String> orignal_name = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        service.input(board);

        if (!CollectionUtils.isEmpty(files)) {
            image_file = uploadService.upload(files);
            for (MultipartFile file : files) {
                orignal_name.add(file.getOriginalFilename());
            }

            param.put("board_no", board.getBoard_no());
            param.put("image_file", image_file);
            param.put("orignal_name", orignal_name);
            param.put("create_member", board.getCreate_member());

            uploadService.input(param);
        }

        return new ResponseDto("등록완료!");
    }

    // 기원 불러오기
    @ApiOperation(value = "기원 불러오기", notes = "작성된 기원 글 데이터를 불러온다.")
    @GetMapping("/origin")
    public ResponseDto loadIntro() throws Exception {
        String board_group = "기원";
        List<BoardMasterDto> introduce = service.load(board_group);

        return new ResponseDto(introduce);
    }

    // 기원 수정
    @ApiOperation(value = "기원 수정하기", notes = "필요한 값을 받아 작성된 기원 글 데이터를 수정한다.")
    @PutMapping("/origin/{board_no}")
    public ResponseDto modifyIntro(@PathVariable(value = "board_no") int board_no,
                                   @RequestPart("board") BoardMasterDto board,
                                   @RequestPart(value = "files", required = false) List<MultipartFile> files) throws Exception {
        log.info("board - {}", board);
        log.info("files - {}", files);

        List<String> image_file;
        List<String> orignal_name = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        service.modify(board_no, board);

        if (!CollectionUtils.isEmpty(files)) {
            image_file = uploadService.upload(files);
            for (MultipartFile file : files) {
                orignal_name.add(file.getOriginalFilename());
            }

            param.put("board_no", board.getBoard_no());
            param.put("image_file", image_file);
            param.put("orignal_name", orignal_name);
            param.put("create_member", board.getCreate_member());

            uploadService.input(param);
        }

        return new ResponseDto("수정완료!");
    }

    // 기원 삭제
    @ApiOperation(value = "기원 삭제하기", notes = "고유넘버 값을 받아 작성된 기원 글 데이터를 삭제한다.")
    @DeleteMapping("/origin/{board_no}/{update_member}")
    public ResponseDto deleteBoard(@PathVariable(value = "board_no") int board_no,
                                   @PathVariable(value = "update_member") String update_member) throws Exception {
        Map<String, Object> param = new HashMap<>();
        List<BoardMasterDto> board = service.load(board_no);
        service.delete(board_no, update_member);

        for(BoardMasterDto img : board) {
            if(img.getImage_file() != null) {
                param.put("update_member", update_member);
                param.put("board_no", board_no);
                uploadService.delete(param);
                break;
            }
            break;
        }

        return new ResponseDto("삭제완료!");
    }
}
