package com.nordic.repository.requests;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nordic.dto.goods.GoodsReqDto;

@Mapper
public interface RequestsMapper {
	public void createRequest(GoodsReqDto goodsReqDto);

	public GoodsReqDto fineOneRequest(int no);

	void acceptRequest(GoodsReqDto goodsReqDto);

	public void rejectRequest(GoodsReqDto goodsReqDto);

	List<GoodsReqDto> findAllUnconfirmedRequest();

	public List<GoodsReqDto> findAllRequest();

	List<GoodsReqDto> findAllConfirmedRequest();

	public List<GoodsReqDto> findAllAcceptedRequest();
}
