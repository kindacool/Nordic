package com.nordic.repository.requests;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nordic.dto.requests.ConfirmedRequestsDto;
import com.nordic.dto.requests.GoodsReqDto;
import com.nordic.dto.requests.UnconfirmedRequestsDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RequestsDao implements RequestsMapper{
	private final RequestsMapper requestsMapper;
	
	@Override
	public void createRequest(GoodsReqDto goodsReqDto) {
		requestsMapper.createRequest(goodsReqDto);
	}

	@Override
	public GoodsReqDto fineOneRequest(int no) {
		return requestsMapper.fineOneRequest(no);
	}

	@Override
	public void acceptRequest(GoodsReqDto goodsReqDto) {
		requestsMapper.acceptRequest(goodsReqDto);
	}

	@Override
	public void rejectRequest(GoodsReqDto goodsReqDto) {
		requestsMapper.rejectRequest(goodsReqDto);		
	}

	@Override
	public List<UnconfirmedRequestsDto> findAllUnconfirmedRequest() {
		return requestsMapper.findAllUnconfirmedRequest();
	}
	
	@Override
	public List<GoodsReqDto> findAllRequest() {
		return requestsMapper.findAllRequest();
	}
	
	@Override
	public List<ConfirmedRequestsDto> findAllConfirmedRequest() {
		return requestsMapper.findAllConfirmedRequest();
	}
	
	@Override
	public List<ConfirmedRequestsDto> findAllAcceptedRequest() {
		return requestsMapper.findAllAcceptedRequest();
	}
	
	@Override
	public List<ConfirmedRequestsDto> findAllRejectedRequest() {
		return requestsMapper.findAllRejectedRequest();
	}

	@Override
	public List<GoodsReqDto> findRequestsByGoods(int no) {
		return requestsMapper.findRequestsByGoods(no);
	}

	public List<GoodsReqDto> myRequests(String member_code) {
		return requestsMapper.myRequests(member_code);
	}
}
