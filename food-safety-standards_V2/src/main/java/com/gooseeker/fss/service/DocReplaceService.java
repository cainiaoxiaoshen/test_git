package com.gooseeker.fss.service;

import java.util.List;

import com.gooseeker.fss.commons.entity.Page;
import com.gooseeker.fss.commons.generic.service.GenericService;
import com.gooseeker.fss.entity.DocReplace;

public interface DocReplaceService extends GenericService<DocReplace, Long>{
//	分页显示所有文档代替关系
	public Page<DocReplace> findDocReplace(Integer currentPage) throws Exception;
//	增加文档代替关系
	public int insertDocReplace(String docId, String userName,String newDoc,String oldDoc,String relation,String remark) throws Exception;
//	根据文档编号查找相应文档代替关系
	public Page<DocReplace> findDocReplaceByDocNo(int currentPage, String docNo, String oldDocNo, int limit) throws Exception;
//	删除文档代替关系
	public void delDocReplace(String docId, String username);
	
	public void addDocReplace(DocReplace docplc);
	
	public void updateDocReplaceModel(DocReplace docplc);
	
	public List<DocReplace> selectDocReplaceByOldDocNo(String oldDocNo);
	
	public void updateDocReplaceDelStatus(Long id, int isDel);
	
	public List<DocReplace> findDocReplacesBydocNo(String docNo);
	
	public void addDocReplaceByCompare(List<DocReplace> docReplaces) throws Exception;
	
}
