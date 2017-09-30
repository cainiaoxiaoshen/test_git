package com.gooseeker.fss.commons.generic.controller;

import java.security.Principal;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.gooseeker.fss.service.StandardsUserService;

public abstract class GenericController
{
    @Resource(name = "standardsUserServiceImpl")
    protected StandardsUserService standardsUserService;
    
    @ModelAttribute()
    protected void getUserInfo(Principal principal, Model model)
    {
        String userName = principal.getName();
        String roleLv = standardsUserService.getUserRoleLv(userName);
        model.addAttribute("roleLv", roleLv);
        model.addAttribute("userName", userName);
    }
}
