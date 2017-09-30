package com.gooseeker.fss.plugin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gooseeker.fss.builder.DocumentPathBuilder;
import com.gooseeker.fss.builder.PageIncludeBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.utils.AnnotateUtil;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.container.PluginMarkContainer;
import com.gooseeker.fss.entity.Annotate;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.AnnotateXpath;
import com.gooseeker.fss.entity.PageInclude;
import com.gooseeker.fss.handlers.TxtHandler;
import com.gooseeker.fss.index.AnnotateIndexUtil;
import com.gooseeker.fss.service.AnnotateDocumentService;
import com.gooseeker.fss.service.AnnotateService;
import com.gooseeker.fss.service.AnnotateWordService;
import com.gooseeker.fss.service.AnnotateXpathService;
import com.gooseeker.fss.service.DocCheckService;
import com.gooseeker.fss.service.DocumentAuthorityHandlerService;
import com.gooseeker.fss.service.PageIncludeService;
import com.gooseeker.fss.service.StandardsUserService;

@Controller
@RequestMapping("standards/plugin")
public class AnnotatePluginController
{
    @Resource(name = "annotateServiceImpl")
    private AnnotateService annotateService;

    @Resource(name = "annotateXpathServiceImpl")
    private AnnotateXpathService annotateXpathService;

    @Resource(name = "pageIncludeServiceImpl")
    private PageIncludeService pageIncludeService;

    @Resource(name = "standardsUserServiceImpl")
    private StandardsUserService standardsUserService;

    @Resource(name = "annotateDocumentServiceImpl")
    private AnnotateDocumentService annotateDocumentService;
    
    @Resource(name = "docCheckServiceImpl")
    private DocCheckService docCheckService;
    
    @Resource(name ="documentAuthorityHandlerServiceImpl")
    private DocumentAuthorityHandlerService authorityHandlerService;
    
    @Resource(name = "annotateWordServiceImpl")
    private AnnotateWordService annotateWordService;

    protected static Logger logger = Logger.getLogger(AnnotatePluginController.class);

    public static final String AUTHORITY = "authority";// 有权限标识
    public static final String NO_AUTHORITY = "noAuthority";// 没有权限标识
    public static final String DOC_IS_NULL = "docIsNull";// 文档不存在标识
    public static final String NO_LOGIN = "noLogin";// 没有登录标识

    /**
     * 检查用户的操作权限
     * 
     * @param docNo
     * @param request
     * @return
     * @throws Exception
     */
    public PluginMarkContainer checkAuthority(HttpServletRequest request) throws Exception
    {
        String reqUrl = "http://test.gooseeker.com/pdf/standards/pdf/GB 2761-2011234-55.pdf";
                //request.getParameter("docNo");
        logger.info("reqUrl: " + reqUrl);
        String user = request.getUserPrincipal().getName();
        PluginMarkContainer container = authorityHandlerService.hasOperateAuthorityForMark(user, reqUrl);
        return container;
    }

    @RequestMapping("/loadDocAnnotate.html")
    public void loadDocAnnotate(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.addHeader("Access-Control-Allow-Origin", "*");
        PluginMarkContainer container = checkAuthority(request);
        if (container.isHasAuthority())
        {
            try
            {
                List<Annotate> annotates = annotateService.findAnnotateAndXpathByDocId(container.getDoc().getId());
                // 得到已经打过标的数据
                JSONArray markJsonArray = AnnotateUtil.constructMarkJson(annotates);
                if (markJsonArray.length() > 0)
                {
                    container.setValue(markJsonArray);
                }
                container.setSuccess(Constant.SUCCESS_FLAG);
            }
            catch (Exception e)
            {
                container.setErrorMessage("构造打标对象出错");
                logger.error("构造打标对象出错", e);
            }
        }
        container.write(response);
    }

