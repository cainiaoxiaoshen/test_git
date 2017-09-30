package com.gooseeker.fss.security.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.gooseeker.fss.security.login.FssSecurityLoginUserDetailsService;

public class CustomerAuthenticationProvider implements AuthenticationProvider
{
    FssSecurityLoginUserDetailsService userDetailsService;
 
    public void setUserDetailsService(FssSecurityLoginUserDetailsService userDetailsService)
    {
        this.userDetailsService = userDetailsService; 
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException
    {
        String username = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String inputPassword = (String)authentication.getCredentials();
        System.out.println("inputPassword: " + inputPassword);
        if(inputPassword.equals(userDetails.getPassword()))
        {
            Object object = "123";
            return new UsernamePasswordAuthenticationToken(userDetails, 
                object, userDetails.getAuthorities()); 
        }
            
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        // TODO Auto-generated method stub
        return true;
    }
    
}
