package com.dognessnetwork.ops.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.dognessnetwork.ops.domain.Resource;
import com.dognessnetwork.ops.domain.Role;
import com.dognessnetwork.ops.domain.User;
import com.dognessnetwork.ops.dto.APIStatus;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.dto.ResponseHeader;
import com.dognessnetwork.ops.repository.RoleRepository;
import com.dognessnetwork.ops.repository.UserRepository;
import com.dognessnetwork.ops.service.UserService;
import com.dognessnetwork.ops.utils.MD5;
import com.dognessnetwork.ops.utils.StringToolsUtil;
import com.dognessnetwork.ops.utils.UpdateUtil;

@Transactional
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Response selectByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		User user = userRepository.findByUsername(username);
		if (StringUtils.isEmpty(user)) {
			return new Response(new ResponseHeader(APIStatus.USERNAME_NOT_EXIST));
		}
		return Response.SUCCESS(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (StringUtils.isEmpty(user)) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		List<GrantedAuthority> auths = new ArrayList<>();
		Set<Role> roles = user.getRoles();
		// 用于添加用户的权限。只要把用户权限添加到authorities 就万事大吉
		for (Role role : roles) {
			if (role != null && role.getName() != null) {
				Set<Resource> list = role.getResources();
				for (Resource resource : list) {
					if (resource != null && resource.getName() != null) {
						auths.add(new SimpleGrantedAuthority(resource.getName()));
						log.info(roles.toString());
					}
				}
			}
		}

		com.dognessnetwork.ops.dto.User user2 = new com.dognessnetwork.ops.dto.User(user.getUsername(),
				user.getPassword(), auths);
		UpdateUtil.copyNonNullProperties(user, user2);
		return user2;
	}

	@Override
	public Response updateUsers(User user) {
		if (StringUtils.isEmpty(user)) {
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		User user2 = userRepository.findOneById(user.getId());
		
		UpdateUtil.copyNonNullProperties(user, user2);
		
		userRepository.saveAndFlush(user2);
		return Response.SUCCESS();
	}

	@Override
	public Response insertUsers(User user) {
		if (StringUtils.isEmpty(user)) {
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		if (checkUser(user)) {
			return new Response(new ResponseHeader(APIStatus.USERNAME_IS_EXIST));
		}
		user.setEnable(false);
		user.setCreatTime(System.currentTimeMillis());
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		User user2 = userRepository.save(user);
		if (StringUtils.isEmpty(user2)) {
			return new Response(new ResponseHeader(APIStatus.FAILURE));
		}
		return Response.SUCCESS().setData(user2);
	}

	public boolean checkUser(User user) {
		User users = userRepository.findByUsername(user.getUsername());
		if (StringUtils.isEmpty(users)) {
			return false;
		} else {
			return true;
		}

	}

	@Override
	public Response delete(String ids) {
		List<Long> splitIds = StringToolsUtil.splitStrId(ids);
		userRepository.deleteByIds(splitIds);
		return Response.SUCCESS();
	}

	@Override
	public Response findById(String id) {
		if (StringUtils.isEmpty(id)) {
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		User user = userRepository.findOneById(Long.parseLong(id));
		if (StringUtils.isEmpty(user)) {
			return new Response(new ResponseHeader(APIStatus.USER_NOT_EXIST));
		}
		
		User cloneUser = null;
		try {
			cloneUser = (User) user.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		Set<Role> roles = cloneUser.getRoles();
		List<Role> findAll = roleRepository.findAll();
		for (Role re : findAll) {
			if(roles.contains(re)){
				re.setChecked(true);
			}
		}
		cloneUser.setRoles(new LinkedHashSet<>(findAll));
		return Response.SUCCESS().setData(cloneUser);
	}

	@Override
	public Response changeRole(String id, String role) {
		if (StringUtils.isEmpty(role)) {
			return new Response(new ResponseHeader(APIStatus.PARAM_IS_EMPTY));
		}
		User user = userRepository.findOneById(Long.parseLong(id));
		String[] roles = role.split(",");
		Set<Role> roles2 = new HashSet<>();
		for (String str : roles) {
			Role role2 = roleRepository.findOneById(Long.parseLong(str));
			roles2.add(role2);
		}
		user.setRoles(roles2);
		userRepository.saveAndFlush(user);
		return Response.SUCCESS();
	}

	@Override
	public Map<String, Object> findAll(Integer page, Integer limit, User user) {
		page = page == null ? 1 : page;
		limit = limit == null ? 10 : limit;

		Page<User> content = CommonService.getPageContent(page, limit, user, userRepository);

		Map<String, Object> res = new HashMap<>();
		res.put("code", "0");
		res.put("count", content.getTotalElements());
		res.put("data", content.getContent());
		return res;
	}
}
