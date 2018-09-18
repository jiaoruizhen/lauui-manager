package com.dognessnetwork.ops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dognessnetwork.ops.domain.ReviewMessage;

public interface RenewaMessageRepository extends JpaRepository<ReviewMessage, Long> {

}
