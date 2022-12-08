package com.nordic.service.board;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BoardImgUploadService {

    //  업로드
    List upload(List<MultipartFile> files) throws IOException;
    //  데이터베이스 입력
    int input(Map<String, Object> param) throws Exception;
    //  삭제 & 수정
    int delete(Map<String, Object> param) throws Exception;
}
