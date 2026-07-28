package com.edu.seiryo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.seiryo.mapper.PurchaseListMapper;
import com.edu.seiryo.mapper.SaleListMapper;
import com.edu.seiryo.model.CountResultModel;
import com.edu.seiryo.query.PurchaseListQuery;
import com.edu.seiryo.query.SaleListQuery;
import com.edu.seiryo.service.ReportService;
import com.edu.seiryo.utils.PageResultUtil;
import com.edu.seiryo.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private PurchaseListMapper purchaseListMapper;
    
    @Resource
    private SaleListMapper saleListMapper;

    @Override
    public Map<String, Object> getPurchaseCountList(PurchaseListQuery query) {
        // 设置分页参数
        IPage<CountResultModel> page = new Page<>(query.getPage(), query.getLimit());
        // 调用 Mapper 自定义查询（需在 XML 中实现）
        page = purchaseListMapper.selectPurchaseCount(page, query);
        return PageResultUtil.setResult(page.getTotal(), page.getRecords());
    }

    @Override
    public Map<String, Object> getMonthSaleCountList(SaleListQuery query) {
        // 调用 Mapper 按月份分组统计
        List<Map<String, Object>> list = saleListMapper.selectMonthSaleCount(query);
        long total = list.size(); // 因为这里是按月份分组的结果，数量就是月份数
        return PageResultUtil.setResult(total, list);
    }
}