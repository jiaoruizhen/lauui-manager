package com.dognessnetwork.ops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dognessnetwork.ops.domain.DeviceInfo;


public interface DeviceInfoRepository extends JpaRepository<DeviceInfo,Long> {
	@Query(value="select * from device_info d where d.device_id=:imei ", nativeQuery = true)
	DeviceInfo selectByDeviceId(@Param("imei")String imei);
	
	@Query(value="select * from device_info d where d.sale_area=1 ", nativeQuery = true)
	List<DeviceInfo> selectBySaleArea();
}
