package com.dognessnetwork.ops.service.Impl;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dognessnetwork.ops.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

	Tag findByDeviceId(Long dId);

}
