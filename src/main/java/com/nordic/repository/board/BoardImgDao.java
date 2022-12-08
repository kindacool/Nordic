package com.nordic.repository.board;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Mapper
@Component
public interface BoardImgDao {
    int inputImage(Map<String, Object> param) throws Exception;
    int deleteImage(Map<String, Object> param) throws Exception;
}
