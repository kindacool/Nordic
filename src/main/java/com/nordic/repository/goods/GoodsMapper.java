package com.nordic.repository.goods;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nordic.dto.goods.GoodsDto;
import com.nordic.dto.goods.GoodsReqDto;



@Mapper
public interface GoodsMapper {
	public void createGoods(GoodsDto goodsDto);

	public GoodsDto readOneGoods(int goodsNo);
	
	public List<GoodsDto> readAllGoods();
	
	public void deleteGoods(int no);
	
	public void updateGoods(GoodsDto goodsDto);
	
	public List<GoodsDto> readAvailableGoods();
	
	
}
