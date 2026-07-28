package com.edu.seiryo.controller;

import com.edu.seiryo.query.PurchaseListQuery;
import com.edu.seiryo.query.SaleListQuery;
import com.edu.seiryo.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 统计报表
 * @author TianTian
 * @date 2022/1/22 12:23
 */
@Controller
@RequestMapping("report")
public class ReportController {

    @Resource
    private ReportService reportService;

    @RequestMapping("countSupplier")
    public String countSupplierPage(){ return "count/supplier"; }

    @RequestMapping("countCustomer")
    public String countCustomerPage(){ return "count/customer"; }

    @RequestMapping("countPurchase")
    public String countPurchase(){ return "count/purchase"; }

    @RequestMapping("countSale")
    public String countSale(){ return "count/sale"; }

    @RequestMapping("countDaySale")
    public String countDaySale(){ return "count/day_sale"; }

    @RequestMapping("countMonthSale")
    public String countMonthSale(){ return "count/month_sale"; }



    /**
     * 商品采购统计列表数据（对应 PDF 第 10 页）
     */
    @RequestMapping("listPurchaseCount")
    @ResponseBody
    public Map<String, Object> listPurchaseCount(PurchaseListQuery query) {
        return reportService.getPurchaseCountList(query);
    }

    /**
     * 月销售统计数据（对应 PDF 第 11 页）
     */
    @RequestMapping("listMonthSaleCount")
    @ResponseBody
    public Map<String, Object> listMonthSaleCount(SaleListQuery query) {
        return reportService.getMonthSaleCountList(query);
    }
}