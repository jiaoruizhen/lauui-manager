package com.dognessnetwork.ops.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dognessnetwork.ops.domain.Device;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.repository.DeviceRepository;
import com.dognessnetwork.ops.service.DeviceService;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

	private @Autowired DeviceRepository deviceRepository;
	private @Autowired FeederRepository feederRepository;
	private @Autowired NecklaceRepository necklaceRepository;
	private @Autowired RobotRepository robotRepository;
	private @Autowired TagRepository tagRepository;
	
	@Override
	public Page<Device> all(Integer page, Integer limit, Device dev) {
		page = page == null ? 1 : page;
		limit = limit == null ? 10 : limit;
		return CommonService.getPageContent(page, limit, dev, deviceRepository);
	}

	@Override
	public Object getById(Long id) {
		Optional<Device> _dev = deviceRepository.findById(id);
		if(_dev.isPresent()){
			Device dev = _dev.get();
			Long dId = dev.getId();
			String type = dev.getType();
			
			Object detail = null;
			if("FEEDER".equals(type)){
				detail = feederRepository.findByDeviceId(dId);
			}else if("ROBOT".equals(type)){
				detail = robotRepository.findByDeviceId(dId);
			}else if("TAG".equals(type)){
				detail = tagRepository.findByDeviceId(dId);
			}else if("NECKLACE".equals(type)){
				detail = necklaceRepository.findByDeviceId(dId);
			}
			return Response.SUCCESS(detail);
		}
		return Response.INTERNAL_SERVER_ERROR;
	}

}
