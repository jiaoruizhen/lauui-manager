package com.dognessnetwork.ops;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dognessnetwork.ops.domain.Menu;
import com.dognessnetwork.ops.domain.Role;
import com.dognessnetwork.ops.repository.MenuRepository;
import com.dognessnetwork.ops.repository.RoleRepository;
import com.dognessnetwork.ops.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperatesApplicationTests {

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	public void contextLoads() {
		Menu m = new Menu();
		m.setName("xxx");
		
		menuRepository.save(m);
//		int page = 1;
//		int limit = 10;
//		Page<Role> findByIdNotIn = roleRepository.findByIdNotIn(Arrays.asList((long)1, (long)3), PageRequest.of(--page, limit));
//		System.out.println(findByIdNotIn);
	}
	
	
	@Test
	public void contextLoads2() {
		System.out.println(userRepository.findAll().size());
//		Menu findById = menuRepository.findById((long) 1).get();
//		findById.setName("zzz");
//		menuRepository.save(findById);
//		int page = 1;
//		int limit = 10;
//		Page<Role> findByIdNotIn = roleRepository.findByIdNotIn(Arrays.asList((long)1, (long)3), PageRequest.of(--page, limit));
//		System.out.println(findByIdNotIn);
	}

}
