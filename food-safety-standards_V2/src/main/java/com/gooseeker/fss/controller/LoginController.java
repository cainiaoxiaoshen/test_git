package com.gooseeker.fss.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.entity.MyUser;
import com.gooseeker.fss.mapper.UserMapper;
import com.gooseeker.fss.service.StandardsUserService;

@Controller
public class LoginController
{
    protected static Logger logger = Logger.getLogger(LoginController.class);

    @Resource(name = "standardsUserServiceImpl")
    private StandardsUserService standardsUserService;

    @RequestMapping("/index.html")
    public String index()
    {
        // 重定向到登录页面
        return "redirect:/standards/login.html";
    }

    /**
     * 登录页面
     * @param request
     * @param model
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/standards/login.html")
    public String login(HttpServletRequest request, Model model)
    {

        HttpSession session = request.getSession();
        Exception ex = (Exception) session
                .getValue("SPRING_SECURITY_LAST_EXCEPTION");
        String errorMsg = "";
        if (ex instanceof UsernameNotFoundException)
        {
            errorMsg = "用户不存在";
        }
        if (ex instanceof BadCredentialsException)
        {
            errorMsg = "用户或密码错误";
        }
        if (ex instanceof LockedException)
        {
            errorMsg = "该帐户已经被锁定";
        }
        if (ex instanceof AccountExpiredException)
        {
            errorMsg = "账号已经过期";
        }
        if (ex instanceof DisabledException)
        {
            errorMsg = "账号不能使用";
        }
        String error = request.getParameter("error");
        if (StringUtil.isNotBlank(error) && "true".equals(error))
        {
            model.addAttribute("errorMsg", errorMsg);
        }
        return "login";
    }

    /**
     * 错误页面
     * @param model
     * @return
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/error404.html")
    public String error(Model model) throws IOException
    {
        model.addAttribute("message", "error404");
        // 指向错误页面
        return "error";
    }
    
    @RequestMapping(value = "/standards/getCsrf.html", method = RequestMethod.GET)
    public String getCsrf()
    {
        return "csrf";
    }

}
