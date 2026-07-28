package com.edu.seiryo.model;

import lombok.Data;


/**
 * 销售金额model
 * @author TianTian
 * @date 2022/1/22 12:19
 */
@Data
public class SaleCount {
    private float amountCost; // 成本总金额

    private float amountSale; // 销售总金额

    private float amountProfit; // 销售利润

    private String date; // 日期

	public void setDate(String data) {
		// TODO Auto-generated method stub
		
	}

	public void setAmountCost(float format2Bit) {
		// TODO Auto-generated method stub
		
	}

	public void setAmountSale(float format2Bit) {
		// TODO Auto-generated method stub
		
	}

	public int getAmountSale() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setAmountProfit(float f) {
		// TODO Auto-generated method stub
		
	}

	public int getAmountCost() {
		// TODO Auto-generated method stub
		return 0;
	}
}
