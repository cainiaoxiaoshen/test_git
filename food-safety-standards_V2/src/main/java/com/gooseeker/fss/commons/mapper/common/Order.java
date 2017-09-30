package com.gooseeker.fss.commons.mapper.common;
/**
 * 
 * Description:
 * 排序用
 * Order.java Create on 2013-4-18 下午8:44:01 
 * @author held
 * @version 1.0
 * Copyright (c) 2013 held,Inc. All Rights Reserved.
 */
class Order {
	public static String asc(String orderBy) {
		return new StringBuilder(" ").append(orderBy).append(" asc ").toString();
	}

	public static String desc(String orderBy) {
		return new StringBuilder(" ").append(orderBy).append(" desc ").toString();
	}
}