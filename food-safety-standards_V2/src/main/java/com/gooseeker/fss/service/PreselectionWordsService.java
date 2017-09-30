package com.gooseeker.fss.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.commons.entity.Page;
import com.gooseeker.fss.commons.generic.service.GenericService;
import com.gooseeker.fss.entity.Annotate;
import com.gooseeker.fss.entity.PreselectionWords;

public interface PreselectionWordsService extends GenericService<PreselectionWords, Long>
{
    /**
     * 添加页面包含的关键词信息
     * @param list
     * @return
     */
    public int insertPreselectionWords(List<PreselectionWords> list);
    
    /**
     * 添加关键词
     * @param annotate 对象中的 product factor unit funct
     * @return
     * @see [类、类#方法、类#成员]
     */
    public int insertFromAnnotate(Annotate annotate);
    
    /**
     * 找到没有被选择过的预选词
     * @param map
     * @return
     */
    public PageInfo<PreselectionWords> findNotSelectedByPage(String word, int p);
    
    /**
     * 根据id更新词的被选状态
     * @param state
     * @param ids
     * @return
     */
    public int updateStateByIds(int state, long[] ids);
    
    /**
     * 更新词的被选状态
     * @param state
     * @param word
     * @return
     */
    public int updateStateByWords(int state, String[] words);
}
