package com.dognessnetwork.ops.service;

import com.dognessnetwork.ops.domain.Role;
import com.dognessnetwork.ops.dto.Response;

public interface RoleService {


	Response delete(String id);

	Response add(Role role);

	Response findAll(Integer pageSize);

	Response findById(String id);

	Response update(String id,String name, String detail, String resource, Integer enable);

	Response add( String name, String detail,String resource);

}
