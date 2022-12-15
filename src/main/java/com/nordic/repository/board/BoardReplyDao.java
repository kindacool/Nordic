package com.nordic.repository.board;

import com.nordic.dto.board.BoardReplyDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface BoardReplyDao {

    int inputReply(Map<String, Object> map) throws Exception;
    List<BoardReplyDto> loadReplyList(int board_no) throws Exception;
    int modifyReply(Map<String, Object> map) throws Exception;
    int deleteReply(Map<String, Object> map) throws Exception;
}
