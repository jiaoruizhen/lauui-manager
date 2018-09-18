package com.dognessnetwork.ops.service;

import java.util.Map;

import com.dognessnetwork.ops.domain.Resource;
import com.dognessnetwork.ops.domain.Role;
import com.dognessnetwork.ops.dto.Response;

public interface RoleService {


	Response delete(String id);

	void add(Role role);

	Response findById(String id);

	Response add( String name, String detail,String resource);

	Role update(Role role);

	Map<String, Object> findAll(Integer page, Integer limit, Role role);

	Role updatePower(Role role);
}
