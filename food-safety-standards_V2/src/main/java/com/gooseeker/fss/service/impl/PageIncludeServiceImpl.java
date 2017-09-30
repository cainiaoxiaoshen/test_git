package com.gooseeker.fss.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gooseeker.fss.builder.ConditionSqlBuilder;
import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.commons.generic.service.impl.GenericServiceImpl;
import com.gooseeker.fss.entity.PageInclude;
import com.gooseeker.fss.mapper.PageIncludeMapper;
import com.gooseeker.fss.service.AnnotateWordService;
import com.gooseeker.fss.service.PageIncludeService;

@Service("pageIncludeServiceImpl")
public class PageIncludeServiceImpl extends
        GenericServiceImpl<PageInclude, Long> implements PageIncludeService
{

    private static Logger logger = Logger.getLogger(PageIncludeServiceImpl.class);

    @Resource(name = "pageIncludeMapper")
    private PageIncludeMapper pageIncludeMapper;
    
    @Resource(name = "annotateWordServiceImpl")
    private AnnotateWordService annotateWordService;

    @Override
    public GenericMapper<PageInclude, Long> getMapper()
    {
        return pageIncludeMapper;
    }

    /**
     * 添加页面包含的关键词信息
     * 
     * @param list
     * @return
     */
    @Override
    public int addPageIncludes(List<PageInclude> includes)
    {
        return pageIncludeMapper.saveOrUpdates(includes);
    }

    /**
     * 得到文档的页面所包含的关键词信息
     */
    @Override
    public PageInclude findByDocNoAndPage(String docNo, int page)
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        sqlBuilder.addAndEqCondition("docNo", docNo).addAndEqCondition("page", page);
        List<PageInclude> includes = selectByWhereSql(sqlBuilder.getCondition());
        if(includes.size() > 0)
        {
            return includes.get(0);
        }
        return null;
    }
}
