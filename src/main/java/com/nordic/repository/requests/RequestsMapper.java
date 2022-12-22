package com.nordic.repository.requests;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.nordic.dto.requests.ConfirmedRequestsDto;
import com.nordic.dto.requests.GoodsReqDto;
import com.nordic.dto.requests.UnconfirmedRequestsDto;

@Mapper
public interface RequestsMapper {
	public void createRequest(GoodsReqDto goodsReqDto);

	public GoodsReqDto fineOneRequest(int no);

	void acceptRequest(GoodsReqDto goodsReqDto);

	public void rejectRequest(GoodsReqDto goodsReqDto);

	List<UnconfirmedRequestsDto> findAllUnconfirmedRequest(Map<String, Object> map);

	public List<GoodsReqDto> findAllRequest();

	List<ConfirmedRequestsDto> findAllConfirmedRequest(Map<String, Object> map);

	public List<GoodsReqDto> findRequestsByGoods(int no);

	public List<ConfirmedRequestsDto> myRequests(String member_code);

	public GoodsReqDto duplicateRequestsCheck(GoodsReqDto goodsReqDto);

	public void cancelRequest(GoodsReqDto goodsReqDto);
}
