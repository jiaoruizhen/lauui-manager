package com.dognessnetwork.ops.service;

import com.dognessnetwork.ops.domain.Resource;
import com.dognessnetwork.ops.dto.Response;

public interface ResourceService {

	Response add(Resource resource);

	Response findAll(Integer page);

	Response delete(String id);

	Response update(Resource resource);

}
