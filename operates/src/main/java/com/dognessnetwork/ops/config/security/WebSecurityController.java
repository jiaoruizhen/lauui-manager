package com.dognessnetwork.ops.config.security;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dognessnetwork.ops.dto.APIStatus;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.dto.ResponseHeader;
import com.dognessnetwork.ops.dto.User;

@Controller
public class WebSecurityController {
	
	// 获取登录用户示例url
	@GetMapping("/getLoginUser")
	@ResponseBody
	public Object getLoginUser(HttpSession session) throws Exception{
		User user  = SecurityUtils.getUser(session);
		return user;
	}
	
	@GetMapping("/redirectLogin")
	@ResponseBody
	public Response redirectLogin(){
		return new Response(new ResponseHeader(APIStatus.LOGIN_TIMEOUT));
	}
}
