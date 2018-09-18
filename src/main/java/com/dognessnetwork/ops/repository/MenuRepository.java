package com.dognessnetwork.ops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dognessnetwork.ops.domain.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}
