package com.edu.seiryo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.edu.seiryo.model.CountResultModel;
import com.edu.seiryo.pojo.PurchaseList;
import com.edu.seiryo.query.PurchaseListQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 进货单接口
 * @author TianTian
 * @date 2022/1/21 18:27
 */
@Mapper
public interface PurchaseListMapper extends BaseMapper<PurchaseList> {
    
    IPage<CountResultModel> selectPurchaseCount(IPage<CountResultModel> page, @Param("query") PurchaseListQuery query);
}