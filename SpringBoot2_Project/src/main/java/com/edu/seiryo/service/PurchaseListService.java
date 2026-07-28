package com.edu.seiryo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.seiryo.pojo.PurchaseList;
import com.edu.seiryo.pojo.PurchaseListGoods;
import com.edu.seiryo.query.PurchaseListQuery;

import java.util.List;
import java.util.Map;

/**
 * 进货单服务类
 * @author TianTian
 * @date 2022/1/19 13:58
 */
public interface PurchaseListService extends IService<PurchaseList> {

    // 1. 生成进货单号
    String createPurchaseNumber();

    // 2. 保存进货单（包含商品列表），对应 Controller 中的 save 方法
    void savePurchaseList(PurchaseList purchaseList, List<PurchaseListGoods> plgList);

    // 3. 分页查询进货单列表
    Map<String, Object> purchaseList(PurchaseListQuery purchaseListQuery);

    // 4. 删除进货单
    void deletePurchaseList(Integer id);
}