package com.dognessnetwork.ops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dognessnetwork.ops.domain.ReviewMessage2;

public interface RenewaMessageRepository2 extends JpaRepository<ReviewMessage2, Long> {

	@Query(value="SELECT * from review_message where user_id=:uid ORDER BY `status` LIMIT :page,10",nativeQuery=true)
	List<ReviewMessage2> findAll(@Param("page")Integer page,@Param("uid")Long uid);
	
	@Query(value="select count(*) from review_message",nativeQuery=true)
	Integer countPage();
	
	ReviewMessage2 findByIccid(String iccid);

	List<ReviewMessage2> findByRenewaer(Long renewaer);
}
