package com.gooseeker.fss.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.entity.PageInclude;

@Repository("pageIncludeMapper")
public interface PageIncludeMapper extends GenericMapper<PageInclude, Long>
{

    /**
     * 添加页面包含的关键词信息
     * 
     * @param list
     * @return
     */
    public int saveOrUpdates(List<PageInclude> list);
}
