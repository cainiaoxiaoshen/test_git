package com.gooseeker.fss.service;

import java.util.List;

import com.gooseeker.fss.commons.generic.service.GenericService;
import com.gooseeker.fss.entity.UrlRole;

public interface UrlRoleService extends GenericService<UrlRole, Integer> {
	
	public List<UrlRole> findUrlRoleByUrlName(String url) throws Exception;
}
