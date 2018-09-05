package com.dognessnetwork.ops.service.Impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dognessnetwork.ops.domain.Resource;
import com.dognessnetwork.ops.domain.Role;
import com.dognessnetwork.ops.domain.User;
import com.dognessnetwork.ops.dto.APIStatus;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.dto.ResponseHeader;
import com.dognessnetwork.ops.repository.ResourceRepository;
import com.dognessnetwork.ops.repository.RoleRepository;
import com.dognessnetwork.ops.repository.UserRepository;
import com.dognessnetwork.ops.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository UserRepository;
	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public Response findAll(Integer pageSize) {
		if (StringUtils.isEmpty(pageSize)) {
			List<Role> list = roleRepository.findAll();
			return Response.SUCCESS().setData(list);
		}
		Page<Role> _page = roleRepository.findAll(PageRequest.of((pageSize - 1), 10));
		Map<String, Object> map = new HashMap<>();
		map.put("list", _page.getContent());
		map.put("total", _page.getTotalElements());
		return Response.SUCCESS().setData(map);
	}

	@Override
	public Response update(String id, String name, String detail, String resource, Integer enable) {
		if (StringUtils.isEmpty(id)) {
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		Role role2 = roleRepository.findOneById(Long.parseLong(id));
		if (!StringUtils.isEmpty(name)) {
			role2.setName(name);
		}
		if (!StringUtils.isEmpty(detail)) {
			role2.setDetail(detail);
		}
		if (!StringUtils.isEmpty(enable)) {
			if (enable == 1) {
				role2.setEnable(true);
			} else {
				role2.setEnable(false);
			}
		}
		if (!StringUtils.isEmpty(resource)) {
			String[] resources = resource.split(",");
			Set<Resource> resources2 = new HashSet<>();
			for (String str : resources) {
				Resource response = resourceRepository.findOneById(Long.parseLong(str));
				resources2.add(response);
			}
			role2.setResources(resources2);
		}
		Role role=roleRepository.saveAndFlush(role2);
		return Response.SUCCESS().setData(role);
	}

	@Override
	public Response delete(String id) {
		if (StringUtils.isEmpty(id)) {
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		Role role = roleRepository.findOneById(Long.parseLong(id));
		if (StringUtils.isEmpty(role)) {
			return new Response(new ResponseHeader(APIStatus.ROLE_NOT_EXIST));
		}
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		List<Resource> resources = resourceRepository.findByRoles(roles);
		if (resources.size() > 0) {
			for (Resource resource : resources) {
				resource.setRoles(null);
				resourceRepository.saveAndFlush(resource);
			}

		}
		List<User> users = UserRepository.findByRoles(roles);
		if (users.size() > 0) {
			for (User user : users) {
				user.setRoles(null);
				UserRepository.saveAndFlush(user);
			}

		}
		roleRepository.delete(role);
		return Response.SUCCESS();
	}

	@Override
	public Response add(Role role) {
		if (StringUtils.isEmpty(role)) {
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		Role role2 = roleRepository.findOneById(role.getId());
		if (StringUtils.isEmpty(role2)) {
			Role role3 = roleRepository.save(role);
			return Response.SUCCESS().setData(role3);
		} else {

			return Response.SUCCESS().setData(role2);
		}

	}

	@Override
	public Response findById(String id) {
		if (StringUtils.isEmpty(id)) {
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		Role role = roleRepository.findOneById(Long.parseLong(id));
		if (StringUtils.isEmpty(role)) {
			return new Response(new ResponseHeader(APIStatus.ROLE_NOT_EXIST));
		}
		return Response.SUCCESS().setData(role);
	}

	@Override
	public Response add(String name, String detail, String resource) {
		if(StringUtils.isEmpty(name)||StringUtils.isEmpty(detail)||StringUtils.isEmpty(resource)){
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		Role role=new Role();
		role.setName(name);
		String[] resources = resource.split(",");
		Set<Resource> resources2 = new HashSet<>();
		for (String str : resources) {
			Resource response = resourceRepository.findOneById(Long.parseLong(str));
			resources2.add(response);
		}
		System.out.println(resources2.toString());
		role.setResources(resources2);
		role.setDetail(detail);
		role.setEnable(true);
		role.setRoleIdentify(621);
		roleRepository.save(role);
		return Response.SUCCESS();
	}

}
