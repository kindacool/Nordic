package com.nordic.service.requests;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.nordic.dto.requests.AcceptedRequestsDto;
import com.nordic.dto.requests.GoodsReqDto;
import com.nordic.dto.requests.UnconfirmedRequestsDto;
import com.nordic.repository.requests.RequestsDao;
import com.nordic.service.points.PointsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestsService {
	private final RequestsDao requestsDao;
	
	public void createRequest(GoodsReqDto goodsReqDto) {
		requestsDao.createRequest(goodsReqDto);
	}

	public GoodsReqDto findOneRequest(int no) {
		return requestsDao.fineOneRequest(no);
	}

	public void acceptRequest(GoodsReqDto goodsReqDto) {
		requestsDao.acceptRequest(goodsReqDto);
	}

	public void rejectRequest(GoodsReqDto goodsReqDto) {
		requestsDao.rejectRequest(goodsReqDto);
	}

	public List<UnconfirmedRequestsDto> findAllUnconfirmedRequest(int pageNum) {
		PageHelper.startPage(pageNum,10);
		return requestsDao.findAllUnconfirmedRequest();
	}

	public List<GoodsReqDto> findAllRequest(int pageNum) {
		PageHelper.startPage(pageNum,10);
		return requestsDao.findAllRequest();
	}

	public List<GoodsReqDto> findAllConfirmedRequest(int pageNum) {
		PageHelper.startPage(pageNum,10);
		return requestsDao.findAllConfirmedRequest();
	}

	public List<AcceptedRequestsDto> findAllAcceptedRequest(int pageNum) {
		PageHelper.startPage(pageNum,10);
		return requestsDao.findAllAcceptedRequest();
	}
}
