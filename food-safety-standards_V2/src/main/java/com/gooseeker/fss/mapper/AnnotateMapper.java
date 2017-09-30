package com.gooseeker.fss.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.container.QueryConditionContainer;
import com.gooseeker.fss.entity.Annotate;
import com.gooseeker.fss.entity.vo.ExcelAnnotateVo;
import com.gooseeker.fss.entity.vo.FactorsDetailsVo;

@Repository("annotateMapper")
public interface AnnotateMapper extends GenericMapper<Annotate, Long>
{
    /**
     * 得到文档的打标信息--for导出
     * @param docId
     * @return ExcelAnnotateVo
     * @see [类、类#方法、类#成员]
     */
    public List<ExcelAnnotateVo> findByDocIdForExport(long docId);
    
    /**
     * 找出文档的打标信息和对应的xpath
     * 
     * @param docId
     * @return
     * @throws Exception
     */
    public List<Annotate> findAnnotateAndXpathByDocId(long docId);

    /**
     * 打标结果的详细信息
     * @param conditionMap
     * @return
     */
    public List<FactorsDetailsVo> selectAnnotateDetailsByWhereSql(Map<String, String> conditionMap);

    /**
     * 根据唯一键删除（docId，product，factor）
     * @param map
     * @return
     */
    public int deleteByUniqueKey(Map<String, Object> map);
    
    /**
     * 根据id删除打标信息
     * @param ids
     * @return
     */
    public int deleteByIds(long[] ids);
    
    /**
     * 调用存储过程更新打标结果的某个字段
     * @param map
     * @return
     * @throws Exception
     */
    public int updateAnnotateProperty(Map<String, Object> map);
    
}
