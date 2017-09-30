package com.gooseeker.fss.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.commons.entity.Page;
import com.gooseeker.fss.commons.generic.service.GenericService;
import com.gooseeker.fss.container.PluginMarkContainer;
import com.gooseeker.fss.container.QueryConditionContainer;
import com.gooseeker.fss.entity.Annotate;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.AnnotateXpath;
import com.gooseeker.fss.entity.vo.ExcelAnnotateVo;
import com.gooseeker.fss.entity.vo.FactorsDetailsVo;

public interface AnnotateService extends GenericService<Annotate, Long>
{
    
//    /**
//     * 插入打标信息
//     * 
//     * @param list
//     * @param xpathList
//     * @return
//     */
//    int insertAnnotates(Annotate annotate, List<AnnotateXpath> xpathList)
//            throws Exception;

    /**
     * 添加打标信息，和对应的xpath等相关信息
     * 
     * @param list
     * @param xpathList
     * @return
     */
    public int addAnnotate(Annotate annotate, List<AnnotateXpath> xpathList, PluginMarkContainer container) throws Exception;

    /**
     * 查找文档的打标信息对应的xpath
     * 
     * @param docId
     * @return
     */
    public List<Annotate> findAnnotateAndXpathByDocId(long docId);
    
    /**
     * 根据文档编号，产品，物质查找
     * 
     * @param docId
     * @param product
     * @param factor
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public Annotate findByDocIdAndProAndFactor(long docId, String product, String factor);
    
    /**
     * 得到文档的打标信息--for导出
     * @param docId
     * @return ExcelAnnotateVo
     * @see [类、类#方法、类#成员]
     */
    public List<ExcelAnnotateVo> findByDocIdForExport(long docId);

    /**
     * 分页查找
     * 
     * @param docId
     * @param p
     * @return
     */
    public PageInfo<Annotate> findByPage(long docId, int p, String date);
    
    /**
     * 指标搜索
     * @param container
     * @return
     */
    public PageInfo<FactorsDetailsVo> findFactorByPage(QueryConditionContainer container);

    /**
     * 删除打标信息
     * 
     * @param ids
     */
    public void deleteByIds(long[] ids);

    /**
     * 
     * @param annotate 打标结果对象
     * @param standardNo 文档的标准编号
     * @param tagState 文档的打标状态
     * @param user 当前用户
     * @param roleLv 用户的权限等级
     * @return
     * @throws Exception
     */
    public int deleteAnntate(Annotate annotate, String standardNo, int tagState, String user, String roleLv)
            throws Exception;
    
    /**
     * 
     * @param annotate 被修改的打标结果对象
     * @param type 被修改的属性类型1、2、3....
     * @param text 被修改后的值
     * @param tagState 文档的打标状态
     * @param user 当前的用户
     * @param roleLv 用户的权限等级
     * @return
     */
    public int updateAnnotateProperty(Annotate annotate, int type, String text, 
            int tagState, String user, String roleLv);
}
