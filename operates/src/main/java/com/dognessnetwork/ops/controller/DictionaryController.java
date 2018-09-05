package com.dognessnetwork.ops.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dognessnetwork.ops.domain.Dictionary;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.service.DictionaryService;

@RestController
public class DictionaryController {

	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping("/save")
	public Object save(Dictionary vo) {
		return dictionaryService.save(vo);
	}

	@RequestMapping("/all")
	public Object all(Dictionary dic) {
		return dictionaryService.all(dic);
	}
	
	@RequestMapping("/count")
	public Object count() {
		return dictionaryService.count();
	}

	@RequestMapping("/getById")
	public Object getById(Long id) {
		return dictionaryService.findById(id);
	}

	@RequestMapping("/del")
	public Object del(Long id) {
		dictionaryService.del(id);
		return true;
	}

	@RequestMapping("/delBatch")
	public Object delBatch(String id) {
		dictionaryService.delBatch(id);
		return true;
	}
	
	@RequestMapping("/import")
	public Object ImportDictiontry(MultipartFile file) {
		return dictionaryService.ImportDictiontry(file);
	}
	
	@RequestMapping("/getByCode")
	public Object getByCode(String code, String langType){
		Dictionary dic = dictionaryService.getByCode(code, langType);
		return Response.SUCCESS(dic);
	}
	
	@RequestMapping("/getByType")
	public Object getByType(String type, String langType){
		List<Dictionary> dic = dictionaryService.getByType(type, langType);
		return Response.SUCCESS(dic);
	}

}
