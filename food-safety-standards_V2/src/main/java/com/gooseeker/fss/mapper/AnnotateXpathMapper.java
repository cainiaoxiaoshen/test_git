package com.gooseeker.fss.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.entity.AnnotateXpath;

@Repository("annotateXpathMapper")
public interface AnnotateXpathMapper extends GenericMapper<AnnotateXpath, Long>
{
    /**
     * 
     * 查询打标结果的时候懒加载对应的xpath信息
     * 
     * @param map
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public List<AnnotateXpath> findAnnotateXpath(Map<String, Object> map)
            throws Exception;
    
    /**
     * 根据唯一键删除（docId，product，factor）
     * @param map
     * @return
     */
    public int deleteByUniqueKey(Map<String, Object> map);
}
