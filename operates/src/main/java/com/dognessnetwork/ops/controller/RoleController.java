package com.dognessnetwork.ops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dognessnetwork.ops.domain.Role;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.service.RoleService;

@Controller
@RequestMapping(value="/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@PostMapping(value="/findAll")
	@ResponseBody
	public Response findAll(Integer page){
		return roleService.findAll(page);
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Response update(String id,String name,String detail,String resource,Integer enable){
		return roleService.update(id,name,detail,resource,enable);
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public Response delete(String id){
		return roleService.delete(id);
	}
	
	@PostMapping("/add")
	@ResponseBody
	public Response add(String name,String detail,String resource){
		return roleService.add(name,detail,resource);
	}
	
	@PostMapping("findById")
	@ResponseBody
	public Response findById(String id){
		return roleService.findById(id);
	}
}
