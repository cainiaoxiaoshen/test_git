package com.gooseeker.fss.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gooseeker.fss.builder.AnnotateDocumentBuilder;
import com.gooseeker.fss.builder.DocumentPathBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.entity.ResponseJsonMsg;
import com.gooseeker.fss.commons.generic.controller.GenericController;
import com.gooseeker.fss.commons.utils.DocumentUploadUtil;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.container.DocumentUploadContainer;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.AnnotateWord;
import com.gooseeker.fss.entity.PageInclude;
import com.gooseeker.fss.index.AnnotateIndexUtil;
import com.gooseeker.fss.service.AnnotateDocumentService;
import com.gooseeker.fss.service.AnnotateWordService;
import com.gooseeker.fss.service.PageIncludeService;

@Controller
@RequestMapping("standards/doc")
public class UploadController extends GenericController
{
    protected static Logger logger = Logger.getLogger(UploadController.class);

    @Resource(name = "annotateDocumentServiceImpl")
    private AnnotateDocumentService annotateDocumentService;

    @Resource(name = "annotateWordServiceImpl")
    private AnnotateWordService annotateWordService;

    @Resource(name = "pageIncludeServiceImpl")
    private PageIncludeService pageIncludeService;
    
    @RequestMapping("/upload.html")
    public void upload(HttpServletResponse response, 
            @ModelAttribute("userName") String userName,
            @RequestParam("type") String type,
            @RequestParam MultipartFile fileUpload) throws Exception
    {
        AnnotateIndexUtil indexUtil = new AnnotateIndexUtil();
        List<AnnotateWord> words = annotateWordService.findAll();
        String repeatFileNames = Constant.STR_EMPTY;
        JSONObject msg = new JSONObject();
        DocumentUploadContainer container = DocumentUploadUtil.upload(fileUpload, userName, type, words);
        if(container != null)
        {
            List<PageInclude> pageIncludes = container.getPageIncludes();
            List<AnnotateDocument> documents = container.getDocuments();
            repeatFileNames = container.getRepeatFileNames();
            if(documents != null && documents.size() > 0)
            {
                //插入文档信息，这里插入的文档都是没有存在的
                annotateDocumentService.saveOrUpdates(documents);
            }
            //只有pdf文档解析了得到了文本内容，匹配到关键词后pageIncludes才会有值
            if(pageIncludes != null && pageIncludes.size() > 0)
            {
                //添加pageInclude信息
                pageIncludeService.addPageIncludes(pageIncludes);
                //添加索引
                indexUtil.addIndexs(documents);
            }
        }
        msg.put("type", type);
        msg.put("repeatFileNames", repeatFileNames);
        response.getWriter().write(msg.toString());
    }
    
    @RequestMapping("/repeatUpload.html")
    public void repeatUpload(HttpServletResponse response,
            @ModelAttribute("userName") String userName,
            @RequestParam("type") String type,
            @RequestParam("repeatFileNames") String repeatFileNames) throws Exception
    {
        ResponseJsonMsg msg = new ResponseJsonMsg(false);
        if(StringUtil.isNotBlank(repeatFileNames) && StringUtil.isNumerical(type))
        {
            //已经存在的文档标号
            String[] rptDocNames = repeatFileNames.split(Constant.SPLIT_COMMA);
            AnnotateIndexUtil indexUtil = new AnnotateIndexUtil();
            List<AnnotateWord> words = annotateWordService.findAll();
            DocumentUploadContainer container = DocumentUploadUtil.reUpload(rptDocNames, userName, type, words);
            List<PageInclude> pageIncludes = container.getPageIncludes();
            List<AnnotateDocument> documents = container.getDocuments();
            if(documents != null && documents.size() > 0)
            {
                //更新文档信息
                annotateDocumentService.updates(documents);
            }
            //只有pdf文档解析了得到了文本内容，匹配到关键词后pageIncludes才会有值
            if(pageIncludes != null && pageIncludes.size() > 0)
            {
                //添加pageInclude信息
                pageIncludeService.addPageIncludes(pageIncludes);
                //更新索引
                indexUtil.updateIndexs(documents);
            }
            msg.setSuccess(true);
        }
        msg.write(response);
    }
    
