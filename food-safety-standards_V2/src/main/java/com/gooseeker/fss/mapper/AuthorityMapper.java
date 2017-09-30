package com.gooseeker.fss.mapper;

import org.springframework.stereotype.Repository;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.entity.Authority;

@Repository("authorityMapper")
public interface AuthorityMapper extends GenericMapper<Authority, Long>
{
    /**
     * 根据用户id删除用户的权限
     * @param uid
     * @return
     */
    public int deleteAuthorityByUid(Long uid);
	
}
