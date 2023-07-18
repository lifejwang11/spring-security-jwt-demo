package com.example.springsecuritydemo.security.filter;

import com.example.springsecuritydemo.security.tokens.JwtAuthenticatioToken;
import com.example.springsecuritydemo.utils.HttpUtils;
import com.example.springsecuritydemo.utils.JwtTokenUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;


public class JWTFileter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", "POST");

    public JWTFileter(){
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }
    protected JWTFileter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public JWTFileter(AuthenticationManager authenticationManager){
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER,authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        //写登陆验证的逻辑
        return new JwtAuthenticatioToken(1,1);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException {
        // 存储登录认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authResult);
        // 记住我服务
        getRememberMeServices().loginSuccess(request, response, authResult);
        // 触发事件监听器
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(
                    new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }
        // 生成并返回token给客户端，后续访问携带此token
        JwtAuthenticatioToken token = new JwtAuthenticatioToken(null, null,
                JwtTokenUtils.generateToken(authResult));
        HttpUtils.write(response, token, "Success");
    }
}
