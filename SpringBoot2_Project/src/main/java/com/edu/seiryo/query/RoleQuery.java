package com.edu.seiryo.query;

import lombok.Data;

/**
 * 封装了前端传回参数的一种格式
 * @author TianTian
 * @date 2022/1/12 22:06
 */
@Data
public class RoleQuery extends BaseQuery{
    private String roleName;

	public long getPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getRoleName() {
		// TODO Auto-generated method stub
		return null;
	}
}
