package com.nordic.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardMasterDto {
    private int board_no;
    private String board_object;
    private String board_desc;
    private String board_group;
    private char reply_yn;
    private char use_yn;
    private char fix_yn;
    private String remark;
    private String create_member;
    private Date create_date;
    private String update_member;
    private Date update_date;
    private int board_image_no;
    private String image_file;
    private String orignal_name;
    private char image_use_yn;
}
