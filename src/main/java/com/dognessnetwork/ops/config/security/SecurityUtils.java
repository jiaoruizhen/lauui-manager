package com.dognessnetwork.ops.config.security;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dognessnetwork.ops.dto.User;


public class SecurityUtils {
	public static User getUser() throws Exception{
		User user = null;
		try {
			SecurityContext a = (SecurityContext) getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			user = (User) a.getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new Exception("没有登录, 请登录");
		}
		return user;
	}
	
	private static HttpSession getSession() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpSession session = sra.getRequest().getSession();
		return session;
	}
}
