package com.gooseeker.fss.service;

import com.gooseeker.fss.commons.generic.service.GenericService;
import com.gooseeker.fss.entity.Authority;

public interface AuthorityService extends GenericService<Authority, Long> {
	
	public int deleteAuthorityByUid(Long uid);
}
