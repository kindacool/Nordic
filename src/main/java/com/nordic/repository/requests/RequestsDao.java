package com.nordic.repository.requests;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nordic.dto.goods.GoodsReqDto;

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
	public List<GoodsReqDto> findAllUnconfirmedRequest() {
		return requestsMapper.findAllUnconfirmedRequest();
	}
	
	@Override
	public List<GoodsReqDto> findAllRequest() {
		return requestsMapper.findAllRequest();
	}
	
	@Override
	public List<GoodsReqDto> findAllConfirmedRequest() {
		return requestsMapper.findAllConfirmedRequest();
	}
	
	@Override
	public List<GoodsReqDto> findAllAcceptedRequest() {
		return requestsMapper.findAllAcceptedRequest();
	}
}
