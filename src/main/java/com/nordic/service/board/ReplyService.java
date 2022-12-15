package com.nordic.service.board;

import com.nordic.dto.board.BoardReplyDto;

import java.util.List;
import java.util.Map;

public interface ReplyService {

    // 불러오기
    List<BoardReplyDto> load(int board_no) throws Exception;

    // 등록
    int input(Map<String, Object> map) throws Exception;

    // 수정
    int modify(Map<String, Object> map) throws Exception;

    // 삭제
    int delete(Map<String, Object> map) throws Exception;
}
