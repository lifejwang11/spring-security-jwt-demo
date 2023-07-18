package com.example.springsecuritydemo.utils;

import com.example.springsecuritydemo.security.tokens.JwtAuthenticatioToken;
import java.math.BigInteger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtils {

	/**
	 * 获取令牌进行认证
	 * @param request
	 */
	public static void checkAuthentication(HttpServletRequest request) {
		Authentication authentication = null;
		// 获取令牌并根据令牌获取登录认证信息
		authentication =JwtTokenUtils.getAuthenticationeFromToken(request);
		if (authentication == null){
			return;
		}
		// 设置登录认证信息到上下文
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
	 * 获取当前用户名
	 * @return
	 */
	public static String getUsername() {
		String username = null;
		Authentication authentication = getAuthentication();
		if(authentication != null) {
			Object principal = authentication.getPrincipal();
			if(principal != null ) {
				username = (String) principal;
			}
		}
		return username;
	}

	//再次请求携带jwt获取id
	public static BigInteger getUserId() {
		BigInteger id = BigInteger.ZERO;
		if (getAuthentication() instanceof JwtAuthenticatioToken){
			JwtAuthenticatioToken authentication = (JwtAuthenticatioToken) getAuthentication();
			if(authentication != null) {
				Object principal = authentication.getPrincipal();
				if(principal != null ) {
					id = new BigInteger(authentication.getId()) ;
				}
			}
		}
		return id;
	}

	public static int getUserLoginType() {
		int loginType = 0;
		if (getAuthentication() instanceof JwtAuthenticatioToken){
			JwtAuthenticatioToken authentication = (JwtAuthenticatioToken) getAuthentication();
			if(authentication != null) {
				return authentication.getLoginType();
			}
		}
		return loginType;
	}

	/**
	 * 获取当前用户id
	 * @return
	 */
	public static BigInteger getUserId(Authentication authentication) {
		BigInteger username = null;
		if(authentication != null) {
			Object principal = authentication.getPrincipal();
			if(principal != null ) {
//				username = (BigInteger) principal;
				username =BigInteger.ONE;
			}
		}
		return username;
	}

	/**
	 * 获取当前用户id
	 * @return
	 */
//	public static int getUserLoginType(Authentication authentication) {
//		int loginType = 0;
//		if(authentication != null) {
//			Object principal = authentication.getPrincipal();
//			if(principal != null ) {
//				loginType = ((User) principal).getType();
//			}
//		}
//		return loginType;
//	}
	
	/**
	 * 获取用户名
	 * @return
	 */
	public static String getUsername(Authentication authentication) {
		String username = null;
		if(authentication != null) {
			Object principal = authentication.getPrincipal();
			if(principal != null) {
				username =  String.valueOf(principal) ;
//				username = (String) principal;
			}
		}
		return username;
	}
	
	/**
	 * 获取当前登录信息
	 * @return
	 */
	public static Authentication getAuthentication() {
		if(SecurityContextHolder.getContext() == null) {
			return null;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication;
	}
	
}
