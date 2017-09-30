package com.gooseeker.fss.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.container.QueryConditionContainer;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.vo.MarkProgressVo;

@Repository("annotateDocumentMapper")
public interface AnnotateDocumentMapper extends GenericMapper<AnnotateDocument, Long>
{
    /**
     * 得到打标进程
     * @param progress
     * @return
     */
    
    public void getMarkProgress(MarkProgressVo progress);

//    /**
//     * 根据查询条件查询文档
//     * @param container
//     * @return
//     */
//    public List<AnnotateDocument> findByQueryCondition(QueryConditionContainer container);
    
//    /**
//     * 根据文当reqUrl查询doc对象
//     * 
//     * @param reqUrl
//     * @return
//     */
//    public AnnotateDocument selectByReqUrl(String reqUrl)
//        throws Exception;
    
//    /**
//     * 根据标准编号查找文档
//     * 
//     * @param standardNo
//     * @return
//     * @throws Exception
//     * @see [类、类#方法、类#成员]
//     */
//    public AnnotateDocument selectByStandardNo(String standardNo)
//        throws Exception;
//    
//    /**
//     * 查询没有被分配过任务的文档
//     * 
//     * @param map
//     * @return
//     */
//    public List<AnnotateDocument> findNoAssignmentDoc(Map<String, Object> map)
//        throws Exception;
//    
//    /**
//     * 查询没有被分配过任务的文档总数
//     * 
//     * @param map
//     * @return
//     */
//    public int findNoAssignmentDocTotal(Map<String, Object> map)
//        throws Exception;
    
    /**
     * 批量删除文档
     * 
     * @param ids
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public int deleteByPrimaryKeys(List<Long> ids);
    
//    /**
//     * 找出需要同步的文档， 状态不为入库的文档
//     * 
//     * @return
//     * @see [类、类#方法、类#成员]
//     */
//    public List<AnnotateDocument> findSynchronousDoc();
    
    /**
     * 更新文档版本
     * 
     * @param map
     * @return
     * @see [类、类#方法、类#成员]
     */
    public int updateDocVersion(Map<String, Object> map);
    // ------------------------------------------------
    
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
