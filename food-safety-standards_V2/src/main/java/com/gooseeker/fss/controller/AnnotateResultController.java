package com.gooseeker.fss.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.commons.entity.AnnotatePropertyDictionary;
import com.gooseeker.fss.commons.entity.ExcelEntity;
import com.gooseeker.fss.commons.entity.ResponseJsonMsg;
import com.gooseeker.fss.commons.generic.controller.GenericController;
import com.gooseeker.fss.commons.utils.ExcelUtil;
import com.gooseeker.fss.commons.utils.FileUtil;
import com.gooseeker.fss.commons.utils.PropertiesUtil;
import com.gooseeker.fss.entity.Annotate;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.vo.ExcelAnnotateVo;
import com.gooseeker.fss.service.AnnotateDocumentService;
import com.gooseeker.fss.service.AnnotateService;
import com.gooseeker.fss.service.DocumentAuthorityHandlerService;


@Controller
@RequestMapping("standards/annotate")
public class AnnotateResultController extends GenericController
{
    protected static Logger logger = Logger.getLogger(AnnotateResultController.class);

    @Resource(name = "annotateDocumentServiceImpl")
    private AnnotateDocumentService annotateDocumentService;

    @Resource(name = "annotateServiceImpl")
    private AnnotateService annotateService;

    @Resource(name ="documentAuthorityHandlerServiceImpl")
    private DocumentAuthorityHandlerService authorityHandlerService;
    
    
    /**
     * 打标结果页面
     * @param model
     * @param userName 当前用户
     * @param roleLv 用户权限等级
     * @param docId 文档id
     * @param docNo 文档编号
     * @param date 筛选日期
     * @param p 当前页数
     * @return
     * @throws Exception
     */
    @RequestMapping("/annotate.html")
    public String annotate(Model model, @ModelAttribute("userName") String userName,
            @ModelAttribute("roleLv") String roleLv,
            @RequestParam(value = "docId", required = false) Long docId,
            @RequestParam(value = "docNo", required = false) String docNo,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "p", required = false, defaultValue = "1") int p) throws Exception
    {
        AnnotateDocument doc = annotateDocumentService.selectByPrimaryKey(docId);
        if(doc == null)
        {
            doc = annotateDocumentService.selectByStandardNo(docNo);
        }
        if (doc != null)
        {
            long id = doc.getId();
            // 分页查询文档的打标结果信息
            PageInfo<Annotate> page = annotateService.findByPage(id, p, date);
            boolean canDelete = authorityHandlerService.hasOperateAuthority(id, userName, roleLv);
            List<ExcelAnnotateVo> annotates = annotateService.findByDocIdForExport(id);
            int antTotal = annotates.size();
            model.addAttribute("antTotal", antTotal);
            model.addAttribute("canDelete", canDelete);
            model.addAttribute("date", date);
            model.addAttribute("doc", doc);
            model.addAttribute("page", page);
        }
        return "doc/annotate";
    }
    
    /**
     * 打标结果下载
     * @param request
     * @param response
     */
    @RequestMapping(value = "/exportExcel.html", method = RequestMethod.GET)
    public void exportExcel(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("userName") String userName,
            @RequestParam("docId") long docId)
    {
        List<ExcelAnnotateVo> annotates = annotateService.findByDocIdForExport(docId);
        int antTotal = annotates.size();
        if(antTotal > 0)
        {
            List<ExcelEntity<ExcelAnnotateVo>> excelEntitys = new ArrayList<ExcelEntity<ExcelAnnotateVo>>();
            //每个sheet页限制的数据条数
            int dataNum = PropertiesUtil.getIntProperty("excel.sheet.data.num");
            //有多少个sheet页
            int sheetTotal = antTotal % dataNum == 0 ? antTotal / dataNum : (antTotal / dataNum + 1);
            
            for (int i = 0; i < sheetTotal; i++)
            {
                //设置ExcelEntity内容
                ExcelEntity<ExcelAnnotateVo> entity = new ExcelEntity<ExcelAnnotateVo>();
                int fromIndex = dataNum * i;
                int toIndex = fromIndex + dataNum;
                if(toIndex > antTotal)
                {
                    toIndex = antTotal;
                }
                entity.setBodys(annotates.subList(fromIndex, toIndex));
                entity.setSheetName("Sheet" + (i + 1));
                entity.setHeads(AnnotatePropertyDictionary.getOutNamelistWithOutPage());
                excelEntitys.add(entity);
            }
            ExcelUtil<ExcelAnnotateVo> util = new ExcelUtil<ExcelAnnotateVo>();
            String path = PropertiesUtil.getProperty("excel.file.create.path");
            //火狐中54版本下载文件名带有空格下载有问题所以docNo.replace(" ", "")去掉空格
            path = path + "/" + String.valueOf(docId).replace(" ", "") + "_" + userName + "_" + System.currentTimeMillis() + ".xlsx";
            //创建excel文件
            File excel = new File(path);
            util.creatExcel(excel, excelEntitys);
            FileUtil.download(response, request, excel, "application/x-excel");
            excel.delete();
        }
    }
    
    /**
     * 修改打标结果信息
     * @param request
     * @param response
     * @throws NumberFormatException
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/update.html")
    public void update(HttpServletResponse response,
        @RequestParam("docId") long docId,
        @RequestParam("antId") long antId,
        @RequestParam("text") String text,
        @RequestParam("type") int type,
        @ModelAttribute("userName") String userName,
        @ModelAttribute("roleLv") String roleLv) throws NumberFormatException, Exception
    {
        ResponseJsonMsg msg = new ResponseJsonMsg(false);
        /*
         * 1、先判断是不是唯一键修改，根据type来判断type=1--》产品，type=2--》指标
         * 2、如果不是唯一键，根据type找出被修改的属性
         * 3、修改annotate的对应的属性，修改属性对应的annotateXpath中的updateText=被修改后的值、updated=true
         * 4、如果修改的是唯一键，先检查被修改后的product、factor是否存在，如果存在则返回提示不能修改
         * 5、不存在则修改annotate的对应的属性，修改原来的product、factor对应的annotateXpath中的所有结果为最新的product和factor
         * 修改被修改的属性的annotateXpath：updateText=被修改后的值、updated=true
         */
        AnnotateDocument doc = annotateDocumentService.selectByPrimaryKey(docId);
        if(authorityHandlerService.hasOperateAuthority(doc, userName, roleLv))
        {
            Annotate annotate = annotateService.selectByPrimaryKey(antId);
            if(annotate != null)
            {
                int errorCode = annotateService.updateAnnotateProperty(annotate, type, 
                        text, doc.getTagState(), userName, roleLv);
                
                if(errorCode == 1)
                {
                    msg.setSuccess(true);
                }
                else if(errorCode == -1)
                {
                    msg.setErrorMsg("唯一键已经存在，不能修改");
                }
                else
                {
                    msg.setErrorMsg("修改出错");
                }
            }
        }
        msg.write(response);
    }
    
    /**
     * 删除打标结果
     * @throws Exception 
     */
    @RequestMapping("/delete.html")
    public void delete(HttpServletResponse response,
            @ModelAttribute("userName") String userName,
            @ModelAttribute("roleLv") String roleLv,
            @RequestParam("docId") long docId,
            @RequestParam("antIds[]") Long[] antIds) throws Exception
    {
        ResponseJsonMsg msg = new ResponseJsonMsg(true);
        if (antIds != null && antIds.length > 0)
        {
            StringBuffer errorMsg = new StringBuffer();
            AnnotateDocument doc = annotateDocumentService.selectByPrimaryKey(docId);
            if (doc != null && authorityHandlerService.hasOperateAuthority(doc, userName, roleLv))
            {
                for (int i = 0; i < antIds.length; i++)
                {
                    Annotate annotate = annotateService.selectByPrimaryKey(antIds[i]);
                    if(annotate != null)
                    {
                        String product = annotate.getProduct();
                        String factor = annotate.getFactor();
                        try
                        {
                            // 根据文档编号，产品名，物质名删除打标结果
                            annotateService.deleteAnntate(annotate, 
                                    doc.getStandardNo(), doc.getTagState(), userName, roleLv);
                        }
                        catch (Exception e)
                        {
                            msg.setSuccess(false);
                            errorMsg.append("产品=[").append(product).append("]物质=[").append(factor).append("]；");
                            logger.error("删除docNo=" + doc.getStandardNo() + ", product=" + product + ", factor" + factor + "出错", e);
                        }
                    }
                }
            }
            msg.setErrorMsg(errorMsg.toString());
        }
        msg.write(response);
    }

}
