package com.nordic.repository.test;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.nordic.dto.test.TestBean;

import java.util.List;

@Mapper
public interface TestMapper {

    @Select("SELECT * FROM MEMBER")
    List<TestBean> Data() throws Exception;
}
