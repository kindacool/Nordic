package com.nordic.service.board;

import com.nordic.dto.board.BoardReplyDto;
import com.nordic.repository.board.BoardReplyDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
@Component
public class ReplyServiceImp implements ReplyService{

    private final BoardReplyDao dao;

    @Override
    public List<BoardReplyDto> load(int board_no) throws Exception {
        return dao.loadReplyList(board_no);
    }

    @Override
    public int input(Map<String, Object> map) throws Exception {
        return dao.inputReply(map);
    }

    @Override
    public int modify(Map<String, Object> map) throws Exception {
        return dao.modifyReply(map);
    }

    @Override
    public int delete(Map<String, Object> map) throws Exception {
        return dao.deleteReply(map);
    }
}
