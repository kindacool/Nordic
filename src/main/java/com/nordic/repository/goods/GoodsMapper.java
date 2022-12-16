package com.nordic.repository.goods;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.nordic.dto.goods.BestSellingGoodsDto;
import com.nordic.dto.goods.GoodsDto;



@Mapper
public interface GoodsMapper {
	public void createGoods(GoodsDto goodsDto);

	public GoodsDto readOneGoods(int goodsNo);
	
	public List<GoodsDto> readAllGoods(Map<String, Object> map);
	
	public void deleteGoods(GoodsDto goodsDTO);
	
	public void updateGoods(GoodsDto goodsDto);
	
	public List<GoodsDto> readAvailableGoods(String keyword);

	public List<BestSellingGoodsDto> getBestSellingGoods();
	
	
}
