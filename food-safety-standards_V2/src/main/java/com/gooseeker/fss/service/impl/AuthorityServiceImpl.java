package com.gooseeker.fss.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.commons.generic.service.impl.GenericServiceImpl;
import com.gooseeker.fss.entity.Authority;
import com.gooseeker.fss.service.AuthorityService;
import com.gooseeker.fss.mapper.AuthorityMapper;

@Service("authorityServiceImpl")
public class AuthorityServiceImpl extends GenericServiceImpl<Authority, Long> implements AuthorityService {

	private static Logger logger = Logger.getLogger(AuthorityServiceImpl.class);
	
	@Resource(name = "authorityMapper")
	private AuthorityMapper authorityMapper;
	@Override
	public GenericMapper<Authority, Long> getMapper() {
		return authorityMapper;
	}
	@Override
	public int deleteAuthorityByUid(Long uid) {
		try {
			return authorityMapper.deleteAuthorityByUid(uid);
		} catch (Exception e) {
			logger.error("删除uid=[" + uid + "]的权限出错", e);
		}
		return 0;
	}

}
