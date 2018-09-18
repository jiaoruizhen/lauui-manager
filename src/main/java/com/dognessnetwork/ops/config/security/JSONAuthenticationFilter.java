package com.dognessnetwork.ops.config.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 登录时根据json数据取出登录参数
 * @author Administrator
 *
 */


public class JSONAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@SuppressWarnings("unchecked")
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
				|| request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {

			ObjectMapper mapper = new ObjectMapper();
			UsernamePasswordAuthenticationToken authRequest = null;
			try{
				InputStream is = request.getInputStream();
				Map<String, String> user = mapper.readValue(is, Map.class);
				
				String username = user.get("username");
				String password = user.get("password");
				if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
					throw new UsernameNotFoundException("用户名密码都不能为空");
				}
				authRequest = new UsernamePasswordAuthenticationToken(username, password);
			} catch (IOException e) {
				e.printStackTrace();
				authRequest = new UsernamePasswordAuthenticationToken("", "");
			}
			setDetails(request, authRequest);
			return this.getAuthenticationManager().authenticate(authRequest);
		}
		else {
			return super.attemptAuthentication(request, response);
		}
	}
}
