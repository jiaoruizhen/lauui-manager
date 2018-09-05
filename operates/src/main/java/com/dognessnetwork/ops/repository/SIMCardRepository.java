package com.dognessnetwork.ops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dognessnetwork.ops.domain.SIMCard;

public interface SIMCardRepository extends JpaRepository<SIMCard,Long>{
	
	@Query(value="select * from sim_card s where s.msisdn=:msisdn ", nativeQuery = true)
	SIMCard selectByMsisdn(@Param("msisdn")String msisdn);
	
	@Query(value="select * from sim_card s where s.imei=:imei ", nativeQuery = true)
	SIMCard selectByIMEI(@Param("imei")String imei);
	
	@Query(value="select * from sim_card s where s.iccid=:iccid ", nativeQuery = true)
	SIMCard selectByICCID(@Param("iccid")String iccid);
	//SELECT * from sim_card  where id ORDER BY id LIMIT 21,20
	@Query(value="SELECT * from sim_card s where s.id ORDER BY s.id LIMIT :page,20", nativeQuery = true)
	List<SIMCard>selectAllSIMCardInfo(@Param("page")Integer page  );
	
	@Query(value="SELECT count(*) from sim_card", nativeQuery = true)
	Integer selectTotalPage();
}
