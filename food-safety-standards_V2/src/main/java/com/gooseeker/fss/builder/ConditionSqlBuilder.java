package com.gooseeker.fss.builder;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.gooseeker.fss.commons.entity.Constant;

public class ConditionSqlBuilder
{
    private final String defaultWhere = " WHERE 1 = 1 ";
    
    private final String defaultOrderBy = " ORDER BY ";
    
    private static final String ORDER_ASC = " ASC ";
    
    private static final String ORDER_DESC = " DESC ";
    
    public StringBuilder whereBuilder = new StringBuilder(defaultWhere);
    
    public StringBuilder orderByBuilder = new StringBuilder();
    
    public Map<String, String> getCondition()
    {
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("whereSql", whereBuilder.toString());
        conditionMap.put("orderBySql", orderByBuilder.toString());
        return conditionMap;
    }

    /**
     * 添加条件（AND property = value）
     * @param property
     * @param value
     * @return
     */
    public ConditionSqlBuilder addAndEqCondition(String property, Object value)
    {
        appendWhereSql(property, value, "AND", "=");
        return this;
    }
    
    /**
     * 添加条件（AND property != value）
     * @param property
     * @param value
     * @return
     */
    public ConditionSqlBuilder addAndNeqCondition(String property, Object value)
    {
        appendWhereSql(property, value, "AND", "!=");
        return this;
    }
    
    /**
     * 添加条件（AND property < value）
     * @param property
     * @param value
     * @return
     */
    public ConditionSqlBuilder addAndLtCondition(String property, Object value)
    {
        appendWhereSql(property, value, "AND", "<");
        return this;
    }
    
    /**
     * 添加条件（AND property > value）
     * @param property
     * @param value
     * @return
     */
    public ConditionSqlBuilder addAndGtCondition(String property, Object value)
    {
        appendWhereSql(property, value, "AND", ">");
        return this;
    }
    
    /**
     * 添加条件（AND property <= value）
     * @param property
     * @param value
     * @return
     */
    public ConditionSqlBuilder addAndLeCondition(String property, Object value)
    {
        appendWhereSql(property, value, "AND", "<=");
        return this;
    }
    
    /**
     * 添加条件（AND property >= value）
     * @param property
     * @param value
     * @return
     */
    public ConditionSqlBuilder addAndGeCondition(String property, Object value)
    {
        appendWhereSql(property, value, "AND", ">=");
        return this;
    }
    
    /**
     * 添加条件（OR property = value）
     * @param property
     * @param value
     * @return
     */
    public ConditionSqlBuilder addOrEqCondition(String property, Object value)
    {
        appendWhereSql(property, value, "OR", "=");
        return this;
    }
    
    /**
     * 添加条件（OR property < value）
     * @param property
     * @param value
     * @return
     */
    public ConditionSqlBuilder addOrLtCondition(String property, Object value)
    {
        appendWhereSql(property, value, "OR", "<");
        return this;
    }
    
    /**
     * 添加条件（OR property > value）
     * @param property
     * @param value
     * @return
     */
    public ConditionSqlBuilder addOrGtCondition(String property, Object value)
    {
        appendWhereSql(property, value, "OR", ">");
        return this;
    }
    
    /**
     * 添加条件（OR property <= value）
     * @param property
     * @param value
     * @return
     */
    public ConditionSqlBuilder addOrLeCondition(String property, Object value)
    {
        appendWhereSql(property, value, "OR", "<=");
        return this;
    }
    
    /**
     * 添加条件（OR property >= value）
     * @param property
     * @param value
     * @return
     */
    public ConditionSqlBuilder addOrGeCondition(String property, Object value)
    {
        appendWhereSql(property, value, "OR", ">=");
        return this;
    }
    
    public ConditionSqlBuilder addOrderByASC(String property)
    {
        if(orderByBuilder.toString().contains(defaultOrderBy))
        {
            orderByBuilder.append(" , ")
                          .append(property)
                          .append(ORDER_ASC);
        }
        else
        {
            orderByBuilder.append(defaultOrderBy)
                          .append(property)
                          .append(ORDER_ASC);
        }
        return this;
    }
    
    public ConditionSqlBuilder addOrderByDESC(String property)
    {
        if(orderByBuilder.toString().contains(defaultOrderBy))
        {
            orderByBuilder.append(" , ")
                          .append(property)
                          .append(ORDER_DESC);
        }
        else
        {
            orderByBuilder.append(defaultOrderBy)
                          .append(property)
                          .append(ORDER_DESC);
        }
        return this;
    }
    
    public ConditionSqlBuilder addConditionSql(String sql)
    {
        //sql = StringEscapeUtils.escapeSql(sql);
        whereBuilder.append(sql);
        return this;
    }
    
    /**
     * 
     * @param property 属性名称
     * @param value 属性值
     * @param append (AND, OR..)
     * @param operate (=, in, <, >...)
     * @return
     */
    private ConditionSqlBuilder appendWhereSql(String property, Object value, String append, String operate)
    {
        if(!StringUtils.isEmpty(property) && value != null)
        {
            String escapeValue = StringEscapeUtils.escapeSql(value.toString());
            if(value instanceof String)
            {
                escapeValue = "'" + escapeValue + "'";
            }
            whereBuilder.append(" ")
                        .append(append)
                        .append(" ( ")
                        .append(property)
                        .append(" ")
                        .append(operate)
                        .append(" ")
                        .append(escapeValue)
                        .append(" ) ");
        }
        return this;
    }
    
    public ConditionSqlBuilder addDocAuthoritySql(String user, String roleLv)
    {
        if(Constant.ROLE_LV_STR_ANNOTATE.equals(roleLv))
        {
            addAndEqCondition("annotateUser", user);
        }
        if(Constant.ROLE_LV_STR_CHECK.equals(roleLv))
        {
            StringBuilder sql = new StringBuilder();
            sql.append(" AND (firstCheckUser = ")
               .append(user)
               .append(" OR secondCheckUser = ")
               .append(user)
               .append(")");
            addConditionSql(sql.toString());
        }
        if(Constant.ROLE_LV_STR_ANNOTATECHECK.equals(roleLv))
        {
            StringBuilder sql = new StringBuilder();
            sql.append(" AND (annotateUser = ")
               .append(user)
               .append(" OR firstCheckUser = ")
               .append(user)
               .append(" OR secondCheckUser = ")
               .append(user)
               .append(")");
            addConditionSql(sql.toString());
        }
        return this;
    }
}
