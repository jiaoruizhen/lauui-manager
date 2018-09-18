package com.dognessnetwork.ops.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dognessnetwork.ops.config.security.DynamicSecurityMetadataSource;
import com.dognessnetwork.ops.domain.Resource;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.service.ResourceService;

@Controller
@RequestMapping(value="/resources")
public class ResourseController {
	@Autowired
	private ResourceService resourceService;
	
	@PostMapping("/add")
	@ResponseBody
	public Response add(Resource resource){
		Response res = resourceService.add(resource);
		DynamicSecurityMetadataSource.LoadUrlRoleMap();
		return res;
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Response update(Resource resource){
		Response res = resourceService.update(resource);
		DynamicSecurityMetadataSource.LoadUrlRoleMap();
		return res;
	}
	@PostMapping("/delete")
	@ResponseBody
	public Response delete(String ids){
		Response res = resourceService.delete(ids);
		DynamicSecurityMetadataSource.LoadUrlRoleMap();
		return res;
	}
	
	@GetMapping("/findAll")
	@ResponseBody
	public Response findAll(){
		List<Resource> findAll = resourceService.findAll();
		return Response.SUCCESS(findAll);
	}
	
	@GetMapping("/list")
	@ResponseBody
	public Map<String, Object> list(Integer page, Integer limit, Resource resource){
		return resourceService.list(page, limit, resource);
	}
	
	@GetMapping("/findAllNotIds")
	@ResponseBody
	public Map<String, Object> findAllNotIds(Integer page, Integer limit, String ids){
		return resourceService.findAllNotIds(page, limit, ids);
	}
	
	@GetMapping("/getMenu")
	@ResponseBody
	public Response getMenu() throws Exception{
		List<Resource> res = resourceService.getMenu();
		return Response.SUCCESS(res);
	}
}
