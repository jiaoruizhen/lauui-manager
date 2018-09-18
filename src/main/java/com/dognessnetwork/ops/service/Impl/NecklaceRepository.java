package com.dognessnetwork.ops.service.Impl;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dognessnetwork.ops.domain.Necklace;

public interface NecklaceRepository extends JpaRepository<Necklace, Long> {

	Necklace findByDeviceId(Long dId);

}