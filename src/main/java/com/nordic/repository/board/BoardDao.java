package com.nordic.repository.board;

import com.nordic.dto.board.BoardMasterDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BoardDao {
    int inputBoard(BoardMasterDto board) throws Exception;
    int modifyBoard(@Param(value = "board_no") int board_no,
                    @Param(value = "board") BoardMasterDto board) throws Exception;

    int deleteBoard(@Param(value = "board_no") int board_no,
                    @Param(value = "update_member") String update_member) throws Exception;
    List<BoardMasterDto> loadBoardList() throws Exception;
    List<BoardMasterDto> loadBoard(int board_no) throws Exception;

    List<BoardMasterDto> loadMainContent(String board_group) throws Exception;
    List<BoardMasterDto> searchBoard(@Param(value = "searchType") String searchType,
                                     @Param(value = "searchContent") String searchContent) throws Exception;
}
