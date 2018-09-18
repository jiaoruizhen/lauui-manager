package com.dognessnetwork.ops.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dognessnetwork.ops.domain.User;


public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
	
	User findByName(String name);
	
	@Query(value="select * from user u  LIMIT :page,10", nativeQuery = true)
	List<User> findAll(@Param("page")Integer page);
	
	@Query(value="select count(*) from user ", nativeQuery = true)
	Integer countAll();

	List<User> findByRoles(Set<?> role);

	User findOneById(long parseLong);
	
	@Modifying
	@Query(value="delete from User e where e.id in (:ids) ")
	int deleteByIds(@Param("ids") List<Long> ids);
	
}
