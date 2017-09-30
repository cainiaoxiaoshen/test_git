package com.gooseeker.fss.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.commons.generic.service.impl.GenericServiceImpl;
import com.gooseeker.fss.entity.UrlRole;
import com.gooseeker.fss.mapper.UrlRoleMapper;
import com.gooseeker.fss.service.UrlRoleService;

@Service("urlRoleServiceImpl")
public class UrlRoleServiceImpl extends GenericServiceImpl<UrlRole, Integer> implements UrlRoleService {

	@Resource(name = "urlRoleMapper")
	private UrlRoleMapper urlRoleMapper;
	@Override
	public List<UrlRole> findUrlRoleByUrlName(String url) throws Exception {
		return urlRoleMapper.findUrlRoleByUrlName(url);
	}

	@Override
	public GenericMapper<UrlRole, Integer> getMapper() {
		return urlRoleMapper;
	}

}
