package com.dognessnetwork.ops.service;


import java.util.List;

import com.dognessnetwork.ops.domain.SystemLogs;


/**
 * @Description:
 * @Author: vesus
 * @CreateDate: 2018/5/20 上午11:43
 * @Version: 1.0
 */
public interface SystemLogService {

    public List<SystemLogs> findAll();

    public void saveUser(SystemLogs log);

    public SystemLogs findOne(long id);

    public void delete(long id);

}
