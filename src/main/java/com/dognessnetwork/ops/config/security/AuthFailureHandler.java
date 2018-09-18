package com.dognessnetwork.ops.config.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.dognessnetwork.ops.common.Constant;
import com.dognessnetwork.ops.dto.APIStatus;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.dto.ResponseHeader;
import com.dognessnetwork.ops.exception.ValidateCodeException;
import com.dognessnetwork.ops.utils.JsonUtil;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

	private static final Logger log = LoggerFactory.getLogger(AuthFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String exceptionMsg = exception.getMessage();
		Integer code = 3000;

		HttpSession session = req.getSession();
		String name = Constant.LOGIN_FAIL_COUNT;
		Integer count = (Integer) session.getAttribute(name);

		if (exception instanceof ValidateCodeException) {
			ValidateCodeException e = (ValidateCodeException) exception;
			code = e.getCode();
		}else if (exception instanceof LockedException) {
			exceptionMsg = APIStatus.ERROR_3026.getMessage();
			code = APIStatus.ERROR_3026.getStatus();
		}else if (exception instanceof BadCredentialsException) {
			exceptionMsg = "ERROR Incorrect username or password";
			if (checkFailCount(req, count, session, name)) {
				code = 3001;
			}
		}
		
		Map<String, Object> hasImgCode = new HashMap<String, Object>();
		hasImgCode.put("hasImgCode", true);
		if (!checkFailCount(req, count, session, name)) {
			hasImgCode = null;
		}

		log.info("登录失败");
		
		response.setContentType("application/json;charset=UTF-8");
		Response result = new Response(new ResponseHeader(exceptionMsg, code), hasImgCode);
		response.getWriter().write(JsonUtil.objectToJson(result));
	}

	private boolean checkFailCount(HttpServletRequest req, Integer count, HttpSession session, String name) {
		if (req.getRequestURI().equals(Constant.DEFAULT_LOGIN_URL)) {
			if (count == null) {
				session.setAttribute(name, 1);
			} else {
				session.setAttribute(name, ++count);
				if (count >= 5) {
					return true;
				}
			}
		}
		return false;
	}

}
