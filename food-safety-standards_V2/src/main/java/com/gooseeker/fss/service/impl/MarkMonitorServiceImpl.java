package com.gooseeker.fss.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.builder.ConditionSqlBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.container.QueryConditionContainer;
import com.gooseeker.fss.entity.MarkMonitor;
import com.gooseeker.fss.mapper.MarkMonitorMapper;
import com.gooseeker.fss.service.MarkMonitorService;

@Service("markMonitorServiceImpl")
public class MarkMonitorServiceImpl implements MarkMonitorService
{

    private static Logger logger = Logger.getLogger(MarkMonitorServiceImpl.class);

    @Resource(name = "markMonitorMapper")
    private MarkMonitorMapper markMonitorMapper;

    /**
     * 得到文档的监测详情
     * @param docId
     * @param user
     * @param roleLv
     * @return
     */
    @Override
    public MarkMonitor findByDocId(long docId, String user, String roleLv)
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        sqlBuilder.addDocAuthoritySql(user, roleLv).addAndEqCondition("id", docId);
        List<MarkMonitor> monitors =  markMonitorMapper.selectByWhereSql(sqlBuilder.getCondition());
        if(monitors.size() > 0)
        {
            return monitors.get(0);
        }
        return null;
    }


    @Override
    public PageInfo<MarkMonitor> findByPage(QueryConditionContainer container)
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        sqlBuilder.addDocAuthoritySql(container.getUser(), container.getRoleLv());
        if(container.getTagState() != null)
        {
            sqlBuilder.addAndEqCondition("tagState", container.getTagState());
        }
        String keyWord = container.getKeyWord();
        if(keyWord != null)
        {
            keyWord = StringEscapeUtils.escapeSql(keyWord.trim());
            StringBuilder sql = new StringBuilder();
            sql.append(" AND (country = '")
               .append(keyWord)
               .append("' OR docName LIKE '%")
               .append(keyWord)
               .append("%' OR annotateUser = '")
               .append(keyWord)
               .append("' OR firstCheckUser = '")
               .append(keyWord)
               .append("' OR secondCheckUser = '")
               .append(keyWord)
               .append("')");
            sqlBuilder.addConditionSql(sql.toString());
        }
        PageHelper.startPage(container.getPageNum(), Constant.PAGE_LIMIT);
        List<MarkMonitor> monitors = markMonitorMapper.selectByWhereSql(sqlBuilder.getCondition());
        PageInfo<MarkMonitor> pageInfo = new PageInfo<MarkMonitor>(monitors);
        return pageInfo;
    }

}
