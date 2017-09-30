package com.gooseeker.fss.service;

import java.util.List;

import com.gooseeker.fss.commons.generic.service.GenericService;
import com.gooseeker.fss.entity.AnnotateXpath;

public interface AnnotateXpathService extends GenericService<AnnotateXpath, Long>
{

    /**
     * 插入
     * 
     * @param list
     * @return
     * @throws Exception
     */
    public int insertAnnotateXpaths(List<AnnotateXpath> list) throws Exception;

    /**
     * 通过文档编号，产品，和物质来删除xpath信息
     * 
     * @param docNo
     * @param product
     * @param factor
     * @return
     * @throws Exception
     */
    public int deleteAnntateXpath(long docId, String product, String factor)
            throws Exception;

}
