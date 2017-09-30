package com.gooseeker.fss.service;


import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.commons.entity.Page;
import com.gooseeker.fss.commons.generic.service.GenericService;
import com.gooseeker.fss.entity.AnnotateWord;

public interface AnnotateWordService extends GenericService<AnnotateWord, Long>
{
    /**
     * 分页查询所有的关键词对象信息
     * @param p
     * @return
     */
    public PageInfo<AnnotateWord> findByPage(int p);
    
    public void deleteAnnotateWord(long id, String word) throws Exception;
    
    /**
     * 添加从预选词库中确认的关键词
     * @param words 要确认的词
     * @throws Exception
     */
    public void addAnnotateWords(String[] words) throws Exception;
    
    /**
     * 找到还没有被同步过的词
     * @return
     */
    public List<String> getNoSynchronizedWords();
    
    public int updateWordVersionIsNullTOVersion(String version);
    
    /**
     * 得到比参数版本更高的词
     * @param version
     * @return
     * @see [类、类#方法、类#成员]
     */
    public List<String> findHighVersionWords(String version);
    
    /**
     * 得到所有的关键词
     * @return
     * @see [类、类#方法、类#成员]
     */
    public List<String> getAllWords();
    
    /**
     * 得到所有的关键词对象信息
     * @return
     */
    public List<AnnotateWord> findAll();
}
