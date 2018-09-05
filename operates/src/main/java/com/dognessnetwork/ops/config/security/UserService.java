package com.dognessnetwork.ops.config.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dognessnetwork.ops.dto.User;
import com.dognessnetwork.ops.repository.UserRepository;
import com.dognessnetwork.ops.utils.UpdateUtil;


@Service
public class UserService implements UserDetailsService {

	@Autowired 
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		com.dognessnetwork.ops.domain.User petUser = userRepository.findByUsername(s);
		User user = null;
		
		if (petUser == null) {
			throw new UsernameNotFoundException("用户未找到");
		}else if(petUser.isEnable()){
			user = new User(petUser.getUsername(), petUser.getPassword(), true, true, true, false, new ArrayList<GrantedAuthority>());
		}
		if(user == null){
			user = new User(petUser.getUsername(), petUser.getPassword(), new ArrayList<GrantedAuthority>());
		}
		UpdateUtil.copyNonNullProperties(petUser, user);
		return user;
	}
}