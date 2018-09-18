package com.dognessnetwork.ops.service;




import java.util.List;

import com.dognessnetwork.ops.domain.SIMCardInfo;
import com.dognessnetwork.ops.utils.ReturnData;

public interface SimCardInfoService {
	SIMCardInfo insertSimCardInfo(SIMCardInfo sIMCardInfo);
	SIMCardInfo selectByImei(String imei);
	ReturnData selectAll(Integer page,Integer operate);
	ReturnData selectByImeiAndOperate(String imei,Integer operate);
	ReturnData selectById(List<Long> idList, Long uid);
}
