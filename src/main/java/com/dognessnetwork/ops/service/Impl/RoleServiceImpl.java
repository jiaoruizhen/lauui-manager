package com.dognessnetwork.ops.service.Impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.dognessnetwork.ops.utils.StringToolsUtil;
import com.dognessnetwork.ops.utils.UpdateUtil;

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
	public Map<String, Object> findAll(Integer page, Integer limit, Role role) {
		page = page == null ? 1 : page;
		limit = limit == null ? 10 : limit;

		Page<Role> content = CommonService.getPageContent(page, limit, role, roleRepository);

		Map<String, Object> res = new HashMap<>();
		res.put("code", "0");
		res.put("count", content.getTotalElements());
		res.put("data", content.getContent());
		return res;
	}

	@Override
	public Role update(Role role) {
		Role role2 = roleRepository.findOneById(role.getId());
		UpdateUtil.copyNonNullProperties(role, role2);
		roleRepository.saveAndFlush(role2);
		return role2;
	}
	
	@Override
	public Role updatePower(Role role) {
		Role role2 = roleRepository.findOneById(role.getId());
		UpdateUtil.copyNonNullProperties(role, role2);
		if(role.getResources() == null){
			role2.setResources(null);
		}
		roleRepository.saveAndFlush(role2);
		return role2;
	}

	@Override
	public Response delete(String ids) {
		List<Long> splitIds = StringToolsUtil.splitStrId(ids);
		roleRepository.deleteByIds(splitIds);
		return Response.SUCCESS();
	}

	@Override
	public void add(Role role) {
		role.setEnable(true);
		roleRepository.save(role);
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
		
		Role cloneRole = null;
		try {
			cloneRole = (Role) role.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		Set<Resource> resources = cloneRole.getResources();
		List<Resource> findAll = resourceRepository.findAll();
		for (Resource res : findAll) {
			if(resources.contains(res)){
				res.setChecked(true);
			}
		}
		cloneRole.setResources(new LinkedHashSet<>(findAll));
		return Response.SUCCESS().setData(cloneRole);
	}
	
	
	@Override
	public Response add(String name, String detail, String resource) {
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(detail) || StringUtils.isEmpty(resource)) {
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		Role role = new Role();
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
