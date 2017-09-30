package com.gooseeker.fss.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.generic.controller.GenericController;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.container.QueryConditionContainer;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.DocCheck;
import com.gooseeker.fss.entity.MarkMonitor;
import com.gooseeker.fss.entity.vo.UserAnnotateDetailsVo;
import com.gooseeker.fss.service.AnnotateDocumentService;
import com.gooseeker.fss.service.DocCheckService;
import com.gooseeker.fss.service.MarkMonitorService;

/**
 * 
 * 监测管理
 *
 */
@Controller
@RequestMapping("standards/manage")
public class MonitorController extends GenericController
{
    @Resource(name = "markMonitorServiceImpl")
    private MarkMonitorService markMonitorService;

    @Resource(name = "annotateDocumentServiceImpl")
    private AnnotateDocumentService annotateDocumentService;

    @Resource(name = "docCheckServiceImpl")
    private DocCheckService docCheckService;

    /**
     * 文档打标监测
     */
    @RequestMapping("/monitor.html")
    public String monitor()
    {
        return "manage/monitor";
    }

    /**
     * 打标监测
     */
    @RequestMapping("/monitorItems.html")
    public String monitorItems(Model model, @ModelAttribute("userName") String userName,
            @ModelAttribute("roleLv") String roleLv,
            @RequestParam("title") String title,
            @RequestParam(value = "mntUser", required = false) String mntUser,
            @RequestParam(value = "tagState", required = false) String tagState,
            @RequestParam(value = "kw", required = false) String kw,
            @RequestParam(value = "p", required = false, defaultValue = "1") int p)
    {
        if (StringUtil.isNotBlank(mntUser))
        {
            userName = StringUtil.decode(mntUser);
        }
        QueryConditionContainer container = new QueryConditionContainer(null, tagState, null, userName, roleLv, p, kw);
        PageInfo<MarkMonitor> pageInfo = markMonitorService.findByPage(container);
        model.addAttribute("page", pageInfo);
        model.addAttribute("container", container);
        model.addAttribute("title", title);
        return "manage/monitorItems";

    }

    /**
     * 用户打标监测
     */
    @RequestMapping("/accountMonitor.html")
    public String accountMonitor(Model model, @ModelAttribute("userName") String userName,
            @RequestParam(value = "mntUser") String mntUser)
    {
        if (StringUtil.isNotBlank(mntUser))
        {
            String roleLv = standardsUserService.getUserRoleLv(userName);
            if (Constant.ROLE_LV_STR_ADMIN.equals(roleLv) || mntUser.equals(userName))
            {   
                // 管理员或者本人
                UserAnnotateDetailsVo antDetails = standardsUserService.findAnnotateDetails(mntUser);
                if (antDetails != null)
                {
                    model.addAttribute("antDetails", antDetails);
                }
            }
            model.addAttribute("mntUser", mntUser);
        }
        return "manage/accountMonitor";
    }

    /**
     * 审核监测
     * 
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/checkDetails.html")
    public String docCheckDetails(Model model, @ModelAttribute("userName") String userName,
            @RequestParam("docId") long docId, @RequestParam(value = "p", required = false, defaultValue = "1") int p)
    {
        AnnotateDocument doc = annotateDocumentService.selectByPrimaryKey(docId);
        if (doc != null)
        {
            // 查出文档的监测信息
            MarkMonitor monitor = markMonitorService.findByDocId(docId, userName, "");
            if (monitor != null)
            {
                // 分页查询文档的审核记录信息
                PageInfo<DocCheck> page = docCheckService.findByPage(docId, p);
                model.addAttribute("page", page);
                model.addAttribute("monitor", monitor);
            }
        }
        model.addAttribute("doc", doc);
        return "manage/checkDetails";
    }
}
