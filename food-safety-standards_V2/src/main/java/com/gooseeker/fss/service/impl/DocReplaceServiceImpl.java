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
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.DocReplace;
import com.gooseeker.fss.mapper.DocReplaceMapper;
import com.gooseeker.fss.service.AnnotateDocumentService;
import com.gooseeker.fss.service.DocReplaceService;

@Service("docReplaceServiceImpl")
public class DocReplaceServiceImpl extends GenericServiceImpl<DocReplace, Long>
        implements DocReplaceService
{
    @Resource(name = "docReplaceMapper")
    private DocReplaceMapper docReplaceMapper;

    public Page<DocReplace> findDocReplace(Integer currentPage)
            throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();

        int total = docReplaceMapper.getTotalCount();

        Page<DocReplace> page = new Page<DocReplace>(total, currentPage,
                Constant.PAGE_LIMIT);
        map.put("startNum", page.getStartNum());
        map.put("limit", page.getLimit());

        List<DocReplace> list = docReplaceMapper.findAllDocReplace(map);
        page.setItems(list);

        return page;
    }

    @Override
    public GenericMapper<DocReplace, Long> getMapper()
    {
        // TODO Auto-generated method stub
        return docReplaceMapper;
    }

    public int insertDocReplace(String docId, String userName, String newDoc,
            String oldDoc, String relation, String remark) throws Exception
    {
        // 如果数据没有更新，则放弃插入
        long id = Long.parseLong(docId);
        List<DocReplace> list = docReplaceMapper.selectDocReplaceByDocId(id);
        if (list.get(0).getNewDoc() == newDoc
                && list.get(0).getOldDoc() == oldDoc
                && list.get(0).getRelation() == relation)
        {
            return 0;
        }

        // 如果数据有更新，则插入一条新记录
        Map<String, Object> map = new HashMap<String, Object>();
        Timestamp currentTime = new Timestamp(new Date().getTime());

        map.put("createUser", userName);
        map.put("createTime", currentTime);
        map.put("newDoc", newDoc);
        map.put("oldDoc", oldDoc);
        map.put("relation", relation);
        map.put("remark", remark);
        return docReplaceMapper.insertDocReplace(map);
    }

    public Page<DocReplace> findDocReplaceByDocNo(int currentPage,
            String docNo, String oldDocNo, int limit) throws Exception
    {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("docNo", docNo);
        if (StringUtil.isNotBlank(oldDocNo))
        {
            map.put("oldDocNo", oldDocNo);
        }
        int total = docReplaceMapper.getTotalCountByDocNo(map);
        Page<DocReplace> page = new Page<DocReplace>(total, currentPage, limit);
        map.put("startNum", page.getStartNum());
        map.put("limit", page.getLimit());

        List<DocReplace> list = docReplaceMapper.selectDocReplaceByDocNo(map);
        page.setItems(list);

        return page;
    }

    public void delDocReplace(String docId, String username)
    {
        long id = Long.parseLong(docId);
        Timestamp currentTime = new Timestamp(new Date().getTime());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("modiUser", username);
        map.put("modiTime", currentTime);

        docReplaceMapper.updateDocReplace(map);
    }

    @Override
    public void addDocReplace(DocReplace docplc)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("createUser", docplc.getCreateUserName());
        map.put("createTime", docplc.getCreateTime());
        map.put("newDoc", docplc.getNewDoc());
        map.put("oldDoc", docplc.getOldDoc());
        map.put("relation", docplc.getRelation());
        map.put("remark", docplc.getRemark());
        docReplaceMapper.addDocReplace(map);
    }

    @Override
    public void updateDocReplaceModel(DocReplace docplc)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", docplc.getId());
        map.put("createUser", docplc.getCreateUserName());
        map.put("createTime", docplc.getCreateTime());
        map.put("newDoc", docplc.getNewDoc());
        map.put("oldDoc", docplc.getOldDoc());
        map.put("relation", docplc.getRelation());
        map.put("remark", docplc.getRemark());
        docReplaceMapper.updateDocReplaceModel(map);
    }

    @Override
    public List<DocReplace> selectDocReplaceByOldDocNo(String oldDocNo)
    {
        return docReplaceMapper.selectDocReplaceByOldDocNo(oldDocNo);
    }

    @Override
    public void updateDocReplaceDelStatus(Long id, int isDel)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("isDel", isDel);
        docReplaceMapper.updateDocReplaceDelStatus(map);
    }

    @Override
    public List<DocReplace> findDocReplacesBydocNo(String docNo)
    {
        // TODO Auto-generated method stub
        return docReplaceMapper.findDocReplacesBydocNo(docNo);
    }
    
    @Override
    public void addDocReplaceByCompare(List<DocReplace> docReplaces) throws Exception
    {
        for (int i = 0; i < docReplaces.size(); i++)
        {
            DocReplace replace = docReplaces.get(i);
            replace.setCreateUserName(String.valueOf(2));
            replace.setCreateTime(new Timestamp(System.currentTimeMillis()));
            replace.setDel(false);
            if (StringUtil.isNotBlank(replace.getOldDoc())
                    && StringUtil.isNotBlank(replace.getNewDoc()))
            {
                if (replace.getId() > 0)
                {
                    DocReplace replace2 = selectByPrimaryKey(replace.getId());
                    if (replace2 != null && !replace2.docReplaceEquals(replace))
                    {
                        //有修改 修改原来的设置isDel = 1
                        updateDocReplaceDelStatus(replace2.getId(), 1);
                        addDocReplace(replace);
                    }
                }
                else
                {
                    // 新添加
                    addDocReplace(replace);
                }
            }
        }
    }

}
