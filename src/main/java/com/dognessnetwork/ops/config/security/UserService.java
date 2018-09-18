package com.dognessnetwork.ops.config.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dognessnetwork.ops.domain.Resource;
import com.dognessnetwork.ops.domain.Resource.type;
import com.dognessnetwork.ops.domain.Role;
import com.dognessnetwork.ops.dto.User;
import com.dognessnetwork.ops.repository.UserRepository;
import com.dognessnetwork.ops.utils.UpdateUtil;

@Transactional
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		com.dognessnetwork.ops.domain.User petUser = userRepository.findByUsername(s);
		if (petUser == null) {
			throw new UsernameNotFoundException("用户未找到");
		}

		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		for (Role role : petUser.getRoles()) {
			if(!role.getEnable())
				break;
			for (Resource res : role.getResources()) {
				if (res.getType() != type.menu)
					authorities.add(new SimpleGrantedAuthority(res.getName()));
			}
		}

		User user = new User(petUser.getUsername(), petUser.getPassword(), authorities);
		UpdateUtil.copyNonNullProperties(petUser, user);
		return user;
	}
}