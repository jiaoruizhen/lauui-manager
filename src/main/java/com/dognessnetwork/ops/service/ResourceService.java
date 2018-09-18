package com.dognessnetwork.ops.service;

import java.util.List;
import java.util.Map;

import com.dognessnetwork.ops.domain.Resource;
import com.dognessnetwork.ops.dto.Response;

public interface ResourceService {

	Response add(Resource resource);

	Response delete(String id);

	Response update(Resource resource);

	Map<String, Object> findAllNotIds(Integer page, Integer limit, String ids);

	List<Resource> getMenu() throws Exception;

	Map<String, Object> list(Integer page, Integer limit, Resource resource);

	List<Resource> findAll();

}
