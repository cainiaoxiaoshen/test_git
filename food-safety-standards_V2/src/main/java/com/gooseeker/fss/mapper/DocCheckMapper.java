package com.gooseeker.fss.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.entity.DocCheck;

@Repository("docCheckMapper")
public interface DocCheckMapper extends GenericMapper<DocCheck, Integer>
{
    /**
     * 批量添加审核信息
     * @param list
     * @return
     */
    public int saveOrUpdates(List<DocCheck> list);
}
