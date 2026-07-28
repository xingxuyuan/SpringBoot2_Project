package com.edu.seiryo.query;

import lombok.Data;

import java.util.List;

/**
 * 乐字节  踏实教育 用心服务
 *
 * @author 乐字节--老李
 * @version 1.0
 */
@Data
public class SaleListQuery extends BaseQuery{

    private String saleNumber;
    private Integer customerId;
    private Integer state;



    private String startDate;
    private String endDate;
    private String goodsName;
    private Integer typeId;
    private List<Integer> typeIds;

    public Integer index;

	public long getPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getTypeId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setIndex(long l) {
		// TODO Auto-generated method stub
		
	}
}
