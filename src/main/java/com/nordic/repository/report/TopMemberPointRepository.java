package com.nordic.repository.report;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nordic.dto.report.TopMemberPointDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class TopMemberPointRepository implements TopMemberPointMapper {
	
	private final TopMemberPointMapper topMemberPointMapper;
	
	@Override
	public List<TopMemberPointDto> topRanking() {
		return topMemberPointMapper.topRanking();
	}

}
