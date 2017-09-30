package com.gooseeker.fss.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.builder.ConditionSqlBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.commons.generic.service.impl.GenericServiceImpl;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.container.QueryConditionContainer;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.vo.MarkProgressVo;
import com.gooseeker.fss.mapper.AnnotateDocumentMapper;
import com.gooseeker.fss.service.AnnotateDocumentService;
import com.gooseeker.fss.service.AnnotateWordService;

@Service("annotateDocumentServiceImpl")
public class AnnotateDocumentServiceImpl extends 
            GenericServiceImpl<AnnotateDocument, Long> implements AnnotateDocumentService
{

    //private static Logger logger = Logger.getLogger(AnnotateDocumentServiceImpl.class);

    @Resource(name = "annotateDocumentMapper")
    private AnnotateDocumentMapper annotateDocumentMapper;
    
    @Resource(name = "annotateWordServiceImpl")
    private AnnotateWordService annotateWordService;

    @Override
    public GenericMapper<AnnotateDocument, Long> getMapper()
    {
        return annotateDocumentMapper;
    }

    /**
     * 打标进度
     */
    public MarkProgressVo getMarkProgress()
    {
        MarkProgressVo progress = new MarkProgressVo();
        annotateDocumentMapper.getMarkProgress(progress);
        return progress;
    }
    
    /**
     * 分页查询文档
     */
    @Override
    public PageInfo<AnnotateDocument> findByPage(QueryConditionContainer container)
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        if(container.getTagState() != null)
        {
            sqlBuilder.addAndEqCondition("tagState", container.getTagState());
        }
        if(container.getType() != null)
        {
            sqlBuilder.addAndEqCondition("type", container.getType());
        }
        if(container.getCountry() != null)
        {
            sqlBuilder.addAndEqCondition("country", container.getCountry());
        }
        String user = container.getUser();
        String roleLv = container.getRoleLv();
        sqlBuilder.addDocAuthoritySql(user, roleLv)
                  .addOrderByASC("tagState").addOrderByDESC("createTime");
        PageHelper.startPage(container.getPageNum(), Constant.PAGE_LIMIT);
        List<AnnotateDocument> docs = selectByWhereSql(sqlBuilder.getCondition());
        //List<AnnotateDocument> docs = annotateDocumentMapper.findByQueryCondition(container);
        PageInfo<AnnotateDocument> pageInfo = new PageInfo<AnnotateDocument>(docs);
        return pageInfo;
    }
    
    /**
     * 根据文档reqUrl查找文档信息
     */
    @Override
    public AnnotateDocument selectByReqUrl(String reqUrl)
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        sqlBuilder.addAndEqCondition("reqUrl", reqUrl);
        List<AnnotateDocument> docs = selectByWhereSql(sqlBuilder.getCondition());
        if(docs.size() > 0)
        {
            return docs.get(0);
        }
        return null;
    }
    
    /**
     * 根据标准编号查找文档
     * @param standardNo
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public AnnotateDocument selectByStandardNo(String standardNo)
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        sqlBuilder.addAndEqCondition("standardNo", standardNo);
        List<AnnotateDocument> docs = selectByWhereSql(sqlBuilder.getCondition());
        if(docs.size() > 0)
        {
            return docs.get(0);
        }
        return null;
    }

    /**
     * 查询没有被分配过任务的文档
     */
    @Override
    public PageInfo<AnnotateDocument> findNoAssignmentDoc(String country, String input, int p)
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        sqlBuilder.addAndEqCondition("annotateUser", Constant.STR_EMPTY)
                  .addAndEqCondition("firstCheckUser", Constant.STR_EMPTY)
                  .addAndEqCondition("secondCheckUser", Constant.STR_EMPTY);
        if (StringUtil.isNotBlank(country)
                && !Constant.STR_ZERO.equals(country))
        {
            sqlBuilder.addAndEqCondition("country", country.trim());
        }
        if (StringUtil.isNotBlank(input))
        {
            input = StringEscapeUtils.escapeSql(input.trim());
            StringBuilder sql = new StringBuilder();
            sql.append(" AND (")
               .append("standardNo LIKE '%")
               .append(input)
               .append("%' OR docName LIKE '%")
               .append(input)
               .append("%')");
            sqlBuilder.addConditionSql(sql.toString());
        }
        PageHelper.startPage(p, Constant.PAGE_LIMIT);
        List<AnnotateDocument> docs = selectByWhereSql(sqlBuilder.getCondition());
        PageInfo<AnnotateDocument> pageInfo = new PageInfo<AnnotateDocument>(docs);
        return pageInfo;
    }
    
    /**
     * 
     * 删除文档
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public int deleteByPrimaryKeys(Long[] ids)
    {
        List<Long> list = new ArrayList<Long>();
        for (int i = 0; i < ids.length; i++)
        {
            list.add(ids[i]);
        }
        return annotateDocumentMapper.deleteByPrimaryKeys(list);
    }

    /**
     * 
     * 找到需要同步的文档
     * @return
     */
    @Override
    public List<AnnotateDocument> findSynchronousDoc()
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        sqlBuilder.addAndNeqCondition("tagState", Constant.TAG_STATE_FNISH);
        return selectByWhereSql(sqlBuilder.getCondition());
    }
    
    /**
     * 更新文档的关键词同步版本号
     */
    @Transactional
    @Override
    public void updateSynchronousDocVersion(List<AnnotateDocument> docs) throws Exception
    {
        String version = String.valueOf(System.currentTimeMillis());
        annotateWordService.updateWordVersionIsNullTOVersion(version);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("version", version);
        map.put("list", docs);
        annotateDocumentMapper.updateDocVersion(map);
    }
    
    /**
     * 批量插入文档信息
     * 插入的时候如果唯一件冲突则会执行update操作
     * @param list
     * @return
     */
    @Override
    public int saveOrUpdates(List<AnnotateDocument> list)
    {
        return annotateDocumentMapper.saveOrUpdates(list);
    }
    
    /**
     * 批量更新
     * list中对象必须满足的条件为id或者standardNo属性必须要有一个有值
     * 如果没有值则update不会被执行
     * @param list
     * @return
     */
    @Override
    public int updates(List<AnnotateDocument> list)
    {
        return annotateDocumentMapper.updates(list);
    }
}
