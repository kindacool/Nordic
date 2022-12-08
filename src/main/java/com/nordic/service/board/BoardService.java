package com.nordic.service.board;

import com.nordic.dto.board.BoardMasterDto;

import java.util.List;

public interface BoardService {

    // C
    int input(BoardMasterDto board) throws Exception;

    // R
    List<BoardMasterDto> load() throws Exception;
    List<BoardMasterDto> load(int board_no) throws Exception;
    List<BoardMasterDto> load(String board_group) throws Exception;
    List<BoardMasterDto> load(String searchType,
                              String searchContent) throws Exception;

    // U
    int modify(int board_no,
               BoardMasterDto board) throws Exception;

    // D
    int delete(int board_no) throws Exception;
    int delete(int board_no,
               String update_member) throws Exception;

}
