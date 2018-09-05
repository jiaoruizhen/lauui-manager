package com.dognessnetwork.ops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dognessnetwork.ops.domain.SimInfo;

public interface SimInfoRepository extends JpaRepository<SimInfo,Long>{
	@Query(value="select * from sim_info s where s.sim_area=1 and s.id=:id ", nativeQuery = true)
	SimInfo selectByDeviceId(@Param("id")Long id);
}
