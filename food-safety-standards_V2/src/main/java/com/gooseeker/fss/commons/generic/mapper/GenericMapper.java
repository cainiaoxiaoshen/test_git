package com.gooseeker.fss.commons.generic.mapper;

import java.util.List;
import java.util.Map;

public interface GenericMapper<Model, PK>
{
    /**
     * 根据主键删除
     * 
     * @param id
     * @return
     */
    int deleteByPrimaryKey(PK id);
    
    /**
     * 根据主键查找
     * 
     * @param id
     * @return
     */
    Model selectByPrimaryKey(PK id);
    
    /**
     * 添加或修改 插入对象的时候如果唯一件冲突则执行修改操作
     */
    int saveOrUpdate(Model model);
    
    /**
     * 更新对象
     * @param model
     * @return
     */
    int update(Model model);
    
    /**
     * 根据对象的属性条件查询
     * 如果whereSql条件为null则查出的是全部结果
     * @param conditionMap[whereSql, orderBySql]
     * @return
     */
    List<Model> selectByWhereSql(Map<String, String> conditionMap);
}
