package com.dognessnetwork.ops.service;

import org.springframework.data.domain.Page;

import com.dognessnetwork.ops.domain.Device;

public interface DeviceService {

	Page<Device> all(Integer page, Integer limit, Device dev);

	Object getById(Long id);

}
