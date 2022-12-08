package com.nordic.service.board;

import com.nordic.dto.board.BoardMasterDto;
import com.nordic.repository.board.BoardDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImp implements BoardService{

    private final BoardDao dao;
    @Override
    public int input(BoardMasterDto board) throws Exception {
        return dao.inputBoard(board);
    }

    @Override
    public List<BoardMasterDto> load() throws Exception {
        return dao.loadBoardList();
    }

    @Override
    public List<BoardMasterDto> load(int board_no) throws Exception {
        return dao.loadBoard(board_no);
    }

    @Override
    public List<BoardMasterDto> load(String board_group) throws Exception {
        return dao.loadMainContent(board_group);
    }

    @Override
    public List<BoardMasterDto> load(String searchType, String searchContent) throws Exception {
        return dao.searchBoard(searchType, searchContent);
    }

    @Override
    public int modify(int board_no, BoardMasterDto board) throws Exception {
        return dao.modifyBoard(board_no, board);
    }

    @Override
    public int delete(int board_no) throws Exception {
        return 0;
    }

    @Override
    public int delete(int board_no, String update_member) throws Exception {
        return dao.deleteBoard(board_no, update_member);
    }
}
