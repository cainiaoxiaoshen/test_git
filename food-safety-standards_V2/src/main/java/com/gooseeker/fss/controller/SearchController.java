package com.gooseeker.fss.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.commons.entity.Page;
import com.gooseeker.fss.commons.generic.controller.GenericController;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.container.QueryConditionContainer;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.Country;
import com.gooseeker.fss.entity.vo.FactorsDetailsVo;
import com.gooseeker.fss.index.AnnotateIndexUtil;
import com.gooseeker.fss.service.AnnotateDocumentService;
import com.gooseeker.fss.service.AnnotateService;
import com.gooseeker.fss.service.CountryService;

@Controller
@RequestMapping("standards/search")
public class SearchController extends GenericController
{
    protected static Logger logger = Logger.getLogger(SearchController.class);

    @Resource(name = "annotateDocumentServiceImpl")
    private AnnotateDocumentService annotateDocumentService;
    
    @Resource(name = "annotateServiceImpl")
    private AnnotateService annotateService;

    @Resource(name = "countryServiceImpl")
    private CountryService countryService;
    

    /**
     * 文档检索
     * 
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/docSearch.html")
    public String search(Model model, @ModelAttribute("userName") String userName,
        @ModelAttribute("roleLv") String roleLv,
        @RequestParam(value = "type", required = false) String type,
        @RequestParam(value = "tagState", required = false) String tagState,
        @RequestParam(value = "country", required = false) String country,
        @RequestParam(value = "kw", required = false) String kw,
        @RequestParam(value = "p", required = false, defaultValue = "1") int p) throws Exception
    {
        try
        {
            boolean lucene = false;
            QueryConditionContainer container = new QueryConditionContainer(type, tagState,
                country, userName, roleLv, p, kw);
            if (StringUtil.isNotBlank(kw))
            {
                lucene = true;
                AnnotateIndexUtil indexUtil = new AnnotateIndexUtil();
                // 索引分页查询
                Page<AnnotateDocument> page = indexUtil.query(container);
                model.addAttribute("page", page);
            }
            else
            {
                // 没有关键词的时候用此查询（分页查询）
                PageInfo<AnnotateDocument> page = annotateDocumentService.findByPage(container);
                model.addAttribute("page", page);
            }
            // 国家列表
            List<Country> countries = countryService.findAll();
            model.addAttribute("lucene", lucene);
            model.addAttribute("countries", countries);
            model.addAttribute("container", container);
        }
        catch (Exception e)
        {
            logger.error("搜索出错", e);
        }
        return "search/docSearch";
    }
    
    /**
     * 指标检索
     */
    @RequestMapping("/factorSearch.html")
    public String factorSearch(Model model, @ModelAttribute("userName") String userName,
        @ModelAttribute("roleLv") String roleLv,
        @RequestParam(value = "type", required = false) String type,
        @RequestParam(value = "tagState", required = false) String tagState,
        @RequestParam(value = "country", required = false) String country,
        @RequestParam(value = "kw", required = false) String kw,
        @RequestParam(value = "p", required = false, defaultValue = "1") int p)
    {
        QueryConditionContainer container = new QueryConditionContainer(type, tagState,
            country, userName, roleLv, p, kw);
        // 分页查询
        PageInfo<FactorsDetailsVo> page = annotateService.findFactorByPage(container);
        List<Country> countries = countryService.findAll();
        
        model.addAttribute("countries", countries);
        model.addAttribute("container", container);
        model.addAttribute("page", page);
        return "search/factorSearch";
    }
}
