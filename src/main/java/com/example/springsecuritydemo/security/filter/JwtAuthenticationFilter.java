package com.example.springsecuritydemo.security.filter;

import com.example.springsecuritydemo.utils.HttpUtils;
import com.example.springsecuritydemo.utils.SecurityUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;


public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
	


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	// 获取token, 并检查登录状态
//        try {
//            SecurityUtils.checkAuthentication(request);
//        } catch (Exception e) {
//            HttpUtils.jwt(response,"请重新登陆！");
//            return;
//        }
        chain.doFilter(request, response);
    }
    
}