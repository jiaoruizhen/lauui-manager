package com.dognessnetwork.ops.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dognessnetwork.ops.domain.SystemLogs;
import com.dognessnetwork.ops.repository.SystemLogRepository;
import com.dognessnetwork.ops.service.SystemLogService;


@Service
public class SystemLogServiceImpl implements SystemLogService {

    @Autowired
    SystemLogRepository systemLogRepository ;
    @Override
    public List<SystemLogs> findAll() {
        return null;
    }

    @Override
    public void saveUser(SystemLogs log) {
        systemLogRepository.save(log);
    }

    @Override
    public SystemLogs findOne(long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
