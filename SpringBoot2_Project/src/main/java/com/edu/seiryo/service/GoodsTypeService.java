package com.edu.seiryo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.seiryo.dto.TreeDto;
import com.edu.seiryo.pojo.GoodsType;

import java.util.List;

public interface GoodsTypeService extends IService<GoodsType> {
	
    List<TreeDto> queryAllGoodsTypes(Integer typeId);
    void addGoodsType(GoodsType goodsType);
    void updateGoodsType(GoodsType goodsType);
    void deleteGoodsType(Integer id);
	GoodsType getById(Object typeId);
}