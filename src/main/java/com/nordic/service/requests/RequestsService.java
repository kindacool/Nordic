package com.nordic.service.requests;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nordic.dto.goods.GoodsReqDto;
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

	public List<GoodsReqDto> findAllUnconfirmedRequest() {
		return requestsDao.findAllUnconfirmedRequest();
	}

	public List<GoodsReqDto> findAllRequest() {
		return requestsDao.findAllRequest();
	}

	public List<GoodsReqDto> findAllConfirmedRequest() {
		return requestsDao.findAllConfirmedRequest();
	}

	public List<GoodsReqDto> findAllAcceptedRequest() {
		return requestsDao.findAllAcceptedRequest();
	}
}
