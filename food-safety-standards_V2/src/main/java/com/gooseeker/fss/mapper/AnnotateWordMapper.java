package com.gooseeker.fss.mapper;


import org.springframework.stereotype.Repository;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.entity.AnnotateWord;

@Repository("annotateWordMapper")
public interface AnnotateWordMapper extends GenericMapper<AnnotateWord, Long>
{
    /**
     * 插入词
     * @param words
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public int addAnnotateWords(String[] words);
    
    /**
     * 更新version is NULL 的版本
     * @param version
     * @return
     * @see [类、类#方法、类#成员]
     */
    public int updateWordVersionIsNullTOVersion(String version);
}
