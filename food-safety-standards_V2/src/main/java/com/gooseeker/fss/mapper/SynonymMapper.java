package com.gooseeker.fss.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.entity.Synonym;

@Repository("SynonymMapper")
public interface SynonymMapper extends GenericMapper<Synonym, Long>{
	public List<Synonym> findAllSynonym(Map<String, Object> map);
	public int getTotalCount(); 
	
	public int insertSynonym(Synonym synonym);
	
	public void deleteSynonym(long id);
	
	public void updateSynonym(Map<String, Object> map);
}
