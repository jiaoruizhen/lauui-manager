package com.dognessnetwork.ops.service;

import java.util.List;

import com.dognessnetwork.ops.domain.DeviceInfo;
import com.dognessnetwork.ops.domain.SimInfo;

public interface DeviceInfoService {

	SimInfo selectICCIDByDeviceId(String deviceId);
	
	List<DeviceInfo>selectICCID();
	}
