package com.gooseeker.fss.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gooseeker.fss.entity.MarkMonitor;

@Repository("markMonitorMapper")
public interface MarkMonitorMapper
{

    /**
     * 根据对象的属性条件查询
     * 如果whereSql条件为null则查出的是全部结果
     * @param conditionMap[whereSql, orderBySql]
     * @return
     */
    List<MarkMonitor> selectByWhereSql(Map<String, String> conditionMap);
}
