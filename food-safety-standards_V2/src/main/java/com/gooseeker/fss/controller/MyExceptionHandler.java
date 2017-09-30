package com.gooseeker.fss.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MyExceptionHandler
{
    @ExceptionHandler({ArithmeticException.class})
    public ModelAndView handlerTestException(Exception ex)
    {
        ModelAndView mv = new ModelAndView("test/error");
        mv.addObject("exception", ex);
        return mv;
    }
}
