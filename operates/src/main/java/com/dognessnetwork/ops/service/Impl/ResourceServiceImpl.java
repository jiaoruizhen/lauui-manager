package com.dognessnetwork.ops.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dognessnetwork.ops.domain.Resource;
import com.dognessnetwork.ops.dto.APIStatus;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.dto.ResponseHeader;
import com.dognessnetwork.ops.repository.ResourceRepository;
import com.dognessnetwork.ops.service.ResourceService;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {
	
	@Autowired
	private ResourceRepository resourceRepository;
	@Override
	public Response add(Resource resource) {
		if(StringUtils.isEmpty(resource)){
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		Resource resource2=resourceRepository.findByDetail(resource.getDetail());
		if(StringUtils.isEmpty(resource2)){
			resourceRepository.save(resource);
			return Response.SUCCESS();
		}
		return update(resource2);
	}

	@Override
	public Response findAll(Integer page) {
		if(StringUtils.isEmpty(page)){
			List<Resource>list=resourceRepository.findAll();
			return Response.SUCCESS().setData(list);
		}
		Page<Resource> _page = resourceRepository.findAll(PageRequest.of((page - 1), 10));
		Map<String, Object>map=new HashMap<>();
		map.put("list", _page.getContent());
		map.put("total", _page.getTotalElements());
		return Response.SUCCESS().setData(map);
	}

	@Override
	public Response delete(String id) {
		if(StringUtils.isEmpty(id)){
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		Resource resource=resourceRepository.findOneById(Long.parseLong(id));
		if(StringUtils.isEmpty(resource)){
			return new Response(new ResponseHeader(APIStatus.RESOURCE_NOT_EXIST));
		}
		resourceRepository.delete(resource);
		return Response.SUCCESS();
	}

	@Override
	public Response update(Resource resource) {
		if(StringUtils.isEmpty(resource)){
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		Resource resource2=resourceRepository.findOneById(resource.getId());
		if(!StringUtils.isEmpty(resource.getName())){
			resource2.setName(resource.getName());
		}
		if(!StringUtils.isEmpty(resource.getDetail())){
			resource2.setDetail(resource.getDetail());
		}
		if(!StringUtils.isEmpty(resource.getUrl())){
			resource2.setUrl(resource2.getUrl());
		}
		if(!StringUtils.isEmpty(resource.getType())){
			resource2.setType(resource.getType());
		}
		resourceRepository.saveAndFlush(resource2);
		return Response.SUCCESS();
	}

}