    @RequestMapping("/pageIncludes.html")
    public void pageIncludes(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.addHeader("Access-Control-Allow-Origin", "*");
        String pageNums = request.getParameter("pageNums");
        PluginMarkContainer container = new PluginMarkContainer();
        if (StringUtil.isNotBlank(pageNums))
        {
            container = checkAuthority(request);
            String standardNo = container.getDoc().getStandardNo();
            // 权限检查
            if (container.isHasAuthority())
            {
                List<PageInclude> pageIncludes = new ArrayList<PageInclude>();
                String[] pages = pageNums.split(",");
                for (int i = 0; i < pages.length; i++)
                {
                    if (StringUtil.isNumerical(pages[i]))
                    {
                        try
                        {
                            // 得到page页包含的关键词
                            PageInclude include = pageIncludeService.findByDocNoAndPage(standardNo, Integer.valueOf(pages[i]));
                            pageIncludes.add(include);
                        }
                        catch (Exception e)
                        {
                            logger.error(standardNo + "得到" + pages[i] + "页的关键词出错", e);
                        }
                    }
                }
                if (pageIncludes != null && pageIncludes.size() > 0)
                {
                    JSONArray json = new JSONArray(pageIncludes.toArray());
                    container.setValue(json);
                }
                container.setSuccess(Constant.SUCCESS_FLAG);
            }
        }
        container.write(response);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/saveAnnotate.html")
    public void saveAnnotate(HttpServletResponse response, HttpServletRequest request) throws Exception
    {
        response.addHeader("Access-Control-Allow-Origin", "*");
        PluginMarkContainer container = checkAuthority(request);
        if (container.isHasAuthority())
        {
            Map<String, Object> map = null;
            try
            {
                map = AnnotateUtil.getMarkDatasFromRequest(request, container.getDoc().getId(), 
                        container.getUser());
            }
            catch (Exception e)
            {
                container.setErrorMessage("data format error");
                logger.error("数据格式错误", e);
            }
            if (map != null)
            {
                Annotate annotate = (Annotate) map.get("annotate");
                List<AnnotateXpath> xpathList = (List<AnnotateXpath>) map.get("xpathList");
                if (annotate != null && xpathList != null)
                {
                    if(container.getOperate() == 1 || container.getOperate() == 2)
                    {
                        //审核
                        try
                        {
                            logger.info("保存的审核打标对象： " + annotate.toString());
                            annotateService.addAnnotate(annotate, xpathList, container);
                            container.setSuccess(Constant.SUCCESS_FLAG);
                        }
                        catch (Exception e)
                        {
                            container.setErrorMessage("save check error");
                            logger.error("保存审核信息出错", e);
                        }
                    }
                    else 
                    {
                        //打标
                        try
                        {
                            logger.info("保存的打标对象： " + annotate.toString());
                            annotateService.addAnnotate(annotate, xpathList, null);
                            container.setSuccess(Constant.SUCCESS_FLAG);
                        }
                        catch (Exception e)
                        {
                            container.setErrorMessage("save mark error");
                            logger.error("保存打标信息出错", e);
                        }
                    }
                }
            }
        }
        container.write(response);
    }

    @RequestMapping(value = "/deleteAnnotate.html", method = RequestMethod.GET)
    public void deleteAnnotate(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.addHeader("Access-Control-Allow-Origin", "*");
        // 产品
        String product = request.getParameter("product");
        // 指标
        String factor = request.getParameter("factor");
        PluginMarkContainer container = checkAuthority(request); 
        long docId = container.getDoc().getId();
        if (StringUtil.isNotBlank(product) && StringUtil.isNotBlank(factor)
            && container.isHasAuthority())
        {
            try
            {
                Annotate annotate = annotateService.findByDocIdAndProAndFactor(docId, product, factor);
                if(annotate != null)
                {
                    AnnotateDocument doc = container.getDoc();
                    annotateService.deleteAnntate(annotate, doc.getStandardNo(), 
                            doc.getTagState(), container.getUser(), container.getRoleLv());
                }
            }
            catch (Exception e)
            {
                container.setErrorMessage("error");
                logger.error("docNo=[" + container.getDoc().getStandardNo() + "],product=[" + product
                        + "],factor=[" + factor + "]删除出错", e);
            }
        }
        container.write(response);
    }
    
    /**
     * 请求询问是否需要发送文本内容
     * @param request
     * @param response
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/needtext.html", method = RequestMethod.GET)
    public void needText(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.addHeader("Access-Control-Allow-Origin", "*");
        String pageNums = request.getParameter("pageNums");
        String scroll = request.getParameter("isScroll");
        PluginMarkContainer container = checkAuthority(request); 
        if(StringUtil.isNotBlank(pageNums) && StringUtil.isNotBlank(scroll)
                && container.isHasAuthority())
        {
            logger.info("pageNums: " + pageNums);
            AnnotateDocument doc = container.getDoc();
            List<PageInclude> pageIncludes = new ArrayList<PageInclude>();
            boolean isScroll = Boolean.valueOf(scroll);
            String standardNo = doc.getStandardNo();
            String[] pages = pageNums.split(",");
            for (int i = 0; i < pages.length; i++)
            {
                PageInclude include = pageIncludeService.findByDocNoAndPage(standardNo, Integer.parseInt(pages[i]));
                logger.info("查询" + pages[i] + "页是否有值：" + include);
                if(include != null)
                {
                    //存在，则不需要发送
                    pageIncludes.add(include);
                }
            }
            logger.info("是否需要发送pageInclude：" + pageIncludes.size());
            if (pageIncludes.size() < 1 || isScroll)
            {
                //不存在，或者是滚动加载的方式,需要发送文本内容
                container.setSuccess(Constant.SUCCESS_FLAG);
            }
            else
            {
                //不是滚动加载，pageInclude有值则返回
                container.setValue(pageIncludes.toArray());
            }
        }
        container.write(response);
    }
    
    @RequestMapping(value = "/texthandler.html")
    public void textHandler(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.addHeader("Access-Control-Allow-Origin", "*");
        String page = request.getParameter("page");
        String scroll = request.getParameter("isScroll");
        PluginMarkContainer container = checkAuthority(request); 
        if(StringUtil.isNotBlank(page) && StringUtil.isNotBlank(scroll)
                && container.isHasAuthority())
        {
            boolean isScroll = Boolean.valueOf(scroll);
            int pageNum = Integer.parseInt(page);
            AnnotateDocument doc = container.getDoc();
            logger.info("文档格式：" + doc.getFormat());
            if(!DocumentPathBuilder.DOC_FORMAT_PDF.equals(doc.getFormat()))
            {
                logger.info("文档文本内容处理");
                String standardNo = doc.getStandardNo();
                //检查是否需要文本内容
                PageInclude include = pageIncludeService.findByDocNoAndPage(standardNo, pageNum);
                logger.info("检查PageInclude是否已经存在：" + include);
                if(include == null || isScroll)
                {
                    //接收到的页面的文本内容
                    String text = AnnotateUtil.requestDataResolve(request);
                    logger.info("接收到的文本内容：" + text);
                    if(!StringUtil.isNullOrTrimEmpty(text))
                    {
                        List<String> words = annotateWordService.getAllWords();
                        //根据text匹配构建出PageInclude
                        PageInclude pageInclude = PageIncludeBuilder.builderPageIncludes(pageNum, text, standardNo, words);
                        //添加pageInclude
                        logger.info("从传过来的text中解析出PageInclude ：" + pageInclude);
                        if(pageInclude != null)
                        {
                            pageIncludeService.saveOrUpdate(pageInclude);
                            List<PageInclude> pageIncludes = new ArrayList<PageInclude>();
                            pageIncludes.add(pageInclude);
                            container.setValue(pageIncludes.toArray());
                            logger.info("pageInclude插入并添加到容器中");
                        }
                        //往txt文件中写入内容
                        TxtHandler.writeToTxt(text, doc, pageNum);
                        logger.info("写入txt文件完成");
                        //读取txt文件的全部内容
                        String content = TxtHandler.readTxt(doc);
                        AnnotateIndexUtil indexUtil = new AnnotateIndexUtil();
                        doc.setText(content);
                        //更新索引
                        indexUtil.updateIndex(doc);
                        logger.info("索引更新完成");
                    }
                    container.setSuccess(Constant.SUCCESS_FLAG);
                }
            }
        }
        container.write(response);
    }
}
