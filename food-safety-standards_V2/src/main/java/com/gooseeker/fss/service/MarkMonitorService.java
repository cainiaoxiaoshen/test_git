package com.gooseeker.fss.service;

import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.container.QueryConditionContainer;
import com.gooseeker.fss.entity.MarkMonitor;

public interface MarkMonitorService
{
    /**
     * 分页查找文档的监测信息
     * @param container
     * @return
     */
    public PageInfo<MarkMonitor> findByPage(QueryConditionContainer container);

    /**
     * 找寻文档的监测信息
     * @param docId
     * @param user
     * @param roleLv
     * @return
     */
    public MarkMonitor findByDocId(long docId, String user, String roleLv);
}
