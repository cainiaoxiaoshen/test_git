package com.gooseeker.fss.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.entity.UrlRole;

@Repository("urlRoleMapper")
public interface UrlRoleMapper extends GenericMapper<UrlRole, Integer> {
	
	public List<UrlRole> findUrlRoleByUrlName(String url) throws Exception;
}
