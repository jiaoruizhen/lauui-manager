package com.dognessnetwork.ops.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.alibaba.fastjson.JSONObject;
import com.dognessnetwork.ops.dto.Response;

/**
 * @description 异常捕获类
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver {
	
	public static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, String[]> paramMap = request.getParameterMap();
		String version = request.getHeader("version")==null?"":" "+request.getHeader("version");

		if (ex.getClass().equals(MissingServletRequestParameterException.class)) {
			model = Response.INVALID_PARAMETER.toMap();
			logger.error("", ex);
		} else if (ex.getClass().equals(DataIntegrityViolationException.class)) {
			model = Response.DATA_TOOLONG.toMap();
			logger.error("", ex);
		} else if (ex.getClass().equals(UncategorizedSQLException.class)) {
			model = Response.INCORRECT_STRING.toMap();
			logger.error("", ex);
		}else if (ex.getClass().equals(InvalidSignException.class)) {
			model = Response.INVALID_SIGN.toMap();
			logger.error("", ex);
		} else if (ex.getClass().equals(DuplicateKeyException.class)) {
			model = Response.INSERT_DUPLICATE.toMap();
			logger.error("", ex);	
		} else {
			model = Response.INTERNAL_SERVER_ERROR.toMap();
			logger.error("", ex);
		}
		
		logger.error(request.getRequestURI()+" " + JSONObject.toJSONString(paramMap)+" "+JSONObject.toJSONString(model)+" "+version+" "+request.getRemoteHost());
		return new ModelAndView(new MappingJackson2JsonView(), model);
	}

}
