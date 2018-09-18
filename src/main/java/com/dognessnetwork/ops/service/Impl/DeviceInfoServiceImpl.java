package com.dognessnetwork.ops.service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dognessnetwork.ops.domain.DeviceInfo;
import com.dognessnetwork.ops.domain.SimInfo;
import com.dognessnetwork.ops.repository.DeviceInfoRepository;
import com.dognessnetwork.ops.repository.SimInfoRepository;
import com.dognessnetwork.ops.service.DeviceInfoService;
@Transactional
@Service
public class DeviceInfoServiceImpl implements DeviceInfoService{
	@Autowired
	private DeviceInfoRepository deviceInfoRepository;
	@Autowired
	private SimInfoRepository simInfoRepository;
	@Override
	public SimInfo selectICCIDByDeviceId(String deviceId) {
		DeviceInfo deviceInfo=deviceInfoRepository.selectByDeviceId(deviceId);
		if(deviceInfo==null){
			return null;	
		}
		SimInfo simInfo=simInfoRepository.getOne(deviceInfo.getSimId());
		if(simInfo==null){
			return null;
		}
		return simInfo;
	}
	@Override
	public List<DeviceInfo> selectICCID() {
		List<DeviceInfo> deviceInfoList=deviceInfoRepository.selectBySaleArea();
		List<DeviceInfo> list=new ArrayList<>();
		for(DeviceInfo deviceInfo:deviceInfoList){
			if(deviceInfo.getSimId()!=null){
				SimInfo simInfo=simInfoRepository.selectByDeviceId(deviceInfo.getSimId());
				if(simInfo!=null){
					deviceInfo.setCardId(simInfo.getICCID());
					deviceInfo.setOperator(Long.parseLong("1"));
					list.add(deviceInfo);
				}
			}
			
		}
		System.out.println(deviceInfoList);
		return list;
	}

}
