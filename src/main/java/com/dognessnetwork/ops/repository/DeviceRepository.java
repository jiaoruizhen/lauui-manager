package com.dognessnetwork.ops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dognessnetwork.ops.domain.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {

}
