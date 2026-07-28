package com.edu.seiryo.query;

import lombok.Data;

import java.util.List;

/**
 * 入库表单查询
 * @author TianTian
 * @date 2022/1/19 13:54
 */
@Data
public class PurchaseListQuery  extends BaseQuery{

    private String purchaseNumber;
    private Integer supplierId;
    private Integer state;


    private String startDate;
    private String endDate;
    private String goodsName;
    private Integer typeId;
    private List<Integer> typeIds;

    public Integer index;

	public Object getPage() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPurchaseNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getSupplierId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStartDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEndDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getLimit() {
		// TODO Auto-generated method stub
		return null;
	}
}
