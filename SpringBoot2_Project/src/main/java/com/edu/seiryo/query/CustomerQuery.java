package com.edu.seiryo.query;

import lombok.Data;

@Data
public class CustomerQuery extends BaseQuery{
    private String customerName;

	public long getPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	public CharSequence getCustomerName() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getLimit() {
		// TODO Auto-generated method stub
		return 0;
	}
}
