package com.dognessnetwork.ops.config;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;


public class OverallFilter extends OncePerRequestFilter {
	private Logger log = (Logger) LoggerFactory.getLogger(getClass());
	private AntPathMatcher pathMatcher = new AntPathMatcher();
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		openCross(request, response);
		filterChain.doFilter(request, response);
//		chechUrl(request, response, filterChain, request, response);
		try {
			recordLog(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	private void chechUrl(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain,
//			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		String uri = request.getRequestURI();
//		if(AppConstant.getOnlinePattern() && isCheckUrl(uri)){
//			String id = request.getSession().getId();
//			String sessionName = HeaderStrategy.getHeaderName();
//			String paramId = request.getParameter(sessionName);
//			String headerId = request.getHeader(sessionName);
//			if((!StringUtils.isEmpty(paramId) && id.equals(paramId)) || (!StringUtils.isEmpty(headerId) && id.equals(headerId))){
//		        filterChain.doFilter(servletRequest, servletResponse);
//			}else{
//				Response result = new Response(new ResponseHeader(APIStatus.ERROR_3019));
//				String json = JsonUtil.objectToJson(result);
//				PrintWriter out = response.getWriter();
//				out.write(json);
//				out.flush();
//				out.close();
//			}
//		}else{
//			filterChain.doFilter(servletRequest, servletResponse);
//		}
//	}
	
//	public boolean isCheckUrl(String url){
//		for (String u : AppConstant.getFilterCheckList()) {
//			if(pathMatcher.match(u, url)){
//				return false;
//			}
//		}
//		return true;
//	}

	private void recordLog(HttpServletRequest request) throws Exception {
		log.info("------------------------------------Request log---------------------------------------------");
		log.info("| url: " + request.getRequestURI());
		log.info("| ip: " + request.getRemoteHost());
		
//		User user=SecurityUtils.getUser(request.getSession());
//		log.info("| user:"+user.getUsername());
		
		Map<String, String[]> parameterMap = request.getParameterMap();
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String[]> entry : entrySet) {
			sb.append(entry.getKey() + ":" + entry.getValue()[0] + " | ");
		}
		log.info("| Params: " + sb.toString());
	}

	private void openCross(HttpServletRequest request, HttpServletResponse response) {
		String url = request.getHeader("Origin");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", url);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Expose-Headers", "x-auth-token");
	}
}