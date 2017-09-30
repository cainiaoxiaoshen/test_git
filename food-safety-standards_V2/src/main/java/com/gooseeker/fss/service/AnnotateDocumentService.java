package com.gooseeker.fss.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.commons.entity.Page;
import com.gooseeker.fss.commons.generic.service.GenericService;
import com.gooseeker.fss.container.QueryConditionContainer;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.vo.MarkProgressVo;

public interface AnnotateDocumentService extends GenericService<AnnotateDocument, Long>
{

    /**
     * 得到打标的进度
     * 
     * @return
     */
    public MarkProgressVo getMarkProgress();

    /**
     * 根据查询条件分页查询文档信息
     * @param condition
     * @return
     */
    public PageInfo<AnnotateDocument> findByPage(QueryConditionContainer container);

    /**
     * 根据文档id查找文档
     * 
     * @param reqUrl
     * @return
     */
    public AnnotateDocument selectByReqUrl(String reqUrl);
    
    /**
     * 根据标准编号查找文档
     * @param standardNo
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public AnnotateDocument selectByStandardNo(String standardNo);

    /**
     * 查找没有被分配过任务的文档
     * 
     * @param country
     * @param docNo
     * @param p
     * @return
     */
    public PageInfo<AnnotateDocument> findNoAssignmentDoc(String country, String docNo, int p);
    
    /**
     * 批量删除文档
     * @param ids
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public int deleteByPrimaryKeys(Long[] ids);
    
    /**
     * 找到需要同步的文档
     * @return
     * @see [类、类#方法、类#成员]
     */
    public List<AnnotateDocument> findSynchronousDoc();
    
    /**
     * 更新文档的关键词同步版本号
     * @param docs
     * @throws Exception
     */
    public void updateSynchronousDocVersion(List<AnnotateDocument> docs) throws Exception;
    
    /**
     * 批量插入文档信息
     * 插入的时候如果唯一件冲突则会执行update操作
     * @param list
     * @return
     */
    public int saveOrUpdates(List<AnnotateDocument> list);
    
    /**
     * 批量更新
     * list中对象必须满足的条件为id或者standardNo属性必须要有一个有值
     * 如果没有值则update不会被执行
     * @param list
     * @return
     */
    public int updates(List<AnnotateDocument> list);
    
}
