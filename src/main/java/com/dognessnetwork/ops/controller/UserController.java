package com.dognessnetwork.ops.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dognessnetwork.ops.domain.User;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.service.UserService;

@Controller
@RequestMapping(value="/users")
public class UserController{
	@Autowired
	private UserService userService;
	
	@PostMapping(value="/test")
	@ResponseBody
	//@SystemControllerLog(descrption="测试信息",actionType="1")
	public Response test(){
		return Response.SUCCESS();
	}
//	@PostMapping("/systemInit")
//	@ResponseBody
//	public Response systemInit(HttpSession session, HttpServletResponse response){
//		String id = session.getId();
//		response.addHeader(HeaderStrategy.getHeaderName(), id);
//		return Response.SUCCESS();
//	}
	
	@PostMapping(value="/regist")
	@ResponseBody
	public Response regist(@Valid User user, BindingResult result){
		if(result.hasErrors()){
			FieldError fieldError = result.getFieldError();
			return Response.ERROR(fieldError.getDefaultMessage(), 5000);
		}
		return userService.insertUsers(user);
	}
	
	@GetMapping(value="/findAll")
	@ResponseBody
	public Map<String, Object> findAll(Integer page, Integer limit, User user){
		return userService.findAll(page, limit, user);
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Response update(User user){
		return userService.updateUsers(user);
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public Response delete(String ids){
		return userService.delete(ids);
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
