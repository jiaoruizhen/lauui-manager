package com.dognessnetwork.ops.service.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dognessnetwork.ops.config.security.SecurityUtils;
import com.dognessnetwork.ops.domain.Resource;
import com.dognessnetwork.ops.domain.Resource.type;
import com.dognessnetwork.ops.domain.Role;
import com.dognessnetwork.ops.dto.APIStatus;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.dto.ResponseHeader;
import com.dognessnetwork.ops.dto.User;
import com.dognessnetwork.ops.repository.ResourceRepository;
import com.dognessnetwork.ops.repository.RoleRepository;
import com.dognessnetwork.ops.repository.UserRepository;
import com.dognessnetwork.ops.service.ResourceService;
import com.dognessnetwork.ops.utils.StringToolsUtil;
import com.dognessnetwork.ops.utils.UpdateUtil;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceRepository resourceRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Response add(Resource resource) {
		if (StringUtils.isEmpty(resource)) {
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		Resource resource2 = resourceRepository.findByDetail(resource.getDetail());
		if (StringUtils.isEmpty(resource2)) {
			resource.setName(resource.getName().toUpperCase());
			
			if(resource.getType()==type.url){
				resource.setPid((long) -1);
			}else if(resource.getPid() == null){
				resource.setPid((long) 0);
			}
			
			resourceRepository.save(resource);
			return Response.SUCCESS();
		}
		return update(resource2);
	}

	@Override
	public Map<String, Object> list(Integer page, Integer limit, Resource resource) {
		page = page == null ? 1 : page;
		limit = limit == null ? 10 : limit;

		Page<Resource> content = CommonService.getPageContent(page, limit, resource, resourceRepository);

		Map<String, Object> res = new HashMap<>();
		res.put("code", "0");
		res.put("count", content.getTotalElements());
		res.put("data", content.getContent());
		return res;
	}

	@Override
	public Map<String, Object> findAllNotIds(Integer page, Integer limit, String ids) {
		page = page == null ? 1 : page;
		limit = limit == null ? 10 : limit;
		Long[] idArr = getLongArr(ids);
		Page<Resource> content = null;

		if (idArr != null && idArr.length != 0) {
			content = resourceRepository.findByIdNotIn(Arrays.asList(idArr), PageRequest.of(--page, limit));
		} else {
			content = resourceRepository.findAll(PageRequest.of(--page, limit));
		}

		Map<String, Object> res = new HashMap<>();
		res.put("code", "0");
		res.put("count", content.getTotalElements());
		res.put("data", content.getContent());
		return res;
	}

	public static Long[] getLongArr(String ids) {
		if (StringUtils.isEmpty(ids))
			return null;

		String[] split = ids.split(",");
		Long[] arr = new Long[split.length];
		for (int i = 0; i < split.length; i++) {
			arr[i] = Long.valueOf(split[i]);
		}
		return arr;
	}

	@Override
	public Response delete(String ids) {
		if (StringUtils.isEmpty(ids)) {
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		
		List<Long> splitStrId = StringToolsUtil.splitStrId(ids);
		
		for (Long id : splitStrId) {
			Resource resource = resourceRepository.findOneById(id);
			Set<Role> roles = resource.getRoles();
			if (!roles.isEmpty()) {
				for (Role role : roles) {
					role.getResources().remove(resource);
				}
				roleRepository.saveAll(roles);
			}
			resourceRepository.delete(resource);
		}

		return Response.SUCCESS();
	}

	@Override
	public Response update(Resource resource) {
		if (StringUtils.isEmpty(resource)) {
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		Resource resource2 = resourceRepository.findOneById(resource.getId());

		resource.setName(StringUtils.isEmpty(resource.getName()) ? "" : resource.getName().toUpperCase());
		if(resource.getType() == type.menu){
			resource.setPid((long) 0);
		}
		
		UpdateUtil.copyNonNullProperties(resource, resource2);

		resourceRepository.saveAndFlush(resource2);
		return Response.SUCCESS();
	}

	@Override
	public List<Resource> getMenu() throws Exception {
		User user = SecurityUtils.getUser();
		com.dognessnetwork.ops.domain.User curUser = userRepository.findById(user.getId()).get();

		List<Resource> res = new ArrayList<>();
		Set<Role> roles = curUser.getRoles();
		for (Role role : roles) {
			for (Resource resource : role.getResources()) {
				if (resource.getType() != type.url) {
					res.add(resource);
				}
			}
		}

		return buildTree(res, (long) 0);
	}

	private List<Resource> buildTree(List<Resource> all, Long pid) {
		List<Resource> collect = all.stream().filter(e -> e.getPid().equals(pid)).collect(Collectors.toList());
		for (Resource resource : collect) {
			List<Resource> childs = buildTree(all, resource.getId());

			if (!childs.isEmpty()) {
				resource.setChildrens(childs);
			}
		}
		return collect;
	}

	@Override
	public List<Resource> findAll() {
		List<Resource> findAll = resourceRepository.findAll();
		return findAll;
	}

}
