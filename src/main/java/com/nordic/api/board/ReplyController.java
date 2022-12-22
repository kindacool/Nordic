package com.nordic.api.board;

import com.nordic.dto.board.BoardReplyDto;
import com.nordic.dto.common.ResponseDto;
import com.nordic.service.board.ReplyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class ReplyController {

    private final ReplyService service;
    // private final CustomUserDetailsService customeservice;

    @ApiOperation(value = "작성된 댓글 불러오기", notes = "게시글 고유넘버 값을 받아 글에 작성된 댓글 목록을 불러온다.")
    @GetMapping("/{board_no}/replies")
    public ResponseDto load(@PathVariable int board_no) throws Exception {
        List<BoardReplyDto> reply = service.load(board_no);

        return new ResponseDto(reply);
    }

    @ApiOperation(value = "댓글 작성하기", notes = "게시글 고유넘버 값을 받아 해당 글에 댓글을 작성한다.")
    @PostMapping("/{board_no}/reply")
    public ResponseDto input(@PathVariable("board_no") int board_no,
                             @RequestParam(required = true) Map<String, Object> map) throws Exception {
        log.info("write - {}", map);

        // String writer = customeservice.getUserInfo().get("member_code");

        service.input(map);
        List<BoardReplyDto> reply = service.load(board_no);

        return new ResponseDto("등록완료!", reply);
    }

    @ApiOperation(value = "작성한 댓글 수정하기", notes = "댓글 고유넘버 값을 받아 작성한 댓글을 수정한다.")
    @PutMapping("/{board_no}/reply")
    public ResponseDto modify(@PathVariable("board_no") int board_no,
                              @RequestBody Map<String, Object> map) throws Exception {
        log.info("modify - {}", board_no);
        log.info("modify - {}", map);

        // String writer = customeservice.getUserInfo().get("member_code");

        service.modify(map);

        return new ResponseDto("수정완료");
    }

    @ApiOperation(value = "작성한 댓글 삭제하기", notes = "댓글 고유넘버 값을 받아 작성한 댓글을 삭제한다.")
    @DeleteMapping("/{board_no}/reply")
    public ResponseDto delete(@PathVariable("board_no") int board_no,
                              @RequestBody(required = true) Map<String, Object> map) throws Exception {
        log.info("del map - {}", map.get("reply_no"));

        // String writer = customeservice.getUserInfo().get("member_code");

        service.delete(map);
        List<BoardReplyDto> reply = service.load(board_no);

        return new ResponseDto("삭제완료", reply);
    }
}
