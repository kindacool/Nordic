package com.nordic.repository.goods;

import java.util.List;

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
	public List<GoodsDto> readAllGoods() {
		return goodsMapper.readAllGoods();
	}
	
	@Override
	public void deleteGoods(int no) {
		goodsMapper.deleteGoods(no);
	}
	
	@Override
	public void updateGoods(GoodsDto goodsDto) {
		goodsMapper.updateGoods(goodsDto);
	}

	@Override
	public List<GoodsDto> readAvailableGoods() {
		return goodsMapper.readAvailableGoods();
	}
	
	@Override
	public List<BestSellingGoodsDto> getBestSellingGoods() {
		return goodsMapper.getBestSellingGoods();
	}



}
