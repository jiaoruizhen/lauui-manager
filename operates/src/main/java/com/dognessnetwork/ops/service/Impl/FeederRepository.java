package com.dognessnetwork.ops.service.Impl;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dognessnetwork.ops.domain.Feeder;

public interface FeederRepository extends JpaRepository<Feeder, Long> {

	Feeder findByDeviceId(Long id);

}
