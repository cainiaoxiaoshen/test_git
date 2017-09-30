package com.gooseeker.fss.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.builder.ConditionSqlBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.commons.generic.service.impl.GenericServiceImpl;
import com.gooseeker.fss.entity.DocCheck;
import com.gooseeker.fss.mapper.DocCheckMapper;
import com.gooseeker.fss.service.DocCheckService;

@Service("docCheckServiceImpl")
public class DocCheckServiceImpl extends GenericServiceImpl<DocCheck, Integer>
        implements DocCheckService
{

    private static Logger logger = Logger.getLogger(DocCheckServiceImpl.class);

    @Resource(name = "docCheckMapper")
    private DocCheckMapper docCheckMapper;

    @Override
    public GenericMapper<DocCheck, Integer> getMapper()
    {
        return docCheckMapper;
    }

    /**
     * 查询文档的审核记录
     */
    @Override
    public PageInfo<DocCheck> findByPage(long docId, int p)
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        sqlBuilder.addAndEqCondition("docId", docId);
        PageHelper.startPage(p, Constant.PAGE_LIMIT);
        List<DocCheck> docChecks = selectByWhereSql(sqlBuilder.getCondition());
        PageInfo<DocCheck> pageInfo = new PageInfo<DocCheck>(docChecks);
        return pageInfo;
    }

    /**
     * 添加审核信息
     */
    @Override
    public int addDocChecks(List<DocCheck> list)
    {
        return docCheckMapper.saveOrUpdates(list);
    }
}
