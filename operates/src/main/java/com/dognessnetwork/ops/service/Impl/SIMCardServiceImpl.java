package com.dognessnetwork.ops.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.dognessnetwork.ops.common.AOYISIMCardAPI;
import com.dognessnetwork.ops.common.Constant;
import com.dognessnetwork.ops.common.SIMCardAPI;
import com.dognessnetwork.ops.domain.ReviewMessage;
import com.dognessnetwork.ops.domain.SIMCard;
import com.dognessnetwork.ops.domain.SIMCardInfo;
import com.dognessnetwork.ops.domain.SimInfo;
import com.dognessnetwork.ops.dto.RenemaMessageView;
import com.dognessnetwork.ops.dto.Response;
import com.dognessnetwork.ops.dto.SIMCardView;
import com.dognessnetwork.ops.repository.RenewaMessageRepository;
import com.dognessnetwork.ops.repository.SIMCardRepository;
import com.dognessnetwork.ops.repository.SimCardInfoRepository;
import com.dognessnetwork.ops.service.DeviceInfoService;
import com.dognessnetwork.ops.service.SIMCardService;
import com.dognessnetwork.ops.utils.ReturnData;
import com.dognessnetwork.ops.utils.StringToolsUtil;
import com.dognessnetwork.ops.utils.UpdateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional
@Service
public class SIMCardServiceImpl implements SIMCardService {
	private final static Logger logger = LoggerFactory.getLogger(SIMCardServiceImpl.class);
	@Autowired
	private SIMCardRepository sIMCardRepository;
	@Autowired
	private SimCardInfoRepository simCardInfoRepository;
	@Autowired
	private RenewaMessageRepository renewaMessageRepository;
	@Autowired
	private DeviceInfoService deviceInfoService;

