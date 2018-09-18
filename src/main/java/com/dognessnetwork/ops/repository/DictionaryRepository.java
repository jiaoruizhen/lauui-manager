package com.dognessnetwork.ops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.dognessnetwork.ops.domain.Dictionary;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {
	
	
	List<Dictionary> findByPId(Long pid);
	
	@Modifying
	@Transactional
	@Query(value="delete from Dictionary e where e.id in (:ids) ")
	void deleteByIds(@Param("ids") List<Long> ids);
	
	Dictionary findByCodeAndLangType(String code, String langType);

	List<Dictionary> findByTypeAndLangType(String type, String langType);
	
	List<Dictionary> findByCode(String code);
	
	List<Dictionary> findByLangType(String langType);

	List<Dictionary> findByName(String name);

	Dictionary findByNameAndLangType(String name, String langType);
	
}
