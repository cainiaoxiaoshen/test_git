//package com.gooseeker.fss.commons.utils;
//
//import java.security.Principal;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//
//public class AccountUtil
//{
//    /**
//     * 得到用户名
//     * @param request
//     * @return
//     * @see [类、类#方法、类#成员]
//     */
//    public static String getAccountName(HttpServletRequest request)
//    {
//        String username = null;
//        Principal principal = request.getUserPrincipal();
//        if (principal != null)
//        {
//            username = principal.getName();
//        }
//        else
//        {
//            SecurityContext context = (SecurityContext) request.getSession()
//                    .getAttribute("SPRING_SECURITY_CONTEXT");
//            if (context != null)
//            {
//                Authentication authentication = context.getAuthentication();
//                if (authentication != null)
//                {
//                    username = authentication.getName();
//                }
//            }
//        }
//        return username;
//    }
//}
