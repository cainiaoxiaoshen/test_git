package com.gooseeker.fss.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.entity.Page;
import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.commons.generic.service.impl.GenericServiceImpl;
import com.gooseeker.fss.entity.Synonym;
import com.gooseeker.fss.mapper.SynonymMapper;
import com.gooseeker.fss.service.SynonymService;

@Service("SynonymServiceImpl")
public class SynonymServiceImpl extends GenericServiceImpl<Synonym, Long> implements SynonymService{
	
	@Resource(name="SynonymMapper")
	private SynonymMapper synonymMapper;

	public Page<Synonym> findAllSynonym(Integer currentPage) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		int total=synonymMapper.getTotalCount();
		Page<Synonym> page=new Page<Synonym>(total, currentPage, Constant.PAGE_LIMIT);
		map.put("startNum", page.getStartNum());
		map.put("limit", page.getLimit());
		List<Synonym> list = synonymMapper.findAllSynonym(map);
		page.setItems(list);
		return page;
	}

	@Override
	public GenericMapper<Synonym, Long> getMapper() {
		// TODO Auto-generated method stub
		return synonymMapper;
	}

	public int addSynonym(String mainWord, String synWord,String userName, String createTime) throws Exception {
		Synonym synonym=new Synonym();
		synonym.setMainWord(mainWord);
		synonym.setSynWord(synWord);
		synonym.setCreateUser(userName);
		try{
			synonym.setCreateTime(Timestamp.valueOf(createTime));
		}catch(Exception e){
			throw new Exception("创建时间字符串转换出错");
		}
		
		return synonymMapper.insertSynonym(synonym); 
	}

	public void delSynonym(long id) throws Exception {
		
		synonymMapper.deleteSynonym(id);
	}

	public void alterSynonym(String id, String mainWord, String synWord) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		
		map.put("id", id);
		map.put("mainWord", mainWord);
		map.put("synWord", synWord);
		
		synonymMapper.updateSynonym(map);
		
	}
}
