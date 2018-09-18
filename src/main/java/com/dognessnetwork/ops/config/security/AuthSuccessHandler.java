package com.dognessnetwork.ops.config.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

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

		log.info("登录成功");
		Response result = new Response(new ResponseHeader("登录成功", 1000), authentication.getPrincipal());
		String json = JsonUtil.objectToJson(result);
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();
	}

}
