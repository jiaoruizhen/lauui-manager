package com.dognessnetwork.ops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		System.out.println(resource);
		return resourceService.add(resource);
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Response update(Resource resource){
		return resourceService.update(resource);
	}
	@PostMapping("/delete")
	@ResponseBody
	public Response delete(String id){
		return resourceService.delete(id);
	}
	
	@PostMapping("/findAll")
	@ResponseBody
	public Response findAll(Integer page){
		return resourceService.findAll(page);
	}
	
}
