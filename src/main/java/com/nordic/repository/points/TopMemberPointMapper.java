package com.nordic.repository.points;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nordic.dto.points.TopMemberPointDto;

import lombok.extern.slf4j.Slf4j;

@Mapper
public interface TopMemberPointMapper {
	
	List<TopMemberPointDto> topRanking();

}
