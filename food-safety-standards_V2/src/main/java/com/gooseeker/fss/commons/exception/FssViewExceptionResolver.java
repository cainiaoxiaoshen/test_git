package com.gooseeker.fss.commons.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class FssViewExceptionResolver implements HandlerExceptionResolver
{

    protected static Logger logger = Logger.getLogger(FssViewExceptionResolver.class);

    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
    {

        FssViewException fssViewException = null;
        if (ex instanceof FssViewException)
        {
            fssViewException = (FssViewException) ex;
        }
        else
        {
            fssViewException = new FssViewException("操作出错");
        }
        logger.error(ex.getMessage(), ex);
        // 错误信息
        String message = fssViewException.getMessage();
        ModelAndView modelAndView = new ModelAndView();
        // 将错误信息传到页面
        modelAndView.addObject("message", message);
        // 指向错误页面
        modelAndView.setViewName("error");
        return modelAndView;
    }

}
