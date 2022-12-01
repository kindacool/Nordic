package com.nordic.service.goods;

import java.util.List;
import org.springframework.stereotype.Service;
import com.nordic.dto.goods.GoodsDto;
import com.nordic.dto.goods.GoodsReqDto;
import com.nordic.repository.goods.GoodsDao;
import lombok.RequiredArgsConstructor;

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

	public List<GoodsDto> readAllGoods() {
		return goodsDao.readAllGoods();
	}
	
	public void deleteGoods(int no) {
		goodsDao.deleteGoods(no);
	}
	
	public void updateGoods(GoodsDto goodsDto) {
		goodsDao.updateGoods(goodsDto);
	}

	public List<GoodsDto> readAvailableGoods() {
		return goodsDao.readAvailableGoods();
	}



}
