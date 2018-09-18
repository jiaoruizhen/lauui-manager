package com.dognessnetwork.ops.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dognessnetwork.ops.domain.Role;


public interface RoleRepository extends JpaRepository<Role, Long>{

	@Query(value="select * from role u  order by u.id  LIMIT :page,10 ", nativeQuery = true)
	List<Role> findAll(@Param("page")Integer page);

	Role findOneById(Long id);
	
	@Modifying
	@Query(value="delete from Role e where e.id in (:ids) ")
	int deleteByIds(@Param("ids") List<Long> ids);
}
