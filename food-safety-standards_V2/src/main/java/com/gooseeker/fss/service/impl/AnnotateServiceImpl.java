package com.gooseeker.fss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.builder.CheckLogBuilder;
import com.gooseeker.fss.builder.ConditionSqlBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.commons.generic.service.impl.GenericServiceImpl;
import com.gooseeker.fss.container.PluginMarkContainer;
import com.gooseeker.fss.container.QueryConditionContainer;
import com.gooseeker.fss.entity.Annotate;
import com.gooseeker.fss.entity.AnnotateXpath;
import com.gooseeker.fss.entity.DocCheck;
import com.gooseeker.fss.entity.vo.ExcelAnnotateVo;
import com.gooseeker.fss.entity.vo.FactorsDetailsVo;
import com.gooseeker.fss.mapper.AnnotateMapper;
import com.gooseeker.fss.service.AnnotateDocumentService;
import com.gooseeker.fss.service.AnnotateService;
import com.gooseeker.fss.service.AnnotateXpathService;
import com.gooseeker.fss.service.DocCheckService;
import com.gooseeker.fss.service.PreselectionWordsService;
import com.gooseeker.fss.service.StandardsUserService;

@Service("annotateServiceImpl")
public class AnnotateServiceImpl extends GenericServiceImpl<Annotate, Long>
        implements AnnotateService
{

    private static Logger logger = Logger.getLogger(AnnotateServiceImpl.class);

    @Resource(name = "annotateMapper")
    private AnnotateMapper annotateMapper;

    @Resource(name = "annotateXpathServiceImpl")
    private AnnotateXpathService annotateXpathService;

    @Resource(name = "standardsUserServiceImpl")
    private StandardsUserService standardsUserService;
    
    @Resource(name = "docCheckServiceImpl")
    private DocCheckService docCheckService;
    
    @Resource(name = "annotateDocumentServiceImpl")
    private AnnotateDocumentService annotateDocumentService;
    
    @Resource(name = "preselectionWordsServiceImpl")
    private PreselectionWordsService preselectionWordsService;

    @Override
    public GenericMapper<Annotate, Long> getMapper()
    {

        return annotateMapper;
    }

    /**
     * 添加打标信息，和对应的xpath等相关信息
     */
    private int addAnnotate(Annotate annotate, List<AnnotateXpath> xpathList) throws Exception
    {
        saveOrUpdate(annotate);
        annotateXpathService.deleteAnntateXpath(annotate.getDocId(), annotate.getProduct(), annotate.getFactor());
        annotateXpathService.insertAnnotateXpaths(xpathList);
        preselectionWordsService.insertFromAnnotate(annotate);
        int antId = Integer.valueOf(annotate.getId().toString());//annotate是修改操作antId为0（主键冲突）
        return antId;
    }
    
    /**
     * 添加打标信息，和对应的xpath等相关信息
     */
    @Override
    @Transactional
    public int addAnnotate(Annotate annotate, List<AnnotateXpath> xpathList, PluginMarkContainer container) throws Exception
    {
        Annotate dbAnt = findByDocIdAndProAndFactor(annotate.getDocId(), annotate.getProduct(), annotate.getFactor());
        //添加打标结果信息
        int antId = addAnnotate(annotate, xpathList);
        if(container != null)
        {
          //是否需要添加审核记录
            boolean addCheck = CheckLogBuilder.isAddCheckLog(dbAnt.getUser(), container.getUser());
            if(dbAnt != null)//表示数据库中存在这条记录，为空则表示是审核员新添加的要记录审核信息
            {
                antId = Integer.valueOf(dbAnt.getId().toString());
            }
            if(addCheck)
            {
                //记录审核信息
                String user = container.getUser();
                int grade = container.getOperate();
                long docId = container.getDoc().getId();
                String docNo = container.getDoc().getStandardNo();
                List<DocCheck> docChecks = CheckLogBuilder.buildModifyCheckLogs(dbAnt, annotate, antId, user, grade, docId, docNo);
                if(docChecks.size() > 0)
                {
                    logger.info("记录审核信息...");
                    docCheckService.addDocChecks(docChecks);
                }
            }
            //把annotate 对象中的 product factor unit funct添加到预选词库中
            preselectionWordsService.insertFromAnnotate(annotate);
            return 1;
        }
        return antId;
    }
    
    /**
     * 查找文档的打标信息对应的xpath
     */
    @Override
    public List<Annotate> findAnnotateAndXpathByDocId(long docId)
    {
        return annotateMapper.findAnnotateAndXpathByDocId(docId);
    }

    /**
     * 分页查找文档打标信息
     */
    @Override
    public PageInfo<Annotate> findByPage(long docId, int p, String date)
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        sqlBuilder.addAndEqCondition("docId", docId)
                  .addAndEqCondition("DATE_FORMAT(createDate,'%Y-%m-%d')", date)
                  .addOrderByDESC("createDate");
        PageHelper.startPage(p, Constant.PAGE_LIMIT);
        List<Annotate> annotates = selectByWhereSql(sqlBuilder.getCondition());
        PageInfo<Annotate> pageInfo = new PageInfo<Annotate>(annotates);
        return pageInfo;
    }

    /**
     * 删除打标信息
     */
    @Override
    public void deleteByIds(long[] ids)
    {
        annotateMapper.deleteByIds(ids);
    }

    /**
     * 
     * 指标检索
     * @param searchParam
     * @return
     */
    @Override
    public PageInfo<FactorsDetailsVo> findFactorByPage(QueryConditionContainer container)
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        
        sqlBuilder.addDocAuthoritySql(container.getUser(), container.getRoleLv());
        
        if(container.getType() != null)
        {
            sqlBuilder.addAndEqCondition("type", container.getType());
        }
        if(container.getCountry() != null)
        {
            sqlBuilder.addAndEqCondition("country", container.getCountry());
        }
        String keyWord = container.getKeyWord();
        if(keyWord != null)
        {
            StringBuilder sql = new StringBuilder();
            sql.append(" AND (annotate.product LIKE '%")
               .append(keyWord)
               .append("%' OR annotate.factor LIKE '%")
               .append(keyWord)
               .append("%')");
            sqlBuilder.addConditionSql(sql.toString());
        }
        sqlBuilder.addOrderByDESC("annotate.createDate");
        PageHelper.startPage(container.getPageNum(), Constant.PAGE_LIMIT);
        List<FactorsDetailsVo> factorsDetailsVos = annotateMapper.selectAnnotateDetailsByWhereSql(sqlBuilder.getCondition());
        PageInfo<FactorsDetailsVo> pageInfo = new PageInfo<FactorsDetailsVo>(factorsDetailsVos);
        return pageInfo;
    }
    
    /**
     * 
     * @param annotate 打标结果对象
     * @param standardNo 文档的标准编号
     * @param tagState 文档的打标状态
     * @param user 当前用户
     * @param roleLv 用户的权限等级
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public int deleteAnntate(Annotate annotate, String standardNo, int tagState,
            String user, String roleLv)throws Exception
    {
        String antUser = annotate.getUser();
        String product = annotate.getProduct();
        String factor = annotate.getFactor();
        Map<String, Integer> progress = CheckLogBuilder.getCheckProgress(tagState, user, antUser, roleLv);
        if(progress.get("flag") == 1)
        {
            int grade = progress.get("grade");
            DocCheck check = CheckLogBuilder.buildDeleteCheckLog(annotate, user, standardNo, grade);
            docCheckService.saveOrUpdate(check);
        }
        deleteAnntate(annotate.getDocId(), product, factor);
        return 1;
    }
    

    private int deleteAnntate(long docId, String product, String factor) throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("docId", docId);
        map.put("product", product);
        map.put("factor", factor);
        annotateXpathService.deleteAnntateXpath(docId, product, factor);
        annotateMapper.deleteByUniqueKey(map);
        return 1;
    }

    /**
     * 根据产品和指标找到打标结果
     * @param docId
     * @param product
     * @param factor
     * @return
     */
    @Override
    public Annotate findByDocIdAndProAndFactor(long docId, String product, String factor)
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        sqlBuilder.addAndEqCondition("docId", docId)
                  .addAndEqCondition("product", product)
                  .addAndEqCondition("factor", factor);
        
        List<Annotate> annotates = selectByWhereSql(sqlBuilder.getCondition());
        if(annotates.size() > 0)
        {
            return annotates.get(0);
        }
        return null;
    }

    /**
     * 得到文档的打标信息--for导出
     * @param docId
     * @return ExcelAnnotateVo
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<ExcelAnnotateVo> findByDocIdForExport(long docId)
    {
        return annotateMapper.findByDocIdForExport(docId);
    }

    /**
     * 
     * @param annotate 被修改的打标结果对象
     * @param type 被修改的属性类型1、2、3....
     * @param text 被修改后的值
     * @param tagState 文档的打标状态
     * @param user 当前的用户
     * @param roleLv 用户的权限等级
     * @return
     */
    public int updateAnnotateProperty(Annotate annotate, int type, String text, 
            int tagState, String user, String roleLv)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("antId", annotate.getId());
        map.put("type", type);
        map.put("text", text);
        map.put("docId", annotate.getDocId());
        map.put("user", user);
        String antUser = annotate.getUser();
        Map<String, Integer> progress = CheckLogBuilder.getCheckProgress(tagState, user, antUser, roleLv);
        logger.info("是否需要添加审核记录：" + progress.get("flag"));
        map.put("grade", progress.get("grade"));
        map.put("flag", progress.get("flag"));
        return annotateMapper.updateAnnotateProperty(map);
    }
}