	/**
	 * 查询并添加或更新单个卡号信息
	 */
	@Override
	public ReturnData insertSIMCard(String msisdn, String iccid, String imei, String imsi) {
		String cardResult = SIMCardAPI.queryCardID(msisdn, iccid, imei, imsi);
		if (cardResult == null || cardResult == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject json = JSONObject.fromObject(cardResult);
		if ("38".equals(json.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(json.get("code"))) {
			return new ReturnData(204, json.get("error").toString());
		}
		List<SIMCardView> simList = SIMCardAPI.parseResult(cardResult);
		if (simList.size() <= 0 || simList == null) {
			return new ReturnData(206, "查询卡号不能为空！");
		}
		String iccids = "";
		for (SIMCardView sim : simList) {
			if ("1".equals(sim.getCode())) {// 出错"业务处理失败，号码不属于该集团\"
				return new ReturnData(205, "业务处理失败，号码不属于该集团");
			}
			iccids += sim.getIccid() + ",";
			SIMCard sim1 = sIMCardRepository.selectByMsisdn(sim.getMsisdn());
			logger.info("SIMCardView:" + sim1);
			if (sim1 != null) {
				sim1.setIccid(sim.getIccid());
				sim1.setImei(sim.getImei());
				sim1.setImsi(sim.getImsi());
				sIMCardRepository.saveAndFlush(sim1);
			} else {
				SIMCard sim2 = new SIMCard();
				sim2.setIccid(sim.getIccid());
				sim2.setImei(sim.getImei());
				sim2.setImsi(sim.getImsi());
				sim2.setMsisdn(sim.getMsisdn());
				sIMCardRepository.save(sim2);
			}
		}
		String result = SIMCardAPI.queryCardInfo(iccids, Constant.QUERY_BATCH_CARDINFO, null, null, null);
		logger.info("result:" + result);
		if (result == null || result == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject jsonobject = JSONObject.fromObject(result);
		if ("38".equals(jsonobject.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(jsonobject.get("code"))) {
			return new ReturnData(204, jsonobject.get("error").toString());
		}
		JSONObject jsonobject1 = JSONObject.fromObject(jsonobject.get("data"));
		JSONObject jsonobject2 = JSONObject.fromObject(jsonobject1.get("msisdnList"));
		JSONArray jsonobject3 = JSONArray.fromObject(jsonobject2.get("list"));
		List<SIMCard> list = new ArrayList<>();
		if (jsonobject3.size() > 0) {
			for (int i = 0; i < jsonobject3.size(); i++) {
				SIMCard sim = SIMCardAPI.parseRresult(jsonobject3.getString(i));
				logger.info("sim:" + sim);
				logger.info("sim:" + sim.getMsisdn());
				SIMCard sim1 = sIMCardRepository.selectByMsisdn(sim.getMsisdn());
				logger.info("sim1:" + sim1);
				if (sim1 != null) {
					sim.setIccid(sim1.getIccid());
					sim.setImei(sim1.getImei());
					sim.setImsi(sim1.getImsi());
					sim.setFlowInfo(sim1.getFlowInfo());
					sim.setId(sim1.getId());
					SIMCard sim2 = sIMCardRepository.saveAndFlush(sim);
					list.add(sim2);
				} else {
					SIMCard sim2 = sIMCardRepository.save(sim);
					list.add(sim2);
				}
			}
		}

		logger.info("jsonobject3:" + jsonobject3);
		return new ReturnData(200, "成功！", list);
	}

	/**
	 * 批量查询并更新卡的月流量情况
	 */
	@Override
	public ReturnData querySIMCardDailyFlow(String msisdn, String iccid, String imei, String imsi) {
		String cardResult = SIMCardAPI.queryCardID(msisdn, iccid, imei, imsi);
		if (cardResult == null || cardResult == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject json = JSONObject.fromObject(cardResult);
		if ("38".equals(json.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(json.get("code"))) {
			return new ReturnData(204, json.get("error").toString());
		}
		List<SIMCardView> simList = SIMCardAPI.parseResult(cardResult);
		if (simList.size() <= 0 || simList == null) {
			return new ReturnData(206, "查询卡号不能为空！");
		}
		String iccids = "";
		for (SIMCardView sim : simList) {
			if ("1".equals(sim.getCode())) {// 出错"业务处理失败，号码不属于该集团\"
				return new ReturnData(205, "业务处理失败，号码不属于该集团");
			}
			iccids += sim.getIccid() + ",";
			SIMCard sim1 = sIMCardRepository.selectByMsisdn(sim.getMsisdn());
			if (sim1 != null) {
				sim1.setIccid(sim.getIccid());
				sim1.setImei(sim.getImei());
				sim1.setImsi(sim.getImsi());
				sIMCardRepository.saveAndFlush(sim1);
			} else {
				SIMCard sim2 = new SIMCard();
				sim2.setIccid(sim.getIccid());
				sim2.setImei(sim.getImei());
				sim2.setImsi(sim.getImsi());
				sim2.setMsisdn(sim.getMsisdn());
				sIMCardRepository.save(sim2);
			}
		}
		String result = SIMCardAPI.queryCardInfo(iccids, Constant.QUERY_BATCH_CARD_DAILYFLOW, null, null, null);
		System.out.println("result:" + result);
		if (result == null || result == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject jsonobject = JSONObject.fromObject(result);
		if ("38".equals(jsonobject.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(jsonobject.get("code"))) {
			return new ReturnData(204, jsonobject.get("error").toString());
		}
		JSONObject jsonobject1 = JSONObject.fromObject(jsonobject.get("data"));
		JSONObject jsonobject2 = JSONObject.fromObject(jsonobject1.get("msisdnInfoList"));
		JSONArray jsonobject3 = JSONArray.fromObject(jsonobject2.get("list"));
		System.out.println("jsonobject3:" + jsonobject3.getJSONObject(0));
		List<SIMCard> list = new ArrayList<>();
		for (int i = 0; i < jsonobject3.size(); i++) {
			String msisdns = JSONObject.fromObject(jsonobject3.getString(i)).get("msisdn").toString();
			System.out.println(msisdns);
			SIMCard sim1 = sIMCardRepository.selectByMsisdn(msisdns);
			sim1.setFlowInfo(JSONObject.fromObject(JSONObject.fromObject(jsonobject3.getString(i)).get("apnList"))
					.get("list").toString());
			SIMCard sim2 = sIMCardRepository.saveAndFlush(sim1);
			list.add(sim2);
		}
		return new ReturnData(200, "成功", list);
	}

	/**
	 * 批量查询卡号日流量使用情况
	 */
	@Override
	public ReturnData querySIMCardDayFlow(String msisdn, String iccid, String imei, String imsi) {
		String cardResult = SIMCardAPI.queryCardID(msisdn, iccid, imei, imsi);
		if (cardResult == null || cardResult == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject json = JSONObject.fromObject(cardResult);
		if ("38".equals(json.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(json.get("code"))) {
			return new ReturnData(204, json.get("error").toString());
		}
		List<SIMCardView> simList = SIMCardAPI.parseResult(cardResult);
		if (simList.size() <= 0 || simList == null) {
			return new ReturnData(206, "查询卡号不能为空！");
		}
		String iccids = "";
		for (SIMCardView sim : simList) {
			if ("1".equals(sim.getCode())) {// 出错"业务处理失败，号码不属于该集团\"
				return new ReturnData(205, "业务处理失败，号码不属于该集团");
			}
			iccids += sim.getIccid() + ",";
			SIMCard sim1 = sIMCardRepository.selectByMsisdn(sim.getMsisdn());
			if (sim1 != null) {
				sim1.setIccid(sim.getIccid());
				sim1.setImei(sim.getImei());
				sim1.setImsi(sim.getImsi());
				sIMCardRepository.saveAndFlush(sim1);
			} else {
				SIMCard sim2 = new SIMCard();
				sim2.setIccid(sim.getIccid());
				sim2.setImei(sim.getImei());
				sim2.setImsi(sim.getImsi());
				sim2.setMsisdn(sim.getMsisdn());
				sIMCardRepository.save(sim2);
			}
		}
		String result = SIMCardAPI.queryCardInfo(iccids, Constant.QUERY_BATCH_CARD_DAYFLOW, null, null, null);
		System.out.println("result:" + result);
		if (result == null || result == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject jsonobject = JSONObject.fromObject(result);
		if ("38".equals(jsonobject.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(jsonobject.get("code"))) {
			return new ReturnData(204, jsonobject.get("error").toString());
		}
		JSONObject jsonobject1 = JSONObject.fromObject(jsonobject.get("data"));
		return new ReturnData(200, "成功", jsonobject1);
	}

	@Override
	public ReturnData querySIMCardHistoryDayFlow(String msisdn, String iccid, String imei, String imsi, String month,
			String date) {
		String cardResult = SIMCardAPI.queryCardID(msisdn, iccid, imei, imsi);
		if (cardResult == null || cardResult == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject json = JSONObject.fromObject(cardResult);
		if ("38".equals(json.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(json.get("code"))) {
			return new ReturnData(204, json.get("error").toString());
		}
		List<SIMCardView> simList = SIMCardAPI.parseResult(cardResult);
		if (simList.size() <= 0 || simList == null) {
			return new ReturnData(206, "查询卡号不能为空！");
		}
		String iccids = "";
		for (SIMCardView sim : simList) {
			if ("1".equals(sim.getCode())) {// 出错"业务处理失败，号码不属于该集团\"
				return new ReturnData(205, "业务处理失败，号码不属于该集团");
			}
			iccids += sim.getIccid() + ",";
			SIMCard sim1 = sIMCardRepository.selectByMsisdn(sim.getMsisdn());
			if (sim1 != null) {
				sim1.setIccid(sim.getIccid());
				sim1.setImei(sim.getImei());
				sim1.setImsi(sim.getImsi());
				sIMCardRepository.saveAndFlush(sim1);
			} else {
				SIMCard sim2 = new SIMCard();
				sim2.setIccid(sim.getIccid());
				sim2.setImei(sim.getImei());
				sim2.setImsi(sim.getImsi());
				sim2.setMsisdn(sim.getMsisdn());
				sIMCardRepository.save(sim2);
			}
		}
		String result = SIMCardAPI.queryCardInfo(iccids, Constant.QUERY_BATCH_CARD_HISTORY_DAYFLOW, month, date, null);
		System.out.println("result:" + result);
		if (result == null || result == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject jsonobject = JSONObject.fromObject(result);
		if ("38".equals(jsonobject.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(jsonobject.get("code"))) {
			return new ReturnData(204, jsonobject.get("error").toString());
		}
		JSONObject jsonobject1 = JSONObject.fromObject(jsonobject.get("data"));
		return new ReturnData(200, "成功", jsonobject1);
	}

	@Override
	public ReturnData querySIMCardPoolUserdFlow(String groupCode) {
		String result = SIMCardAPI.queryCardInfo(null, Constant.QUERY_GROUP_POOL_USEDFLOW, null, null, groupCode);
		System.out.println("result:" + result);
		if (result == null || result == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject jsonobject = JSONObject.fromObject(result);
		if ("38".equals(jsonobject.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(jsonobject.get("code"))) {
			return new ReturnData(204, jsonobject.get("error").toString());
		}
		JSONObject jsonobject1 = JSONObject.fromObject(jsonobject.get("data"));
		return new ReturnData(200, "成功", jsonobject1);
	}

	@Override
	public ReturnData queryGroupMonthFee(String groupCode, String date) {
		String result = SIMCardAPI.queryCardInfo(null, Constant.QUERY_GROUP_MONTHFEE, date, null, groupCode);
		System.out.println("result:" + result);
		if (result == null || result == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject jsonobject = JSONObject.fromObject(result);
		if ("38".equals(jsonobject.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(jsonobject.get("code"))) {
			return new ReturnData(204, jsonobject.get("error").toString());
		}
		JSONObject jsonobject1 = JSONObject.fromObject(jsonobject.get("data"));
		return new ReturnData(200, "成功", jsonobject1);
	}

	@Override
	public ReturnData querySIMCardBatchPoolUserdFlow(String msisdn, String iccid, String imei, String imsi) {
		String cardResult = SIMCardAPI.queryCardID(msisdn, iccid, imei, imsi);
		if (cardResult == null || cardResult == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject json = JSONObject.fromObject(cardResult);
		if ("38".equals(json.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(json.get("code"))) {
			return new ReturnData(204, json.get("error").toString());
		}
		List<SIMCardView> simList = SIMCardAPI.parseResult(cardResult);
		if (simList.size() <= 0 || simList == null) {
			return new ReturnData(206, "查询卡号不能为空！");
		}
		String iccids = "";
		for (SIMCardView sim : simList) {
			if ("1".equals(sim.getCode())) {// 出错"业务处理失败，号码不属于该集团\"
				return new ReturnData(205, "业务处理失败，号码不属于该集团");
			}
			iccids += sim.getIccid() + ",";
			SIMCard sim1 = sIMCardRepository.selectByMsisdn(sim.getMsisdn());
			if (sim1 != null) {
				sim1.setIccid(sim.getIccid());
				sim1.setImei(sim.getImei());
				sim1.setImsi(sim.getImsi());
				sIMCardRepository.saveAndFlush(sim1);
			} else {
				SIMCard sim2 = new SIMCard();
				sim2.setIccid(sim.getIccid());
				sim2.setImei(sim.getImei());
				sim2.setImsi(sim.getImsi());
				sim2.setMsisdn(sim.getMsisdn());
				sIMCardRepository.save(sim2);
			}
		}
		String result = SIMCardAPI.queryCardInfo(iccids, Constant.QUERY_BATCH_CARD_POOL_USEDFLOW, null, null, null);
		System.out.println("result:" + result);
		if (result == null || result == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject jsonobject = JSONObject.fromObject(result);
		if ("38".equals(jsonobject.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(jsonobject.get("code"))) {
			return new ReturnData(204, jsonobject.get("error").toString());
		}
		JSONObject jsonobject1 = JSONObject.fromObject(jsonobject.get("data"));

		return new ReturnData(200, "成功", jsonobject1);
	}

	@Override
	public ReturnData querySIMCardMonthFeeUserd(String msisdn, String iccid, String imei, String imsi, String month) {
		String cardResult = SIMCardAPI.queryCardID(msisdn, iccid, imei, imsi);
		if (cardResult == null || cardResult == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject json = JSONObject.fromObject(cardResult);
		if ("38".equals(json.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(json.get("code"))) {
			return new ReturnData(204, json.get("error").toString());
		}
		List<SIMCardView> simList = SIMCardAPI.parseResult(cardResult);
		if (simList.size() <= 0 || simList == null) {
			return new ReturnData(206, "查询卡号不能为空！");
		}
		String iccids = "";
		for (SIMCardView sim : simList) {
			if ("1".equals(sim.getCode())) {// 出错"业务处理失败，号码不属于该集团\"
				return new ReturnData(205, "业务处理失败，号码不属于该集团");
			}
			iccids += sim.getIccid() + ",";
			SIMCard sim1 = sIMCardRepository.selectByMsisdn(sim.getMsisdn());
			if (sim1 != null) {
				sim1.setIccid(sim.getIccid());
				sim1.setImei(sim.getImei());
				sim1.setImsi(sim.getImsi());
				sIMCardRepository.saveAndFlush(sim1);
			} else {
				SIMCard sim2 = new SIMCard();
				sim2.setIccid(sim.getIccid());
				sim2.setImei(sim.getImei());
				sim2.setImsi(sim.getImsi());
				sim2.setMsisdn(sim.getMsisdn());
				sIMCardRepository.save(sim2);
			}
		}
		String result = SIMCardAPI.queryCardInfo(iccids, Constant.QUERY_BATCH_CARD_MONTHFEE, month, null, null);
		System.out.println("result:" + result);
		if (result == null || result == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject jsonobject = JSONObject.fromObject(result);
		if ("38".equals(jsonobject.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(jsonobject.get("code"))) {
			return new ReturnData(204, jsonobject.get("error").toString());
		}
		JSONObject jsonobject1 = JSONObject.fromObject(jsonobject.get("data"));

		return new ReturnData(200, "成功", jsonobject1);
	}

	@Override
	public ReturnData querySIMCardRestMoney(String msisdn, String iccid, String imei, String imsi) {
		String cardResult = SIMCardAPI.queryCardID(msisdn, iccid, imei, imsi);
		if (cardResult == null || cardResult == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject json = JSONObject.fromObject(cardResult);
		if ("38".equals(json.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(json.get("code"))) {
			return new ReturnData(204, json.get("error").toString());
		}
		List<SIMCardView> simList = SIMCardAPI.parseResult(cardResult);
		if (simList.size() <= 0 || simList == null) {
			return new ReturnData(206, "查询卡号不能为空！");
		}

		String msisdns = "";
		SIMCard sim1 = null;
		for (SIMCardView sim : simList) {
			if ("1".equals(sim.getCode())) {// 出错"业务处理失败，号码不属于该集团\"
				return new ReturnData(205, "业务处理失败，号码不属于该集团");
			}

			sim1 = sIMCardRepository.selectByMsisdn(sim.getMsisdn());
			if (sim1 != null) {
				sim1.setIccid(sim.getIccid());
				sim1.setImei(sim.getImei());
				sim1.setImsi(sim.getImsi());
				msisdns = sim.getMsisdn();
				sIMCardRepository.saveAndFlush(sim1);
			} else {
				SIMCard sim2 = new SIMCard();
				sim2.setIccid(sim.getIccid());
				sim2.setImei(sim.getImei());
				sim2.setImsi(sim.getImsi());
				sim2.setMsisdn(sim.getMsisdn());
				sim1 = sIMCardRepository.save(sim2);
			}
		}
		String result = SIMCardAPI.querySingleBalance(msisdns, Constant.QUERY_SINGLE_CARD_BALANCE);
		if (result == null || result == "") {
			return new ReturnData(202, "请求卡号不能为空！");
		}
		JSONObject jsonobject = JSONObject.fromObject(result);
		if ("38".equals(jsonobject.get("code"))) {
			return new ReturnData(203, "网络连接失败，请重试！");
		}
		if ("1".equals(jsonobject.get("code"))) {
			return new ReturnData(204, jsonobject.get("error").toString());
		}
		if ("25".equals(jsonobject.get("code"))) {
			return new ReturnData(207, "参数格式错误，请重新填写正确的参数！");
		}
		JSONObject jsonobject1 = JSONObject.fromObject(jsonobject.get("data"));
		SIMCard sim3 = sIMCardRepository.selectByMsisdn(sim1.getMsisdn());
		sim3.setBalance(jsonobject1.get("residual").toString());
		sim1 = sIMCardRepository.saveAndFlush(sim3);
		return new ReturnData(200, "成功", sim1);
	}

	@Override
	public Object queryAllSIMCardInfo(Integer page, Integer limit, SIMCardInfo card) {
		page = page == null ? 1 : page;
		limit = limit == null ? 10 : limit;

		Page<SIMCardInfo> content = CommonService.getPageContent(page, limit, card, simCardInfoRepository);

		Map<String, Object> res = new HashMap<>();
		res.put("code", "0");
		res.put("count", content.getTotalElements());
		res.put("data", content.getContent());
		return res;
	}

	@Override
	public ReturnData querySIMCardByMsisdn(String msisdn, String imei) {
		if (msisdn != "" && msisdn != null) {
			SIMCard sim = sIMCardRepository.selectByMsisdn(msisdn);
			if (sim == null) {
				return new ReturnData(211, "该卡号信息不存在！");
			}
			return new ReturnData(200, "成功", sim);
		} else if (imei != null && imei != "") {
			SIMCard sim = sIMCardRepository.selectByIMEI(imei);
			if (sim == null) {
				return new ReturnData(211, "该卡号信息不存在！");
			}
			return new ReturnData(200, "成功", sim);
		} else {
			return new ReturnData(212, "请输入卡号或者imei号！");
		}
	}

	@Override
	public Object getById(Long id) {
		Optional<SIMCardInfo> _sim = simCardInfoRepository.findById(id);
		if (_sim.isPresent()) {
			SIMCardInfo info = _sim.get();
			String imei = info.getImei();

			SimInfo simInfo = deviceInfoService.selectICCIDByDeviceId(imei);
			Map<String, Object> map = new HashMap<>();
			map.put("info", info);
			if (simInfo != null) {
				JSONObject jsonobject1 = JSONObject.fromObject(AOYISIMCardAPI.queryCardInfo(simInfo.getICCID()));
				if ("0000".equals(jsonobject1.get("status"))) {
					JSONArray array = JSONArray.fromObject(jsonobject1.get("result"));

					SIMCardInfo simCardInfo = (SIMCardInfo) JSONObject.toBean(array.getJSONObject(0), SIMCardInfo.class);
					JSONObject jsonobject = JSONObject.fromObject(AOYISIMCardAPI.queryCardInfo(simCardInfo.getIccid()));
					if ("0000".equals(jsonobject.get("status"))) {
						JSONArray arrays = JSONArray.fromObject(jsonobject.get("result"));
						JSONObject jsonobject2 = arrays.getJSONObject(0);
						map.put("cardOperInfo", jsonobject2);
					}
				}
			}
			return Response.SUCCESS(map);
		}
		return Response.ERROR("没有该SIM卡", 2000);
	}

	@Override
	public void renews(String id, String time) {
		Long[] ids = getIDByIDs(id);

		for (Long _id : ids) {
			Optional<SIMCardInfo> o = simCardInfoRepository.findById(_id);
			if (!o.isPresent())
				break;

			SIMCardInfo simCardInfo = o.get();
			simCardInfo.setExpireTime(time);
			simCardInfoRepository.save(simCardInfo);
		}
	}

	private Long[] getIDByIDs(String id) {
		String[] split = id.split(",");
		Long[] ids = new Long[split.length];
		for (int i = 0; i < split.length; i++) {
			ids[i] = Long.valueOf(split[i]);
		}
		return ids;
	}

	@Override
	public void audit(String id, String time) {
		Long[] ids = getIDByIDs(id);
		ReviewMessage message = null;
		for (Long _id : ids) {
			Optional<SIMCardInfo> o = simCardInfoRepository.findById(_id);
			if (!o.isPresent())
				break;
			SIMCardInfo simCardInfo = o.get();
			simCardInfo.setExpectRenewAt(time);
			simCardInfo.setIsRenewal(true);
			simCardInfo.setRenewalOper(null);// 添加续费的操作人
			simCardInfoRepository.save(simCardInfo);

			message = new ReviewMessage();
			message.setsIMCardInfo(simCardInfo);
			message.setStatus(1);
			renewaMessageRepository.save(message);
		}
	}

	@Override
	public Object auditList(Integer page, Integer limit, ReviewMessage mes) {
		page = page == null ? 1 : page;
		limit = limit == null ? 10 : limit;

		Page<ReviewMessage> content = CommonService.getPageContent(page, limit, mes, renewaMessageRepository);

		List<ReviewMessage> revs = content.getContent();
		RenemaMessageView vo = null;
		List<RenemaMessageView> vos = null;
		if (!revs.isEmpty()) {
			vos = new ArrayList<>();
			for (ReviewMessage o : revs) {
				SIMCardInfo info = o.getsIMCardInfo();
				vo = new RenemaMessageView();
				vo.setSimId(info.getId());
				vo.setAuditStatus(o.getStatus());
				vo.setId(o.getId());
				vos.add(vo);
				UpdateUtil.copyNonNullProperties(info, vo, "id");
			}
		}
		Map<String, Object> res = new HashMap<>();
		res.put("code", "0");
		res.put("count", content.getTotalElements());
		res.put("data", vos);
		return res;
	}



	@Override
	public void auditOper(String id, Integer auditStatus) {
		Long[] ids = getIDByIDs(id);
		if (ids.length == 1) {
			saveRenews(ids[0], auditStatus);
		} else {
			for (Long _id : ids) {
				saveRenews(_id, auditStatus);
			}
		}
	}

	private void saveRenews(Long id, Integer auditStatus) {
		Optional<ReviewMessage> optional = renewaMessageRepository.findById(id);
		if (optional.isPresent()) {
			ReviewMessage review = optional.get();
			Integer status = review.getStatus();

			if (status.equals(3) || status.equals(4)) {
				return;
			}

			SIMCardInfo cardInfo = review.getsIMCardInfo();
			if (auditStatus.equals(1)) {// 审批通过
				review.setStatus(++status);

				if (status.equals(3)) { // 审批完全通过
					cardInfo.setExpireTime(cardInfo.getExpectRenewAt());
					cardInfo.setExpectRenewAt(null);
				}
			} else {
				cardInfo.setExpectRenewAt(null);
				review.setStatus(4);
			}

			cardInfo.setIsRenewal(false);
			renewaMessageRepository.save(review);
		}
	}

	@Override
	public Object queryAOYISIMCardInfoByMsisdnOrIMEI(String card, String imei) {
		if ((card == null || card == "") && (imei != null && imei != "")) {
			if (imei.contains(",")) {// 如果有多个imei
				List<SIMCardView> sIMCardViewList = new ArrayList<>();
				String[] imeis = imei.split(",");
				for (String deviceId : imeis) {
					SimInfo simInfo = deviceInfoService.selectICCIDByDeviceId(deviceId);
					SIMCardView sIMCardView = new SIMCardView();
					JSONObject jsonobject1 = JSONObject.fromObject(AOYISIMCardAPI.queryCardInfo(simInfo.getICCID()));
					if (!"0000".equals(jsonobject1.get("status"))) {
						return new ReturnData(203, jsonobject1.get("message").toString());
					}

					JSONArray array = JSONArray.fromObject(jsonobject1.get("result"));
					for (int i = 0; i < array.size(); i++) {
						SIMCardInfo simCardInfo = (SIMCardInfo) JSONObject.toBean(array.getJSONObject(i),
								SIMCardInfo.class);
						JSONObject jsonobject = JSONObject
								.fromObject(AOYISIMCardAPI.querySIMCard(simCardInfo.getIccid()));
						if (!"0000".equals(jsonobject.get("status"))) {
							return new ReturnData(204, jsonobject1.get("message").toString());
						}
						JSONArray arrays = JSONArray.fromObject(jsonobject.get("result"));
						JSONObject jsonobject2 = arrays.getJSONObject(0);
						if ("00".equals(jsonobject2.get("cardstatus").toString())) {
							sIMCardView.setCardStatus(true);
						}

						sIMCardView.setImei(deviceId);
						sIMCardViewList.add(sIMCardView);
					}
				}
				return new ReturnData(200, "成功", sIMCardViewList);
			}
			// 只有一个
			SimInfo simInfo = deviceInfoService.selectICCIDByDeviceId(imei);
			if (simInfo == null) {
				return new ReturnData(203, "该卡号不存在");
			}
			JSONObject jsonobject1 = JSONObject.fromObject(AOYISIMCardAPI.queryCardInfo(simInfo.getICCID()));
			if (!"0000".equals(jsonobject1.get("status"))) {
				return new ReturnData(203, jsonobject1.get("message").toString());
			}
			List<SIMCardView> simCardInfoList = new ArrayList<>();
			JSONArray array = JSONArray.fromObject(jsonobject1.get("result"));

			SIMCardInfo simCardInfo = (SIMCardInfo) JSONObject.toBean(array.getJSONObject(0), SIMCardInfo.class);
			JSONObject jsonobject = JSONObject.fromObject(AOYISIMCardAPI.queryCardInfo(simCardInfo.getIccid()));
			if (!"0000".equals(jsonobject.get("status"))) {
				return new ReturnData(204, jsonobject1.get("message").toString());
			}
			JSONArray arrays = JSONArray.fromObject(jsonobject.get("result"));
			JSONObject jsonobject2 = arrays.getJSONObject(0);
			return new ReturnData(200, "成功", jsonobject2);
		} else if ((card != null && card != "") || ((card != null || card != "") && (imei == null || imei == ""))) {// 不管imei是否存在
																													// 直接查卡号
			JSONObject jsonobject1 = JSONObject.fromObject(AOYISIMCardAPI.queryCardInfo(card));
			if (!"0000".equals(jsonobject1.get("status"))) {
				return new ReturnData(203, jsonobject1.get("message").toString());
			}
			List<SIMCardInfo> simCardInfoList = new ArrayList<>();
			JSONArray array = JSONArray.fromObject(jsonobject1.get("result"));
			for (int i = 0; i < array.size(); i++) {
				SIMCardInfo simCardInfo = (SIMCardInfo) JSONObject.toBean(array.getJSONObject(i), SIMCardInfo.class);
				JSONObject jsonobject = JSONObject.fromObject(AOYISIMCardAPI.querySIMCard(simCardInfo.getIccid()));
				if (!"0000".equals(jsonobject.get("status"))) {
					return new ReturnData(204, jsonobject1.get("message").toString());
				}
				JSONArray arrays = JSONArray.fromObject(jsonobject.get("result"));
				JSONObject jsonobject2 = arrays.getJSONObject(0);
				simCardInfo.setCardstatus(jsonobject2.get("cardstatus").toString());
				simCardInfo.setGprsstatus(jsonobject2.get("gprsstatus").toString());
				simCardInfo.setMacstatus(jsonobject2.get("macstatus").toString());
				simCardInfo.setMonthFlow(jsonobject2.get("flow").toString());
				simCardInfo.setMsisdn(jsonobject2.get("cardcode").toString());
				simCardInfoList.add(simCardInfo);
			}
			return new ReturnData(200, "成功", simCardInfoList);
		} else {
			return new ReturnData(205, "请输入卡号、iccid、imei、imsi任意一个号码！");
		}
	}
}
