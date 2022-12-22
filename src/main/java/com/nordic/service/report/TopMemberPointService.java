package com.nordic.service.report;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nordic.dto.report.TopMemberPointDto;
import com.nordic.repository.report.TopMemberPointRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class TopMemberPointService {
	
	private final TopMemberPointRepository topMemberPointRepository;
	
	public List<TopMemberPointDto> topRanking() {
		List<TopMemberPointDto> topRanking = topMemberPointRepository.topRanking();
		return topRanking;
	}
	

}