    @RequestMapping("/uploadUrlExcel.html")
    public void uploadUrlExcel(HttpServletResponse response,
            @ModelAttribute("userName") String userName,
            @RequestParam MultipartFile excelFile,
            @RequestParam(value = "typeExcel") int type) throws Exception
    {
        StringBuilder repeatInfo = new StringBuilder();
        JSONArray repeatJsonObj = new JSONArray();
        JSONObject msg = new JSONObject();
        boolean success = false;
        List<AnnotateDocument> documents = DocumentUploadUtil
            .uploadUrlExcel(excelFile, userName, type);
        
        if (documents != null)
        {
            for (int i = 0; i < documents.size(); i++)
            {
                AnnotateDocument doc = documents.get(i);
                String reqUrl = doc.getReqUrl();
                String standardNo = doc.getStandardNo();
                AnnotateDocument urlDoc = annotateDocumentService.selectByReqUrl(reqUrl);
                AnnotateDocument noDoc = annotateDocumentService.selectByStandardNo(standardNo);
                if(urlDoc == null && noDoc == null)
                {
                    annotateDocumentService.saveOrUpdate(doc);
                }
                else 
                {
                    JSONObject json = new JSONObject();
                    json.put("standardNo", standardNo);
                    json.put("reqUrl", reqUrl);
                    repeatJsonObj.put(json);
                    repeatInfo.append("[").append(standardNo).append("+").append(reqUrl).append("]");
                }
            }
            success = true;
        }
        else
        {
            logger.error("文档" + excelFile.getOriginalFilename() + "导入出错");
        }
        msg.put("success", success);
        msg.put("repeatInfo", repeatInfo.toString());
        msg.put("repeatJsonObj", repeatJsonObj.toString());
        msg.put("type", type);
        response.getWriter().write(msg.toString());
    }
    
    @RequestMapping("/repeatUploadUrlExcel.html")
    public void repeatUploadUrlExcel(HttpServletResponse response,
            @ModelAttribute("userName") String userName,
            @RequestParam(value="repeatObjMsg") String repeatObjMsg,
            @RequestParam(value="type") int type) throws Exception
    {
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        JSONArray repeatJsonArray = new JSONArray(repeatObjMsg);
        List<AnnotateDocument> documents = new ArrayList<AnnotateDocument>();
        ResponseJsonMsg msg = new ResponseJsonMsg(false);
        for (int i = 0; i < repeatJsonArray.length(); i++)
        {
            JSONObject jsonObj = (JSONObject)repeatJsonArray.get(i);
            String standardNo = (String)jsonObj.get("standardNo");
            String reqUrl = (String)jsonObj.get("reqUrl");
            AnnotateDocument document = AnnotateDocumentBuilder.builder(userName, standardNo, reqUrl, 
                String.valueOf(type), DocumentPathBuilder.DOC_FORMAT_HTML, createTime, true);
            documents.add(document);
        }
        if(documents.size() > 0)
        {
            annotateDocumentService.updates(documents);
            msg.setSuccess(true);
        }
        msg.write(response);
    }
    
    /**
     * 添加在线网址
     * @param request
     * @param response
     * @param docNos
     * @param urls
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/addHTMLUrl.html")
    public void addHTMLUrl(HttpServletResponse response,
            @ModelAttribute("userName") String userName,
            @RequestParam(value = "isRepeat") boolean isRepeat,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "standardNo") String standardNo,
            @RequestParam(value = "reqUrl") String reqUrl) throws Exception
    {
        ResponseJsonMsg msg = new ResponseJsonMsg(true);
        StringBuilder repeatInfo = new StringBuilder();
        if(StringUtil.isNumerical(type))
        {
            AnnotateDocument urlDoc = annotateDocumentService.selectByReqUrl(reqUrl);
            AnnotateDocument noDoc = annotateDocumentService.selectByStandardNo(standardNo);
            /* 构造出要添加的文档
             * urlDoc或者noDoc不为null则表示网址或者编号已经存在
             */
            AnnotateDocument doc = DocumentUploadUtil.addHTMLUrl(urlDoc, noDoc, userName, reqUrl, standardNo, type, isRepeat);
            if(doc != null)
            {
                annotateDocumentService.saveOrUpdate(doc);
                msg.setSuccess(true);
            }
            else
            {
                msg.setSuccess(false);
                repeatInfo.append("[").append(standardNo).append("+").append(reqUrl).append("]");
            }
            JSONObject json = new JSONObject();
            json.put("standardNo", standardNo);
            json.put("reqUrl", reqUrl);
            json.put("repeatInfo", repeatInfo.toString());
            json.put("type", type);
            msg.setErrorMsg(json.toString());
        }
        msg.write(response);
    }
}
