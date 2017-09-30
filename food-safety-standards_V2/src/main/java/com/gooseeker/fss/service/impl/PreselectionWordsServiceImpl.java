package com.gooseeker.fss.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.builder.ConditionSqlBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.commons.generic.service.impl.GenericServiceImpl;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.entity.Annotate;
import com.gooseeker.fss.entity.PreselectionWords;
import com.gooseeker.fss.mapper.PreselectionWordsMapper;
import com.gooseeker.fss.service.PreselectionWordsService;

@Service("preselectionWordsServiceImpl")
public class PreselectionWordsServiceImpl extends GenericServiceImpl<PreselectionWords, Long> 
              implements PreselectionWordsService
{
    @Resource(name = "preselectionWordsMapper")
    private PreselectionWordsMapper wordsMapper;

    @Override
    public GenericMapper<PreselectionWords, Long> getMapper()
    {
        return wordsMapper;
    }

    /**
     * 添加预选词
     */
    @Override
    public int insertPreselectionWords(List<PreselectionWords> list)
    {
        return wordsMapper.insertPreselectionWords(list);
    }
    
    /**
     * 
     * annotate 对象中的 product factor unit funct
     * @param annotate
     * @return
     */
    public int insertFromAnnotate(Annotate annotate)
    {
        List<PreselectionWords> list = new ArrayList<PreselectionWords>();
        String product = annotate.getProduct();
        if(!StringUtil.isNullOrTrimEmpty(product))
        {
            list.add(new PreselectionWords(product.trim(), 0, 1));
        }
        String factor = annotate.getFactor();
        if(!StringUtil.isNullOrTrimEmpty(factor))
        {
            list.add(new PreselectionWords(factor.trim(), 0, 1));
        }
        
        String unit = annotate.getUnit();
        if(!StringUtil.isNullOrTrimEmpty(unit))
        {
            list.add(new PreselectionWords(unit.trim(), 0, 1));
        }
        
        String funct = annotate.getFunct();
        if(!StringUtil.isNullOrTrimEmpty(funct))
        {
            list.add(new PreselectionWords(funct.trim(), 0, 1));
        }
        return insertPreselectionWords(list);
    }

    /**
     * 找到没有被选择过的预选词
     */
    @Override
    public PageInfo<PreselectionWords> findNotSelectedByPage(String word, int p)
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        sqlBuilder.addAndEqCondition("state", 0);
        if(!StringUtil.isNullOrTrimEmpty(word))
        {
            word = StringEscapeUtils.escapeSql(word.trim());
            StringBuilder sql = new StringBuilder();
            sql.append(" AND (")
               .append("standardNo LIKE '%")
               .append(word)
               .append("%')");
            sqlBuilder.addConditionSql(sql.toString());
        }
        sqlBuilder.addOrderByDESC("frequency");
        PageHelper.startPage(p, Constant.PAGE_LIMIT);
        List<PreselectionWords> preselectionWords = selectByWhereSql(sqlBuilder.getCondition());
        PageInfo<PreselectionWords> pageInfo = new PageInfo<PreselectionWords>(preselectionWords);
        return pageInfo;
    }

    /**
     * 根据id更新词的被选状态
     * @param state
     * @param ids
     * @return
     */
    @Override
    public int updateStateByIds(int state, long[] ids)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", state);
        map.put("ids", ids);
        return wordsMapper.updateStateByIds(map);
    }
    
    /**
     * 更新词的被选状态
     * @param state
     * @param word
     * @return
     */
    @Override
    public int updateStateByWords(int state, String[] words)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", state);
        map.put("words", words);
        return wordsMapper.updateStateByWords(map);
    }

}
