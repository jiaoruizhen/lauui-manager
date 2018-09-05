package com.dognessnetwork.ops.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dognessnetwork.ops.aop.SystemControllerLog;
import com.dognessnetwork.ops.config.redis.HeaderStrategy;
import com.dognessnetwork.ops.domain.User;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.service.UserService;

@Controller
@RequestMapping(value="/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping(value="/test")
	@ResponseBody
	//@SystemControllerLog(descrption="测试信息",actionType="1")
	public Response test(){
		return Response.SUCCESS();
	}
	@PostMapping("/systemInit")
	@ResponseBody
	public Response systemInit(HttpSession session, HttpServletResponse response){
		String id = session.getId();
		response.addHeader(HeaderStrategy.getHeaderName(), id);
		return Response.SUCCESS();
	}
	
	@PostMapping(value="/regist")
	@ResponseBody
	public Response regist(User user){
		
		return userService.insertUsers(user);
	}
	
	@GetMapping(value="/index")
	public String index(HttpSession session){
		return "redirect:/index.html";
	}
	
	@PostMapping(value="/findAll")
	@ResponseBody
	public Response findAll(Integer page,String username){
		return userService.selectAllUsers(page,username);
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Response update(User user){
		return userService.updateUsers(user);
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public Response delete(String id){
		return userService.delete(id);
	}
	
	@PostMapping("/findById")
	@ResponseBody
	public Response findById(String id){
		return userService.findById(id);
	}
	
	@PostMapping("/changeRoles")
	@ResponseBody
	public Response changeRoles(String id,String role){
		return userService.changeRole(id,role);
	}
	
}
