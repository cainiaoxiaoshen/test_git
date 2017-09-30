package com.gooseeker.fss.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.entity.PreselectionWords;

@Repository("preselectionWordsMapper")
public interface PreselectionWordsMapper extends GenericMapper<PreselectionWords, Long>
{
    /**
     * 添加预选词
     * @param list
     * @return
     */
    public int insertPreselectionWords(List<PreselectionWords> list);
    
//    /**
//     * 找出没有被选择过的关键词
//     * @return
//     * @throws Exception
//     * @see [类、类#方法、类#成员]
//     */
//    public List<PreselectionWords> findPageNotSelectedWords(Map<String, Object> map) throws Exception;
    
    /**
     * 根据id更新词的被选状态
     * @param map（state， ids）
     * @return
     * @throws Exception
     */
    public int updateStateByIds(Map<String, Object> map);
    
    /**
     * 更新词的被选状态
     * @param map（state， words）
     * @return
     */
    public int updateStateByWords(Map<String, Object> map);
}
