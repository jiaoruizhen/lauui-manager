package com.dognessnetwork.ops.service.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dognessnetwork.ops.domain.PackageInfo;
import com.dognessnetwork.ops.domain.ReviewMessage2;
import com.dognessnetwork.ops.domain.SIMCardInfo;
import com.dognessnetwork.ops.repository.RenewaMessageRepository2;
import com.dognessnetwork.ops.repository.SimCardInfoRepository;
import com.dognessnetwork.ops.service.SimCardInfoService;
import com.dognessnetwork.ops.utils.ReturnData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional
@Service
public class SimCardInfoServiceImpl implements SimCardInfoService {
	@Autowired
	private SimCardInfoRepository simCardInfoRepository;
	@Autowired
	private RenewaMessageRepository2 renewaMessageRepository;


	@Override
	public SIMCardInfo insertSimCardInfo(SIMCardInfo sIMCardInfo) {

		return simCardInfoRepository.save(sIMCardInfo);
	}

	@Override
	public SIMCardInfo selectByImei(String imei) {
		return simCardInfoRepository.selectByDeviceId(imei);
	}

	@Override
	public ReturnData selectAll(Integer page, Integer operate) {
		if (page == null || page <= 0) {
			page = 1;
		}
		if (operate == null || operate <= 0) {
			operate = 1;
		}
		List<SIMCardInfo> list = simCardInfoRepository.findAll(page, operate);
		Integer count = simCardInfoRepository.selectTotalpage(operate);
		return new ReturnData(200, "成功！", list, count);
	}

	@Override
	public ReturnData selectByImeiAndOperate(String imei, Integer operate) {
		if (imei == null || imei == "" || operate == null || operate <= 0) {
			return new ReturnData(213, "请输入imei!");
		}
		SIMCardInfo sim = simCardInfoRepository.selectByDeviceIdAndOperator(imei, operate);
		if (sim == null) {
			return new ReturnData(214, "该imei号不存在");
		}
		return new ReturnData(200, "成功", sim);
	}

	@Override
	public ReturnData selectById(List<Long> idList, Long uid) {
		List<SIMCardInfo> list = new ArrayList<>();
		for (Long id : idList) {
			SIMCardInfo simCardInfo = simCardInfoRepository.getOne(id);
			if (simCardInfo != null) {
				list.add(simCardInfo);
				ReviewMessage2 renewaMessage1 = renewaMessageRepository.findByIccid(simCardInfo.getIccid());
				ReviewMessage2 renewaMessage = new ReviewMessage2();
				if (renewaMessage1 != null) {
					renewaMessage.setId(renewaMessage1.getId());
				}
				renewaMessage.setIccid(simCardInfo.getIccid());
				renewaMessage.setImei(simCardInfo.getImei());
				renewaMessage.setOperator(simCardInfo.getOperator());
				renewaMessage.setUser(uid);
				renewaMessage.setStatus(0);
				renewaMessage.setRenewaer((long) 2);
				if (simCardInfo.getMeals() != null) {
					renewaMessage.setMeals(simCardInfo.getMeals());
					renewaMessage.setFlow(simCardInfo.getFlow());
					renewaMessage.setBillingcycle(simCardInfo.getBillingcycle());
					renewaMessage.setBillingenddate(simCardInfo.getBillingenddate());
					renewaMessage.setCardStatus(simCardInfo.getCardstatus());
				} else if (simCardInfo.getPackageInfo() != null) {
					JSONArray array = JSONArray.fromObject(simCardInfo.getPackageInfo());
					for (int i = 0; i < array.size(); i++) {
						PackageInfo packageInfo = (PackageInfo) JSONObject.toBean(array.getJSONObject(i),
								PackageInfo.class);
						if ("生效".equals(packageInfo.getPkgStatus())) {
							renewaMessage.setMeals(packageInfo.getPkgName());
							try {
								renewaMessage.setBillingenddate(new SimpleDateFormat("yyyy-MM-dd").format(
										new SimpleDateFormat("yyyyMMdd").parse(packageInfo.getPkgExpireTime())));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							renewaMessage.setCardStatus(simCardInfo.getStatus().toString());
						}
					}
				}
				renewaMessageRepository.saveAndFlush(renewaMessage);
			}
		}
		return new ReturnData(200, "成功", list);
	}

}
