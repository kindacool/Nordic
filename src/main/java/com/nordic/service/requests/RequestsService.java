package com.nordic.service.requests;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.nordic.dto.requests.ConfirmedRequestsDto;
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

	public List<ConfirmedRequestsDto> findAllConfirmedRequest(int pageNum) {
		PageHelper.startPage(pageNum,10);
		return requestsDao.findAllConfirmedRequest();
	}

	public List<ConfirmedRequestsDto> findAllAcceptedRequest(int pageNum) {
		PageHelper.startPage(pageNum,10);
		return requestsDao.findAllAcceptedRequest();
	}
	
	public List<ConfirmedRequestsDto> findAllRejectedRequest(int pageNum) {
		PageHelper.startPage(pageNum,10);
		return requestsDao.findAllRejectedRequest();
	}

	public List<GoodsReqDto> findRequestsByGoods(int no, int pageNum) {
		PageHelper.startPage(pageNum,10);
		return requestsDao.findRequestsByGoods(no);
	}

	public List<GoodsReqDto> myRequests(String member_code, int pageNum) {
		PageHelper.startPage(pageNum,10);
		return requestsDao.myRequests(member_code);
	}
	

}
