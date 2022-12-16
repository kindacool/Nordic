package com.nordic.service.goods;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.nordic.api.goods.GoodsController;
import com.nordic.dto.goods.BestSellingGoodsDto;
import com.nordic.dto.goods.GoodsDto;
import com.nordic.repository.goods.GoodsDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsService {

	private final GoodsDao goodsDao;
	
	public void createGoods(GoodsDto goodsDto) {
		goodsDao.createGoods(goodsDto);
	}

	public GoodsDto readOneGoods(int no) {
		return goodsDao.readOneGoods(no);
	}

	public List<GoodsDto> readAllGoods(int pageNum, Map<String, Object> map) {
		PageHelper.startPage(pageNum, 10);
		return goodsDao.readAllGoods(map);
	}
	
	public void deleteGoods(GoodsDto goodsDTO) {
		goodsDao.deleteGoods(goodsDTO);
	}
	
	public void updateGoods(GoodsDto goodsDto) {
		goodsDao.updateGoods(goodsDto);
	}

	public List<GoodsDto> readAvailableGoods(int pageNum, String keyword) {
		PageHelper.startPage(pageNum, 12);
		return goodsDao.readAvailableGoods(keyword);
	}

	//@Cacheable("bestGoods")
	public List<BestSellingGoodsDto> getBestSellingGoods(int pageNum) {
		PageHelper.startPage(pageNum, 10);
		//log.info("cacheable 실행");
		return goodsDao.getBestSellingGoods();
	}


}
