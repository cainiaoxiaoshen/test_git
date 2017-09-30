package com.gooseeker.fss.security.login;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.sql.Timestamp;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.gooseeker.fss.entity.Authority;
import com.gooseeker.fss.entity.StandardsUser;
import com.gooseeker.fss.service.StandardsUserService;

@Repository("fssSecurityLoginUserDetailsService")
public class FssSecurityLoginUserDetailsService implements UserDetailsService
{
    private static Logger logger = Logger.getLogger(FssSecurityLoginUserDetailsService.class);

    @Resource(name = "standardsUserServiceImpl")
    private StandardsUserService standardsUserService;

    public UserDetails loadUserByUsername(String username)
    {
        logger.info("in loadByUsername for " + username);
        try
        {
            StandardsUser standardsUser = standardsUserService.findByUserName(username);
            String password = null;
            boolean enabled = false;
            Timestamp expiration = null;
            boolean locked = false;
            ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
            password = standardsUser.getPassword();
            enabled = standardsUser.isEnabled();
            locked = standardsUser.isLocked();
            expiration = standardsUser.getExpiration();
            List<Authority> userAuthorities = standardsUser.getAuthorities();
            for (int i = 0; i < userAuthorities.size(); i++)
            {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userAuthorities.get(i).getRole());
                authorities.add(authority);
            }
            Timestamp current = new Timestamp(new Date().getTime());
            boolean notExpired = current.before(expiration) ? true : false;
            return new User(username, password, enabled, notExpired, notExpired, !locked, authorities);
        }
        catch (Exception ex)
        {
            logger.error("Exception caught: cause=" + ex.getCause() + "; msg="
                    + ex.getMessage() + "; stack is\n" + ex.getStackTrace());
            throw new UsernameNotFoundException("User name:" + username
                    + " can't be found");
        }
    }
}