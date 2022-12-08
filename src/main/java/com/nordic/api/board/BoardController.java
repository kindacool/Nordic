package com.nordic.api.board;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import com.github.pagehelper.autoconfigure.PageHelperStandardProperties;
import com.nordic.dto.board.BoardMasterDto;
import com.nordic.dto.common.ResponseDto;
import com.nordic.service.board.BoardImgUploadService;
import com.nordic.service.board.BoardService;
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
public class BoardController {

    private int pageSize = 20;
    private final BoardService service;
    private final BoardImgUploadService uploadService;

    // 게시글 작성
    @PostMapping("/board")
    public ResponseDto inputBoard(@RequestPart(value = "board", required = true) BoardMasterDto board,
                                  @RequestPart(value = "files", required = false) List<MultipartFile> files) throws Exception{
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

    // 게시글 전체목록
    @GetMapping("/board")
    public ResponseDto loadBoardList(@RequestParam(value = "page", required = false, defaultValue = "1") int page) throws Exception {
        PageHelper.startPage(page, pageSize);
        List<BoardMasterDto> boardList = service.load();

        return new ResponseDto(PageInfo.of(boardList));
    }

    // 특정 게시글 읽기
    @GetMapping("/board/{board_no}")
    public ResponseDto readBoard(@PathVariable(value = "board_no") int board_no,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") int page) throws Exception {
        List<BoardMasterDto> board = service.load(board_no);

        return new ResponseDto(board);
    }

    // 게시글 검색
    @GetMapping("/board/{searchType}/{searchContent}")
    public ResponseDto searchBoard(@PathVariable(value = "searchType") String searchType,
                                   @PathVariable(value = "searchContent") String searchContent,
                                   @RequestParam(value = "page", required = false, defaultValue = "1") int page) throws Exception {
        PageHelper.startPage(page, pageSize);
        List<BoardMasterDto> searchResult = service.load(searchType, searchContent);

        return new ResponseDto(PageInfo.of(searchResult));
    }

    // 게시글 수정
    @PutMapping("/board/{board_no}")
    public ResponseDto modifyBoard(@PathVariable("board_no") int board_no,
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

    @DeleteMapping("/board/{board_no}/{update_member}")
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
