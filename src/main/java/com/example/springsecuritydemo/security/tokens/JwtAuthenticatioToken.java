package com.example.springsecuritydemo.security.tokens;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;


public class JwtAuthenticatioToken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;
	
	private String token;

	private String id;

	//用户登陆的类型
	private int loginType;


    public JwtAuthenticatioToken(Object principal, Object credentials){
        super(principal, credentials);
    }
    
    public JwtAuthenticatioToken(Object principal, Object credentials, String token){
    	super(principal, credentials);
    	this.token = token;
    }


    public JwtAuthenticatioToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String token) {
    	super(principal, credentials, authorities);
    	this.token = token;
    }
    
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}
}
