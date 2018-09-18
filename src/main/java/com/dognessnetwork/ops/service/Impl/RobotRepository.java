package com.dognessnetwork.ops.service.Impl;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dognessnetwork.ops.domain.Robot;

public interface RobotRepository extends JpaRepository<Robot, Long> {

	Robot findByDeviceId(Long dId);

}
