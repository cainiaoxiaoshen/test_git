package com.gooseeker.fss.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.builder.DocumentPathBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.entity.Page;
import com.gooseeker.fss.commons.entity.ResponseJsonMsg;
import com.gooseeker.fss.commons.exception.FssViewException;
import com.gooseeker.fss.commons.generic.controller.GenericController;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.Country;
import com.gooseeker.fss.handlers.TxtHandler;
import com.gooseeker.fss.index.AnnotateIndexUtil;
import com.gooseeker.fss.service.AnnotateDocumentService;
import com.gooseeker.fss.service.AnnotateWordService;
import com.gooseeker.fss.service.CountryService;
import com.gooseeker.fss.service.PageIncludeService;
import com.gooseeker.fss.service.StandardsUserService;

/**
 * 
 * 任务分配
 *
 */
@Controller
@RequestMapping("standards/manage")
public class TaskController extends GenericController
{

    @Resource(name = "annotateDocumentServiceImpl")
    private AnnotateDocumentService annotateDocumentService;

    @Resource(name = "countryServiceImpl")
    private CountryService countryService;

    @Resource(name = "standardsUserServiceImpl")
    private StandardsUserService standardsUserService;
    
    @Resource(name = "annotateWordServiceImpl")
    private AnnotateWordService annotateWordService;

    @Resource(name = "pageIncludeServiceImpl")
    private PageIncludeService pageIncludeService;
    
    protected static Logger logger = Logger.getLogger(TaskController.class); 
    
    @RequestMapping("/task.html")
    public String task()
    {
        return "manage/task";
    }
    
    /**
     * 
     * @param response
     * @param annotateUser 打标员
     * @param firstCheckUser 一审员
     * @param secondCheckUser 二审员
     * @param docIds 被指定分配的文档的id
     * @throws Exception
     */
    @RequestMapping("/saveTasks.html")
    public void saveTasks(HttpServletResponse response,
        @RequestParam("annotateUser") String annotateUser,
        @RequestParam("firstCheckUser") String firstCheckUser,
        @RequestParam("secondCheckUser") String secondCheckUser,
        @RequestParam("docIds[]") long[] docIds) throws Exception
    {
        ResponseJsonMsg msg = new ResponseJsonMsg(true);
        StringBuffer msgBuffer = new StringBuffer();
        if (StringUtil.isNotBlank(annotateUser) && StringUtil.isNotBlank(firstCheckUser)
                && StringUtil.isNotBlank(secondCheckUser))
        {
            for (int i = 0; i < docIds.length; i++)
            {
                AnnotateDocument doc = annotateDocumentService.selectByPrimaryKey(docIds[i]);
                try {
                    if(doc != null)
                    {
                        doc.setAnnotateUser(annotateUser);
                        doc.setFirstCheckUser(firstCheckUser);
                        doc.setSecondCheckUser(secondCheckUser);
                        // 给文档设置打标员、一审员、二审员
                        annotateDocumentService.update(doc);
                        AnnotateIndexUtil indexUtil = new AnnotateIndexUtil();
                        //去找到文档对应的txt文件取出里面的内容设置到doc对象的text属性中
                        String text = TxtHandler.readTxt(doc);
                        doc.setText(text);
                        //更新索引
                        indexUtil.updateIndex(doc);
                    }
                } catch (Exception e) {
                    msg.setSuccess(false);
                    msgBuffer.append(doc.getStandardNo()).append(Constant.SPLIT_COMMA);
                    logger.error("给文档编号docNo=[" + doc.getStandardNo() + "]添加任务分配失败", e);
                }
            }
            msg.setErrorMsg(msgBuffer.toString());
        }
        msg.write(response);
    }

    /**
     * 删除任务分配
     * 
     * @param request
     * @param response
     * @throws IOException 
     */
    @RequestMapping("/delDocTasks.html")
    public void delDocTasks(HttpServletResponse response, 
        @RequestParam("docId") long docId) throws Exception
    {
        ResponseJsonMsg msg = new ResponseJsonMsg(true);
        AnnotateDocument doc = annotateDocumentService.selectByPrimaryKey(docId);
        try
        {
            if(doc != null)
            {
                doc.setAnnotateUser(Constant.STR_EMPTY);
                doc.setFirstCheckUser(Constant.STR_EMPTY);
                doc.setSecondCheckUser(Constant.STR_EMPTY);
                // 给文档打标员、一审员、二审员设置为空
                annotateDocumentService.update(doc);
                AnnotateIndexUtil indexUtil = new AnnotateIndexUtil();
                if(!DocumentPathBuilder.DOC_FORMAT_PDF.equals(doc.getFormat()))
                {
                    //去找到文档对应的txt文件取出里面的内容设置到doc对象的text属性中
                    String text = TxtHandler.readTxt(doc);
                    doc.setText(text);
                }
                //更新索引
                indexUtil.updateIndex(doc);
            }
        }
        catch (Exception e)
        {
            logger.error("删除文档docNo=[" + doc.getStandardNo() + "]出错", e);
            msg.setSuccess(false);
        }
        msg.write(response);
    }

    /**
     * 文档列表
     * 
     * @param request
     * @param model
     * @return
     * @throws FssViewException
     * @throws Exception
     */
    @RequestMapping("/docSelect.html")
    public String docSelect(Model model,
        @RequestParam(value = "country", required = false, defaultValue = Constant.STR_ZERO) String country,
        @RequestParam(value = "docNo", required = false) String docNo,
        @RequestParam(value = "p", required = false, defaultValue = "1") int p) throws Exception
    {
        PageInfo<AnnotateDocument> pageInfo = annotateDocumentService.findNoAssignmentDoc(country, docNo, p);
        List<Country> countries = countryService.findAll();
        model.addAttribute("countries", countries);
        model.addAttribute("country", country);
        model.addAttribute("docNo", docNo);
        model.addAttribute("page", pageInfo);
        return "manage/docSelect";
    }
}
