package com.dognessnetwork.ops.service;

import com.dognessnetwork.ops.domain.ReviewMessage;
import com.dognessnetwork.ops.domain.SIMCard;
import com.dognessnetwork.ops.domain.SIMCardInfo;
import com.dognessnetwork.ops.utils.ReturnData;

public interface SIMCardService {
	/**
	 * 批量查询并更新号码的信息
	 * @param msisdn
	 * @param iccid
	 * @param imei
	 * @param imsi
	 * @return
	 */
	ReturnData insertSIMCard(String msisdn,String iccid,String imei,String imsi);
	/**
	 * 批量查询号码的月流量情况
	 * @param msisdn
	 * @param iccid
	 * @param imei
	 * @param imsi
	 * @return
	 */
	ReturnData querySIMCardDailyFlow(String msisdn,String iccid,String imei,String imsi);
	/**
	 * 批量查询号码的日流量使用情况
	 * @param msisdn
	 * @param iccid
	 * @param imei
	 * @param imsi
	 * @return
	 */
	ReturnData querySIMCardDayFlow(String msisdn,String iccid,String imei,String imsi);
	/**
	 * 批量查询号码的历史日流量使用情况
	 * @param msisdn
	 * @param iccid
	 * @param imei
	 * @param imsi
	 * @return
	 */
	ReturnData querySIMCardHistoryDayFlow(String msisdn,String iccid,String imei,String imsi,String month,String date);
	/**
	 * 查询集团流量池的使用情况
	 * @param groupCode
	 * @return
	 */
	ReturnData querySIMCardPoolUserdFlow(String groupCode);
	/**
	 * 查询企业月缴费账单
	 * @param groupCode
	 * @param date
	 * @return
	 */
	ReturnData queryGroupMonthFee(String groupCode,String date);
	/**
	 * 批量查询卡流量池的使用情况
	 * @param msisdn
	 * @param iccid
	 * @param imei
	 * @param imsi
	 * @return
	 */
	ReturnData querySIMCardBatchPoolUserdFlow(String msisdn,String iccid,String imei,String imsi);
	/**
	 * 批量查询卡月费用情况
	 * @param msisdn
	 * @param iccid
	 * @param imei
	 * @param imsi
	 * @return
	 */
	ReturnData querySIMCardMonthFeeUserd(String msisdn,String iccid,String imei,String imsi,String month);
	/**
	 * 查询单个号码余额
	 * @param msisdn
	 * @param iccid
	 * @param imei
	 * @param imsi
	 * @return
	 */
	ReturnData querySIMCardRestMoney(String msisdn,String iccid,String imei,String imsi);
	/**
	 * 查询所有sim卡信息
	 * @param limit 
	 * @param card 
	 * @return
	 */
	/**
	 * 根据msisdn\imei查询SIM卡信息
	 * @param msisdn
	 * @return
	 */
	ReturnData querySIMCardByMsisdn(String msisdn,String imei);
	Object getById(Long id);
	Object queryAllSIMCardInfo(Integer page, Integer limit, SIMCardInfo card);
	void renews(String id, String time);
	void audit(String id, String time);
	Object auditList(Integer page, Integer limit, ReviewMessage mes);
	void auditOper(String id, Integer auditStatus);
	Object queryAOYISIMCardInfoByMsisdnOrIMEI(String card, String imei);
	
}
