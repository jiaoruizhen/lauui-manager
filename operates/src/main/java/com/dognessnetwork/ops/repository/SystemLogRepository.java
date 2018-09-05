package com.dognessnetwork.ops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dognessnetwork.ops.domain.SystemLogs;


/**
 * @Description:
 * @Author: vesus
 * @CreateDate: 2018/5/20 上午11:31
 * @Version: 1.0
 */
public interface SystemLogRepository extends JpaRepository<SystemLogs,Long> {
}
