package com.gooseeker.fss.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.commons.entity.Page;
import com.gooseeker.fss.commons.generic.service.GenericService;
import com.gooseeker.fss.entity.DocCheck;

public interface DocCheckService extends GenericService<DocCheck, Integer>
{

    public PageInfo<DocCheck> findByPage(long docId, int p);

    public int addDocChecks(List<DocCheck> list);

}
