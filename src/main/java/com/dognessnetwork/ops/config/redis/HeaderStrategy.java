//package com.dognessnetwork.ops.config.redis;
//
//import java.util.Collections;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
//import org.springframework.stereotype.Component;
//
//@Component
//public class HeaderStrategy extends HeaderHttpSessionIdResolver {
//	private Logger log = LoggerFactory.getLogger(getClass());
//	private static String headerName = "x-auth-token";
//	
//	public HeaderStrategy() {
//		super(HeaderStrategy.headerName);
//	}
//	
//	public static String getHeaderName() {
//		return headerName;
//	}
//	
//	@Override
//	public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
//		log.debug("产生了新的sessionId: {}", sessionId);
//	}
//	
//	@Override
//	public List<String> resolveSessionIds(HttpServletRequest request) {
//		List<String> resolveSessionIds = super.resolveSessionIds(request);
//		if(resolveSessionIds.size() == 0){
//			String headerValue = request.getParameter(HeaderStrategy.headerName);
//			return headerValue != null ? Collections.singletonList(headerValue)
//					: Collections.emptyList();
//		}
//		return resolveSessionIds;
//	}
//}
