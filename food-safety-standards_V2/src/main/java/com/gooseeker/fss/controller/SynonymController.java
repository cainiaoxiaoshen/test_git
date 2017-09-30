package com.gooseeker.fss.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gooseeker.fss.commons.entity.Page;
import com.gooseeker.fss.entity.Synonym;
import com.gooseeker.fss.service.StandardsUserService;
import com.gooseeker.fss.service.SynonymService;

@Controller
@RequestMapping("standards/doc")
public class SynonymController
{

    @Resource(name = "SynonymServiceImpl")
    private SynonymService synonymService;

    @Resource(name = "standardsUserServiceImpl")
    private StandardsUserService standardsUserService;

    @ModelAttribute("roleLv")
    public String getRoleLv(HttpServletRequest request)
    {
        String user = request.getUserPrincipal().getName();
        String roleLv = standardsUserService.getUserRoleLv(user);
        return roleLv;
    }

    @RequestMapping("/docSynonym.html")
    public String showSym(Model model, HttpServletRequest request)
            throws Exception
    {
        String p = request.getParameter("p");
        int currentPage = (p == null) ? 1 : Integer.valueOf(p);
        Page<Synonym> page = synonymService.findAllSynonym(currentPage);
        model.addAttribute("page", page);

        return "doc/docSynonym";
    }

    /**
     * 删除同义词
     */
    @RequestMapping("/delSynonym.html")
    public void delSym(HttpServletResponse response,
            @RequestParam(value = "id", required = true) String symId)
            throws Exception
    {
        try
        {
            // 根据id删除
            synonymService.delSynonym(Long.parseLong(symId));
        }
        catch (Exception e)
        {
            response.getWriter().write("error");
        }
        response.getWriter().write("success");
    }

    /**
     * 修改
     */
    @RequestMapping("/alterSynonym.html")
    public void alterSym(Model model, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {

        String id = request.getParameter("id");
        // 主词
        String mainWord = request.getParameter("mainWord");
        // 修改的同义词
        String synWord = request.getParameter("synWord");
        try
        {
            synonymService.alterSynonym(id, mainWord, synWord);
        }
        catch (Exception e)
        {
            response.getWriter().write("error");
        }
        response.getWriter().write("success");
    }

    /**
     * 新增
     */
    @RequestMapping("/addSynonym.html")
    public void addSym(Model model, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {

        String mainWord = request.getParameter("mainWord");
        String synWord = request.getParameter("synWord");
        // 用户名
        String createUser = request.getParameter("username");
        // 日期
        String createTime = request.getParameter("date");

        try
        {
            // 添加
            synonymService
                    .addSynonym(mainWord, synWord, createUser, createTime);
        }
        catch (Exception e)
        {
            response.getWriter().write("error");
        }
        response.getWriter().write("success");
    }
}
