package com.gooseeker.fss.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.entity.DocReplace;

@Repository("docReplaceMapper")
public interface DocReplaceMapper extends GenericMapper<DocReplace, Long>{
	public List<DocReplace> findAllDocReplace(Map<String, Object> map);
	public int getTotalCount();
	
	public int insertDocReplace(Map<String, Object> map);
	public List<DocReplace> selectDocReplaceByDocId(long id);
	
	public void updateDocReplace(Map<String, Object> map);
	
	public List<DocReplace> selectDocReplaceByDocNo(Map<String, Object> map);
	public List<DocReplace> findDocReplacesBydocNo(String docNo);
	
	public int getTotalCountByDocNo(Map<String, Object> map);
	
	public void addDocReplace(Map<String, Object> map);
	
	public void updateDocReplaceModel(Map<String, Object> map);
	
	public List<DocReplace> selectDocReplaceByOldDocNo(String oldDocNo);
	
	public void updateDocReplaceDelStatus(Map<String, Object> map);
}
