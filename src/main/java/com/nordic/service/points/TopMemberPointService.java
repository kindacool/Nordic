package com.nordic.service.points;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nordic.dto.points.TopMemberPointDto;
import com.nordic.repository.points.TopMemberPointRepository;

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
