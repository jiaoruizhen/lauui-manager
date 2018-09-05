package com.dognessnetwork.ops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dognessnetwork.ops.domain.ReviewMessage;
import com.dognessnetwork.ops.domain.SIMCardInfo;
import com.dognessnetwork.ops.service.SIMCardService;

@Controller
@RequestMapping("/sim")
public class SimController {
	private @Autowired SIMCardService sIMCardService;
	
	@GetMapping("/sim_query_info")
	@ResponseBody
	public Object queryAllSIMCardInfo(Integer page, Integer limit, SIMCardInfo card) {
		return sIMCardService.queryAllSIMCardInfo(page, limit, card);
	}
	
	@GetMapping("/getById")
	@ResponseBody
	public Object getById(Long id) {
		return sIMCardService.getById(id);
	}
	
	@PostMapping("/audit")
	@ResponseBody
	public Object audit(String id, String time) {
		sIMCardService.audit(id, time);
		return true;
	}
	
	@GetMapping("/audit/list")
	@ResponseBody
	public Object auditList(Integer page, Integer limit, ReviewMessage mes) {
		return sIMCardService.auditList(page, limit, mes);
	}
	
	@PostMapping("/audit/oper")
	@ResponseBody
	public Object auditOper(String id, Integer auditStatus) {
		sIMCardService.auditOper(id, auditStatus);
		return true;
	}
	
	@RequestMapping(value="/sim_query_aoyiCard",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin 
	public Object queryAOYISIMCardInfoByMsisdnOrIMEI(String card,String imei){
		return sIMCardService.queryAOYISIMCardInfoByMsisdnOrIMEI(card, imei);
	}
}
