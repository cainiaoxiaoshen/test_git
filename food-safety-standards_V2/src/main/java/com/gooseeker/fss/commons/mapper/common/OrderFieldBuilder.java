package com.gooseeker.fss.commons.mapper.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Description: OrderFieldBuilder.java Create on 2013-4-18 下午9:17:46
 * 
 * @author held
 * @version 1.0 Copyright (c) 2013 held,Inc. All Rights Reserved.
 */
public class OrderFieldBuilder
{
	private List<String> orderByIterms = new ArrayList<String>(5);

	public String build()
	{
		if (orderByIterms.size() > 0)
		{
			StringBuilder sb = new StringBuilder("order by");
			for (String each : orderByIterms)
			{
				sb.append(" ").append(each).append(",");
			}
			return sb.substring(0, sb.length() - 1).toString();
		}
		return "";
	}

	public OrderFieldBuilder addAscOrder(String order)
	{
		orderByIterms.add(Order.asc(order));
		return this;
	}

	public OrderFieldBuilder addDescOrder(String order)
	{
		orderByIterms.add(Order.desc(order));
		return this;
	}

	public void reset()
	{
		orderByIterms.clear();
	}
}