package com.gooseeker.fss.service;

import java.util.List;

import com.gooseeker.fss.commons.generic.service.GenericService;
import com.gooseeker.fss.entity.PageInclude;

public interface PageIncludeService extends GenericService<PageInclude, Long>
{

    /**
     * 添加页面包含的关键词信息
     * 
     * @param list
     * @return
     */
    public int addPageIncludes(List<PageInclude> list);

    /**
     * 得到文档的页面所包含的关键词信息
     * 
     * @param map
     * @return
     */
    public PageInclude findByDocNoAndPage(String docNo, int page);
}
