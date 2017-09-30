package com.gooseeker.fss.service;

import com.gooseeker.fss.commons.entity.Page;
import com.gooseeker.fss.entity.Synonym;

public interface SynonymService {
	public Page<Synonym> findAllSynonym(Integer currentPage) throws Exception;
	
	public int addSynonym(String mainWord, String synWord, String createUser, String createTime) throws Exception;

	public void delSynonym(long id) throws Exception;

	public void alterSynonym(String id, String mainWord, String synWord) throws Exception;
}
