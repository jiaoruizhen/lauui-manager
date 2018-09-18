package com.dognessnetwork.ops.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dognessnetwork.ops.domain.Device;
import com.dognessnetwork.ops.service.DeviceService;

@RequestMapping("/device")
@RestController
public class DeviceController {
	
	@Autowired
	private DeviceService deviceService;
	
	@GetMapping("/all")
	public Object all(Integer page, Integer limit, Device dev){
		Page<Device> content = deviceService.all(page, limit, dev);
		Map<String, Object> res = new HashMap<>();
		res.put("code", "0");
		res.put("count", content.getTotalElements());
		res.put("data", content.getContent());
		return res;
	}
	
	@GetMapping("/getById")
	@ResponseBody
	public Object getById(Long id) {
		return deviceService.getById(id);
	}
}
