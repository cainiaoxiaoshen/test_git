package com.gooseeker.fss.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.commons.entity.ResponseJsonMsg;
import com.gooseeker.fss.commons.generic.controller.GenericController;
import com.gooseeker.fss.commons.utils.PropertiesUtil;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.entity.StandardsUserRole;
import com.gooseeker.fss.entity.vo.UserWorkTasksVo;
import com.gooseeker.fss.service.AuthorityService;

/**
 * 
 * 账号管理
 *
 */
@Controller
@RequestMapping("standards/manage")
public class AccountManageController extends GenericController
{
    protected static Logger logger = Logger.getLogger(AccountManageController.class);

    @Resource(name = "authorityServiceImpl")
    private AuthorityService authorityService;

    /**
     * 
     * 用户管理首页
     */
    @RequestMapping("/account.html")
    public String account(Model model, @RequestParam(value = "p", required = false, defaultValue = "1") Integer p)
    {
        // 得到用户的任务情况信息
        PageInfo<UserWorkTasksVo> page = standardsUserService.findUserWorkTasksByPage(p);
        model.addAttribute("page", page);
        return "manage/account";
    }

    /**
     * 
     * 创建用户
     */
    @RequestMapping("/createAccount.html")
    public void createAccount(HttpServletResponse response, StandardsUserRole user) throws IOException
    {
        ResponseJsonMsg msg = new ResponseJsonMsg();
        if (user != null)
        {
            try
            {
                Calendar c = Calendar.getInstance();
                c.setTime(new Timestamp(System.currentTimeMillis()));
                //创建日期
                user.setCreateDate(new Timestamp(c.getTimeInMillis()));
                String validDate = PropertiesUtil.getProperty("valid.date.month");
                c.add(Calendar.MONTH, Integer.valueOf(validDate));
                //过期时间，默认为3个月
                user.setExpiration(new Timestamp(c.getTimeInMillis()));
                //添加用户
                standardsUserService.addAccount(user);
                msg.setSuccess(true);
            }
            catch (Exception e)
            {
                logger.error("创建用户出错", e);
            }
        }
        msg.write(response);
    }

    /**
     * 
     * 修改用户信息
     * @throws IOException 
     */
    @RequestMapping("/modifyAccount.html")
    public void modifyAccount(HttpServletResponse response, HttpServletRequest request) throws IOException
    {
        //用户id
        String uid = request.getParameter("uid");
        //修改的角色
        String role = request.getParameter("role");
        //密码
        String password = request.getParameter("password");
        ResponseJsonMsg msg = new ResponseJsonMsg();
        if (StringUtil.isNumerical(uid) && StringUtil.isNotBlank(role)
                && StringUtil.isNotBlank(password))
        {
            try
            {
                standardsUserService.updateAccount(uid, password, role);
                msg.setSuccess(true);
            }
            catch (Exception e)
            {
                logger.error("修改用户uid=[" + uid + "]信息出错", e);
            }
            
        }
        msg.write(response);
    }

    /**
     * 所有账号列表显示
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/accountItems.html")
    public String accountItems(HttpServletRequest request, Model model)
    {
        //0：打标员，1：审核员
        String role = request.getParameter("role");
        if (StringUtil.isNotBlank(role))
        {
            List<UserWorkTasksVo> accounts = standardsUserService.findAccountItems(role);
            model.addAttribute("accounts", accounts);
        }
        return "manage/accountItems";
    }

}
