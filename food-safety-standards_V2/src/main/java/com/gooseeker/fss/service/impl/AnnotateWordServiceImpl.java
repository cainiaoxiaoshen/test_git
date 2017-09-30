package com.gooseeker.fss.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.builder.ConditionSqlBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.commons.generic.service.impl.GenericServiceImpl;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.entity.AnnotateWord;
import com.gooseeker.fss.mapper.AnnotateWordMapper;
import com.gooseeker.fss.service.AnnotateWordService;
import com.gooseeker.fss.service.PreselectionWordsService;

@Service("annotateWordServiceImpl")
public class AnnotateWordServiceImpl extends GenericServiceImpl<AnnotateWord, Long> implements AnnotateWordService {

    @Resource(name = "annotateWordMapper")
    private AnnotateWordMapper annotateWordMapper;

    @Resource(name = "preselectionWordsServiceImpl")
    private PreselectionWordsService wordsService;

    @Override
    public GenericMapper<AnnotateWord, Long> getMapper()
    {
        return annotateWordMapper;
    }

    /**
     * 分页得到打标关键词
     */
    @Override
    public PageInfo<AnnotateWord> findByPage(int p)
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        sqlBuilder.addOrderByASC("version");
        PageHelper.startPage(p, Constant.PAGE_LIMIT);
        List<AnnotateWord> annotateWords = selectByWhereSql(sqlBuilder.getCondition());
        PageInfo<AnnotateWord> pageInfo = new PageInfo<AnnotateWord>(annotateWords);
        return pageInfo;
    }
    
    /**
     * 添加确认的关键词
     */
    @Transactional
    @Override
    public void addAnnotateWords(String[] words) throws Exception
    {
            //确认的词要在预选词库中把他的状态更新为被确认过的状态
        wordsService.updateStateByWords(1, words);
        annotateWordMapper.addAnnotateWords(words);
    }

    @Transactional
    @Override
    public void deleteAnnotateWord(long id, String word) throws Exception
    {
        deleteByPrimaryKey(id);
        wordsService.updateStateByWords(0, new String[] {word});
    }

    /**
     * 得到还没有被同步过的词
     */
    @Override
    public List<String> getNoSynchronizedWords()
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        sqlBuilder.addConditionSql(" WHERE `version` IS NULL");
        List<AnnotateWord> words = selectByWhereSql(sqlBuilder.getCondition());
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < words.size(); i++)
        {
            list.add(words.get(0).getAnnotateWord());
        }
        return list;
    }

    @Override
    public int updateWordVersionIsNullTOVersion(String version)
    {
        return annotateWordMapper.updateWordVersionIsNullTOVersion(version);
    }

    /**
     * 得到高版本的打标关键词
     */
    @Override
    public List<String> findHighVersionWords(String version)
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        if(StringUtil.isNotBlank(version))
        {
            sqlBuilder.addAndGtCondition("CONVERT(`version`, SIGNED)", "CONVERT('" + version + "', SIGNED)")
                      .addConditionSql(" OR `version` IS NULL");
        }
        List<AnnotateWord> words = selectByWhereSql(sqlBuilder.getCondition());
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < words.size(); i++)
        {
            list.add(words.get(0).getAnnotateWord());
        }
        return list;
    }

    /**
     * 得到所有的关键词
     */
    @Override
    public List<String> getAllWords()
    {
        List<String> list = new ArrayList<String>();
        List<AnnotateWord> words =  findAll();
        for (int i = 0; i < words.size(); i++)
        {
            list.add(words.get(0).getAnnotateWord());
        }
        return list;
    }

    /**
     * 得到所有的关键词信息
     */
    @Override
    public List<AnnotateWord> findAll()
    {
        return selectByWhereSql(null);
    }
}
