package com.dognessnetwork.ops.repository;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dognessnetwork.ops.domain.Resource;


public interface ResourceRepository extends JpaRepository<Resource, Long>{

	List<Resource> findByRoles(Set role);

	Resource findByDetail(String detail);

	Resource findOneById(long parseLong);

}
