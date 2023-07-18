package com.example.springsecuritydemo.config;

import com.example.springsecuritydemo.security.filter.JWTFileter;
import com.example.springsecuritydemo.security.filter.JwtAuthenticationFilter;
import com.example.springsecuritydemo.security.handler.CasAuthenticationFailHandler;
import java.util.UUID;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.Cas30ProxyTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.cas.userdetails.GrantedAuthorityFromAssertionAttributesUserDetailsService;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
public class WebSecurity  {

    @Autowired
    JWTFileter fileter;

    @Autowired
    @Lazy
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    CasAuthenticationFilter casAuthenticationFilter;

    @Autowired
    CasAuthenticationEntryPoint casAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) ->
                {
                    try {
                        authz.shouldFilterAllDispatcherTypes(true).and().cors()
                                .and().csrf().disable()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .authorizeHttpRequests().antMatchers("/login","/login/cas").permitAll()
                                // 跨域预检请求
                                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .anyRequest().authenticated();
                        authz.and().addFilterBefore(fileter, UsernamePasswordAuthenticationFilter.class);
                        authz.and().addFilterBefore(casAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                        authz.and().addFilterAfter(jwtAuthenticationFilter,JWTFileter.class);
                        authz.and().exceptionHandling().defaultAuthenticationEntryPointFor(new Http403ForbiddenEntryPoint(),new AntPathRequestMatcher("/login", "POST"));
//                        authz.and().exceptionHandling().defaultAuthenticationEntryPointFor(casAuthenticationEntryPoint,new AntPathRequestMatcher("/login/cas", "GET"));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                );
        return http.build();
    }

    @Bean
    public ServiceProperties serviceProperties(){
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService("https://127.0.0.1/login/cas");
        return serviceProperties;
    }

    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint(ServiceProperties serviceProperties){
        CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
        casAuthenticationEntryPoint.setLoginUrl("https://sso.ecust.edu.cn/authserver/login");
        casAuthenticationEntryPoint.setServiceProperties(serviceProperties);
        return casAuthenticationEntryPoint;
    }


    @Bean
    public CasAuthenticationProvider casAuthenticationProvider(ServiceProperties serviceProperties){
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setServiceProperties(serviceProperties);
        casAuthenticationProvider.setTicketValidator(new Cas20ServiceTicketValidator("https://sso.ecust.edu.cn/authserver"));
        String []list = new String[]{"username"};
        casAuthenticationProvider.setAuthenticationUserDetailsService(new GrantedAuthorityFromAssertionAttributesUserDetailsService(list));
        casAuthenticationProvider.setKey(UUID.randomUUID().toString());
        return casAuthenticationProvider;
    }




    @Bean
    public ProviderManager providerManager(CasAuthenticationProvider casAuthenticationProvider){
        ProviderManager providerManager = new ProviderManager(casAuthenticationProvider);
        return providerManager;
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(ProviderManager providerManager){
        return new JwtAuthenticationFilter(providerManager);
    }

    @Bean
    public CasAuthenticationFilter casAuthenticationFilter(ProviderManager providerManager){
        CasAuthenticationFilter casAuthenticationFilter1=new CasAuthenticationFilter();
        casAuthenticationFilter1.setAuthenticationManager(providerManager);
        casAuthenticationFilter1.setAuthenticationFailureHandler(new CasAuthenticationFailHandler());
        return casAuthenticationFilter1;
    }

    @Bean
    public JWTFileter jwtFileter(ProviderManager providerManager){
        return new JWTFileter(providerManager);
    }
}
