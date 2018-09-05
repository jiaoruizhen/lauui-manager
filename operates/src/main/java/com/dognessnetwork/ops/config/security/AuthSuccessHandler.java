package com.dognessnetwork.ops.config.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.dognessnetwork.ops.common.Constant;
import com.dognessnetwork.ops.config.redis.HeaderStrategy;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.dto.ResponseHeader;
import com.dognessnetwork.ops.utils.JsonUtil;


@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(AuthSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8");
		HttpSession session = request.getSession();
		response.setHeader(HeaderStrategy.getHeaderName(), session.getId());
		
		String name = Constant.LOGIN_FAIL_COUNT;
		Integer count = (Integer) session.getAttribute(name);
		if (count != null) {
			session.setAttribute(name, null);
		}
		
		String appHeader = request.getHeader("x-app");
		if (appHeader != null && Integer.parseInt(appHeader) == 1) {
			session.setMaxInactiveInterval(60 * 60 * 24 * 30);
		}

		log.info("登录成功");
		Response result = new Response(new ResponseHeader("登录成功", 1000), authentication.getPrincipal());
		String json = JsonUtil.objectToJson(result);
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();
	}

}
