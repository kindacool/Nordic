package com.nordic.repository.goods;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.nordic.dto.goods.BestSellingGoodsDto;
import com.nordic.dto.goods.GoodsDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GoodsDao implements GoodsMapper{
	
	private final GoodsMapper goodsMapper;

	@Override
	public void createGoods(GoodsDto goodsDto) {
		goodsMapper.createGoods(goodsDto);
	}

	@Override
	public GoodsDto readOneGoods(int no) {
		return goodsMapper.readOneGoods(no);
	}
	
	@Override
	public List<GoodsDto> readAllGoods(Map<String, Object> map) {
		return goodsMapper.readAllGoods(map);
	}
	
	@Override
	public void deleteGoods(GoodsDto goodsDTO) {
		goodsMapper.deleteGoods(goodsDTO);
	}
	
	@Override
	public void updateGoods(GoodsDto goodsDto) {
		goodsMapper.updateGoods(goodsDto);
	}

	@Override
	public List<GoodsDto> readAvailableGoods(String keyword) {
		return goodsMapper.readAvailableGoods(keyword);
	}
	
	@Override
	public List<BestSellingGoodsDto> getBestSellingGoods() {
		return goodsMapper.getBestSellingGoods();
	}



}
