package com.nordic.repository.requests;

import java.util.List;

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

	List<UnconfirmedRequestsDto> findAllUnconfirmedRequest();

	public List<GoodsReqDto> findAllRequest();

	List<ConfirmedRequestsDto> findAllConfirmedRequest();

	public List<ConfirmedRequestsDto> findAllAcceptedRequest();

	List<ConfirmedRequestsDto> findAllRejectedRequest();
}
