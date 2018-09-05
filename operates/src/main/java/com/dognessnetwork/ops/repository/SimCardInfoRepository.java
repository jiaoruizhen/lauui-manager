package com.dognessnetwork.ops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dognessnetwork.ops.domain.SIMCardInfo;

public interface SimCardInfoRepository extends JpaRepository<SIMCardInfo,Long>{
	@Query(value="select * from sim_card_info s where s.imei=:imei ", nativeQuery = true)
	SIMCardInfo selectByDeviceId(@Param("imei")String imei);
	
	@Query(value="select * from sim_card_info s where s.operator=:operate ORDER BY s.billingenddate  LIMIT :page,20", nativeQuery = true)
	List<SIMCardInfo> findAll(@Param("page")Integer page,@Param("operate")Integer operate);
	
	@Query(value="select count(*) from sim_card_info s where s.operator=:operate", nativeQuery = true)
	Integer selectTotalpage(@Param("operate")Integer operate);
	
	@Query(value="select * from sim_card_info s where s.imei=:imei and s.operator=:operate ", nativeQuery = true)
	SIMCardInfo selectByDeviceIdAndOperator(@Param("imei")String imei,@Param("operate")Integer operate);
}
