package com.example.springsecuritydemo.security.provider;

import com.example.springsecuritydemo.security.tokens.JwtAuthenticatioToken;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JWTProvider implements AuthenticationProvider, InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticatioToken.class.isAssignableFrom(authentication);
    }
}
