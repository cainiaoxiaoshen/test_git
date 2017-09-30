package com.gooseeker.fss.commons.interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.entity.Authority;
import com.gooseeker.fss.entity.StandardsUser;
import com.gooseeker.fss.entity.UrlRole;
import com.gooseeker.fss.service.StandardsUserService;
import com.gooseeker.fss.service.UrlRoleService;

/**
 * 权限的细节控制
 * 
 * @author tianju
 *
 */
public class AuthorityHandlerInterceptor implements HandlerInterceptor
{

    @Resource(name = "standardsUserServiceImpl")
    private StandardsUserService standardsUserService;

    @Resource(name = "urlRoleServiceImpl")
    private UrlRoleService urlRoleService;

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception
    {
        String user = null;
        try
        {
            user = request.getUserPrincipal().getName();
        }
        catch (Exception e) {}
        String url = request.getRequestURI();
        if (StringUtil.isNotBlank(user))
        {
            if ((url.indexOf("/standards/doc/") > 0 || url.indexOf("/standards/factor/") > 0))
            {
                List<Authority> authorities = userRolesCheck(user);
                // 首先检查用户是否存在已经是否有角色分配
                if (authorities != null)
                {
                    // 然后再检查用户是否有权限访问该url
                    String authorityUrl = url.substring(
                            url.lastIndexOf("/") + 1, url.length());
                    boolean hasAuthority = userUrlAuthorityCheck(authorityUrl, authorities);
                    if (!hasAuthority)
                    {
                        request.getRequestDispatcher("/WEB-INF/pages/noPermission.jsp").forward(request, response);
                    }
                    return hasAuthority;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                // 除了/standards/doc/ 和/standards/factor/的其他url都返回true
                // 其他url在security中配置了权限限制
                return true;
            }
        }
        else
        {
            // 用户名为null可能是/standards/login.html url
            // 插件部分由插件的controller自己处理
            if (url.indexOf("/standards/login.html") > 0
                    || url.indexOf("/standards/logout.html") > 0
                    || url.indexOf("/standards/plugin/") > 0)
            {
                // 不是login.html直接返回true
                return true;
            }
            else
            {
                response.setHeader("sessionstatus", "timeout");
                return false;
            }
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception
    {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) throws Exception
    {

    }

    private List<Authority> userRolesCheck(String user)
    {
        StandardsUser standardsUser = standardsUserService.findByUserName(user);
        if(standardsUser != null)
        {
            return standardsUser.getAuthorities();
        }
        return null;
    }

    private boolean userUrlAuthorityCheck(String authorityUrl,
            List<Authority> authorities) throws Exception
    {

        // 访问该url所需要的用户权限
        List<UrlRole> urlRoles = urlRoleService.findUrlRoleByUrlName(authorityUrl);
        if (urlRoles.size() == 0)
        {
            // 表示该url所有的用户权限都可以访问
            return true;
        }
        else
        {
            boolean hasAuthority = false;
            for (UrlRole urlRole : urlRoles)
            {
                for (Authority role : authorities)
                {
                    if (Constant.ROLE_ADMIN.equals(role.getRole()))
                    {
                        // 有admin权限
                        hasAuthority = true;
                        break;
                    }
                    if (urlRole.getRole().equals(role.getRole()))
                    {
                        // 用户拥有url的访问权限
                        hasAuthority = true;
                        break;
                    }
                }
            }
            return hasAuthority;
        }
    }
}
