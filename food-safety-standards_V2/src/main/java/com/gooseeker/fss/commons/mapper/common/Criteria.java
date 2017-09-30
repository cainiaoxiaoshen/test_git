package com.gooseeker.fss.commons.mapper.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询条件的组装 Description: Criteria.java Create on 2013-4-18 下午8:43:13
 * 
 * @author held
 * @version 1.0 Copyright (c) 2013 held,Inc. All Rights Reserved.
 */
public class Criteria
{
	
	public void setSelectBody(String selectBody)
	{
		this.selectBody = selectBody;
	}

	public void setForUpdate()
	{
		this.forUpdate = " for update ";
	}

	public void addWhere(String where, Object[] values)
	{
		whereBuilder.addWhere(where, values);
	}
	
	public void addConditionsAdd(String where, Object[] values)
	{
		whereBuilder.addConditionsAdd(where, values);
	}

	public void addAscOrder(String filed)
	{
		orderBuilder.addAscOrder(filed);
	}

	public void addDescOrder(String filed)
	{
		orderBuilder.addDescOrder(filed);
	}

	public void setSumField(String sumField)
	{
		addParameter(SUMFIELD, sumField);
	}

	public void addParameter(String key, String value)
	{
		paraMap.put(key, value);
	}

	
	public Map<String, String> getAnswer()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(WHERE, whereBuilder.build());
		map.put(ORDER, orderBuilder.build());
		map.put(SELECTBODY, selectBody);
		map.put(FORUPDATE, forUpdate);
		map.put(SQL,sql);
		map.put(TOTALCOUNTSQL,totalCountsql);
		map.putAll(paraMap);
		
		return map;
	}
	

	public void reset()
	{
		selectBody = "";
		paraMap.clear();
		whereBuilder.reset();
		orderBuilder.reset();
		sql="";
		totalCountsql="";
	}

	/* public function */
	public static WhereFieldBuilder newWhereBuilder()
	{
		return new WhereFieldBuilder();
	}

	public static OrderFieldBuilder newOrderBuilder()
	{
		return new OrderFieldBuilder();
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	
	
	/* END - public function */

	/* private function */
	/* END - private function */




	public String getTotalCountsql() {
		return totalCountsql;
	}

	public void setTotalCountsql(String totalCountsql) {
		this.totalCountsql = totalCountsql;
	}



	/* private property */
	private String selectBody = "";
	private String forUpdate = "";
	private Map<String, String> paraMap = new HashMap<String, String>(10);
	private WhereFieldBuilder whereBuilder = newWhereBuilder();
	private OrderFieldBuilder orderBuilder = newOrderBuilder();
	private String sql;
	private String totalCountsql;

	/* END - private property */

	/* public final property */
	public static final String WHERE = "where";
	public static final String ORDER = "order";
	public static final String SUMFIELD = "sumField";
	private static final String FORUPDATE = "for_update";
	private static final String SELECTBODY = "selectBody";
	private static final String SQL="sql";
	private static final String TOTALCOUNTSQL="totalCountsql";
	/* END - public final property */
	
	public static void main(String[] args)
    {
	    Criteria criteria = new Criteria();
	    criteria.addWhere("userName" + " = '%1$s'", new Object[] {1});
    }
}
