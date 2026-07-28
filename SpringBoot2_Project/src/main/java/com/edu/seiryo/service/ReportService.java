package com.edu.seiryo.service;

import com.edu.seiryo.query.PurchaseListQuery;
import com.edu.seiryo.query.SaleListQuery;
import java.util.Map;

public interface ReportService {
    Map<String, Object> getPurchaseCountList(PurchaseListQuery query);
    Map<String, Object> getMonthSaleCountList(SaleListQuery query);
}