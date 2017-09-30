package com.gooseeker.fss.commons.mapper.common;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * Description: 添加条件封装类 WhereFieldBuilder.java Create on 2013-4-18 下午9:19:15
 * 
 * @author held
 * @version 1.0 Copyright (c) 2013 held,Inc. All Rights Reserved.
 */

public class WhereFieldBuilder
{
	private final String defaultValue = " where 1=1 ";
	private StringBuilder where = new StringBuilder(defaultValue);// 查找属性名称

	public String build()
	{
		return where.append(" ").toString();
	}

	public WhereFieldBuilder addWhere(String where, Object[] values)
	{
		if (StringUtils.isEmpty(where))
		{
			return this;
		}
		if (!ArrayUtils.isEmpty(values))
		{
			Object[] escapeSqlValues = new Object[values.length];
			int i = 0;
			for (Object each : values)
			{
				if (each instanceof String)
				{
					escapeSqlValues[i++] = StringEscapeUtils.escapeSql(each.toString());
				}
				else
				{
					escapeSqlValues[i++] = each;
				}
			}
			where = String.format(where, escapeSqlValues);
		}
		this.where.append(" and ( ").append(where).append(" )");
		return this;
	}
	
	public WhereFieldBuilder addConditionsAdd(String where, Object[] values)
	{
		if (StringUtils.isEmpty(where))
		{
			return this;
		}
		if (!ArrayUtils.isEmpty(values))
		{
			Object[] escapeSqlValues = new Object[values.length];
			int i = 0;
			for (Object each : values)
			{
				if (each instanceof String)
				{
					escapeSqlValues[i++] = StringEscapeUtils.escapeSql(each.toString());
				}
				else
				{
					escapeSqlValues[i++] = each;
				}
			}
			where = String.format(where, escapeSqlValues);
		}
		
		if (this.where.toString().contains(defaultValue))
		{
			this.where = new StringBuilder();
		}
		
		this.where.append(" and ( ").append(where).append(" )");
		return this;
	}

	public void reset()
	{
		where.setLength(0);
		where.append(defaultValue);
	}

}