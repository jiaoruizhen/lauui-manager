package com.dognessnetwork.ops.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dognessnetwork.ops.domain.Resource;
import com.dognessnetwork.ops.domain.Role;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.service.RoleService;

@Controller
@RequestMapping(value="/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping(value="/findAll")
	@ResponseBody
	public Map<String, Object> findAll(Integer page, Integer limit, Role role){
		return roleService.findAll(page, limit, role);
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Response update(Role role){
		return Response.SUCCESS(roleService.update(role));
	}
	
	@PostMapping("/updatePower")
	@ResponseBody
	public Response updatePower(Role role){
		roleService.updatePower(role);
		return Response.SUCCESS();
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public Response delete(String ids){
		return roleService.delete(ids);
	}
	
	@PostMapping("/add")
	@ResponseBody
	public Response add(Role role){
		roleService.add(role);
		return Response.SUCCESS();
	}
	
	@PostMapping("findById")
	@ResponseBody
	public Response findById(String id){
		return roleService.findById(id);
	}
}
