package com.nordic.repository.requests;

import java.util.List;
import java.util.Map;

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
	public List<UnconfirmedRequestsDto> findAllUnconfirmedRequest(Map<String, Object> map) {
		return requestsMapper.findAllUnconfirmedRequest(map);
	}
	
	@Override
	public List<GoodsReqDto> findAllRequest() {
		return requestsMapper.findAllRequest();
	}
	
	@Override
	public List<ConfirmedRequestsDto> findAllConfirmedRequest(Map<String, Object> map) {
		return requestsMapper.findAllConfirmedRequest(map);
	}
	
	@Override
	public List<GoodsReqDto> findRequestsByGoods(int no) {
		return requestsMapper.findRequestsByGoods(no);
	}

	public List<ConfirmedRequestsDto> myRequests(String member_code) {
		return requestsMapper.myRequests(member_code);
	}

	public GoodsReqDto duplicateRequestsCheck(GoodsReqDto goodsReqDto) {
		return requestsMapper.duplicateRequestsCheck(goodsReqDto);
	}

	public void cancelRequest(GoodsReqDto goodsReqDto) {
		requestsMapper.cancelRequest(goodsReqDto);
	}
}
