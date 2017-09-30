package com.gooseeker.fss.commons.generic.service.impl;

import java.util.List;
import java.util.Map;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.commons.generic.service.GenericService;

public abstract class GenericServiceImpl<Model, PK> implements
        GenericService<Model, PK>
{

    /**
     * 定义成抽象方法,由子类实现,完成dao的注入
     *
     * @return GenericMapper实现类
     */
    public abstract GenericMapper<Model, PK> getMapper();

    /**
     * 根据主键查询
     */
    public Model selectByPrimaryKey(PK id)
    {
        return getMapper().selectByPrimaryKey(id);
    }

    /**
     * 根据主键删除
     */
    public int deleteByPrimaryKey(PK id)
    {
        return getMapper().deleteByPrimaryKey(id);
    }
    
    /**
     * 添加或修改 插入对象的时候如果唯一件冲突则执行修改操作
     */
    public int saveOrUpdate(Model model)
    {
        return getMapper().saveOrUpdate(model);
    }
    
    /**
     * 更新对象
     * @param model
     * @return
     */
    public int update(Model model)
    {
        return getMapper().update(model);
    }
    
    /**
     * 根据对象的属性条件查询
     * 如果whereSql条件为null则查出的是全部结果
     * @param conditionMap[whereSql, orderBySql]
     * @return
     */
    public List<Model> selectByWhereSql(Map<String, String> conditionMap)
    {
        return getMapper().selectByWhereSql(conditionMap);
    }
}
