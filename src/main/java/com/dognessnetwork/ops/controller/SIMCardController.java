//package com.dognessnetwork.ops.controller;
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.dognessnetwork.ops.common.AOYISIMCardAPI;
//import com.dognessnetwork.ops.common.Constant;
//import com.dognessnetwork.ops.common.SIMCardAPI;
//import com.dognessnetwork.ops.domain.SIMCardInfo;
//import com.dognessnetwork.ops.dto.SIMCardView;
//import com.dognessnetwork.ops.service.SIMCardService;
//import com.dognessnetwork.ops.service.SimCardInfoService;
//import com.dognessnetwork.ops.utils.ReturnData;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//@Controller
//@RequestMapping(value="/sim")
//public class SIMCardController {
//	 private final static  Logger logger = LoggerFactory.getLogger(SIMCardController.class);
//
//	@Autowired
//	private SIMCardService sIMCardService;
//	@Autowired
//	private SimCardInfoService simCardInfoService;
//	
//	@RequestMapping(value="/sim_query",method=RequestMethod.GET)
//	@ResponseBody
//	//解决跨域问题
//	@CrossOrigin 
//	public Object insertSIMCard(String msisdn,String iccid,String imei,String imsi){
//		logger.info("/sim/sim_query====="+msisdn+","+iccid+","+imei+","+imsi);
//		return sIMCardService.insertSIMCard(msisdn, iccid, imei, imsi);
//	}
//
//	@RequestMapping(value="/sim_query_flow",method=RequestMethod.POST)
//	@ResponseBody
//	public Object querySIMCardDailyFlow(String msisdn,String iccid,String imei,String imsi){
//		logger.info("/sim/sim_query_flow====="+msisdn+","+iccid+","+imei+","+imsi);
//		return sIMCardService.querySIMCardDailyFlow(msisdn, iccid, imei, imsi);
//	}
//	
//	@RequestMapping(value="/sim_query_dayflow",method=RequestMethod.POST)
//	@ResponseBody
//	public Object querySIMCardDayFlow(String msisdn,String iccid,String imei,String imsi){
//		logger.info("/sim/sim_query_dayflow====="+msisdn+","+iccid+","+imei+","+imsi);
//		return sIMCardService.querySIMCardDayFlow(msisdn, iccid, imei, imsi);	
//	}
//	@RequestMapping(value="/sim_query_history_dayflow",method=RequestMethod.POST)
//	@ResponseBody
//	public Object querySIMCardHistoryDayFlow(String msisdn, String iccid, String imei, String imsi,String month,String date){
//		logger.info("/sim/sim_query_history_dayflow====="+msisdn+","+iccid+","+imei+","+imsi);
//		return sIMCardService.querySIMCardHistoryDayFlow(msisdn, iccid, imei, imsi, month, date);	
//	}
//	@RequestMapping(value="/sim_query_pool_usedflow",method=RequestMethod.POST)
//	@ResponseBody
//	public Object querySIMCardPoolUsedFlow(String groupCode){
//		logger.info("/sim/sim_query_pool_usedflow====="+groupCode);
//		return sIMCardService.querySIMCardPoolUserdFlow(groupCode);	
//	}
//	@RequestMapping(value="/sim_query_pool_monthfee",method=RequestMethod.POST)
//	@ResponseBody
//	public Object queryGroupMonthFee(String groupCode, String month){
//		logger.info("/sim/sim_query_pool_monthfee====="+groupCode+","+month);
//		return sIMCardService.queryGroupMonthFee(groupCode, month);	
//	}
//	@RequestMapping(value="/sim_query_batchpool_usedflow",method=RequestMethod.POST)
//	@ResponseBody
//	public Object querySIMCardBatchPoolUserdFlow(String msisdn, String iccid, String imei, String imsi){
//		logger.info("/sim/sim_query_batchpool_usedflow====="+msisdn+","+iccid+","+imei+","+imsi);
//		return sIMCardService.querySIMCardBatchPoolUserdFlow(msisdn, iccid, imei, imsi);	
//	}
//	@RequestMapping(value="/sim_query_batch_monthfee",method=RequestMethod.POST)
//	@ResponseBody
//	public Object querySIMCardMonthFeeUserd(String msisdn, String iccid, String imei, String imsi,String month){
//		logger.info("/sim/sim_query_batch_monthfee====="+msisdn+","+iccid+","+imei+","+imsi+","+month);
//		return sIMCardService.querySIMCardMonthFeeUserd(msisdn, iccid, imei, imsi, month);	
//	}
//	@RequestMapping(value="/sim_query_single_balance",method=RequestMethod.POST)
//	@ResponseBody
//	public Object querySIMCardRestMoney(String msisdn, String iccid, String imei, String imsi){
//		logger.info("/sim/sim_query_single_balance====="+msisdn+","+iccid+","+imei+","+imsi);
//		return sIMCardService.querySIMCardRestMoney(msisdn, iccid, imei, imsi);
//	}
////	@RequestMapping(value="/sim_query_info",method=RequestMethod.POST)
////	@ResponseBody
////	public Object queryAllSIMCardInfo(String page){
////		logger.info("/sim/sim_query_info====="+page);
////		return sIMCardService.queryAllSIMCardInfo(Integer.parseInt(page));
////	}
//	
//	@RequestMapping(value="/sim_query_byMsisdnOrImei",method=RequestMethod.POST)
//	@ResponseBody
//	public Object querySIMCardInfoByMsisdnOrIMEI(String msisdn,String imei){
//		sIMCardService.querySIMCardRestMoney(msisdn, null, imei, null);
//		logger.info("/sim/sim_query_byMsisdnOrImei====="+msisdn+","+imei);
//		return sIMCardService.querySIMCardByMsisdn(msisdn,imei);	
//	}
//	
//	@RequestMapping(value="/sim_query_aoyiCard2",method=RequestMethod.POST)
//	@ResponseBody
//	@CrossOrigin 
//	public Object queryAOYISIMCardInfoByMsisdnOrIMEI2(String imei){
////		logger.info("/sim/sim_query_aoyiCard====="+card+","+imei);
////		
////		if((card==null||card=="")&&(imei!=null&&imei!="")){//imei 存在  card 不存在
////			if(imei.contains(",")){//如果有多个imei
////				List<SIMCardView>sIMCardViewList=new ArrayList<>();
////				String[] imeis=imei.split(",");
////				for(String deviceId:imeis){
////					SimInfo simInfo=deviceInfoService.selectICCIDByDeviceId(deviceId);
////					SIMCardView sIMCardView=new SIMCardView();	
////				JSONObject jsonobject1 = JSONObject.fromObject(AOYISIMCardAPI.queryCardInfo(simInfo.getICCID()));
////				if(!"0000".equals(jsonobject1.get("status"))){
////					return new ReturnData(203,jsonobject1.get("message").toString());
////				}
////				
////				JSONArray array=JSONArray.fromObject(jsonobject1.get("result"));
////				for(int i=0;i<array.size();i++){
////					SIMCardInfo simCardInfo=(SIMCardInfo) JSONObject.toBean(array.getJSONObject(i), SIMCardInfo.class);
////					JSONObject jsonobject = JSONObject.fromObject(AOYISIMCardAPI.querySIMCard(simCardInfo.getIccid()));
////					if(!"0000".equals(jsonobject.get("status"))){
////						return new ReturnData(204,jsonobject1.get("message").toString());
////					}
////					JSONArray arrays=JSONArray.fromObject(jsonobject.get("result"));
////					JSONObject jsonobject2=arrays.getJSONObject(0);
////					if("00".equals(jsonobject2.get("cardstatus").toString())){
////						sIMCardView.setCardStatus(true);
////					}
////					sIMCardView.setImei(deviceId);	
////					sIMCardViewList.add(sIMCardView);
////				}
////				}
////				return new ReturnData(200,"成功",sIMCardViewList);
////			}
////			//只有一个
////			SimInfo simInfo=deviceInfoService.selectICCIDByDeviceId(imei);
////			if(simInfo==null){
////				return new ReturnData(203,"该卡号不存在");
////			}
////			JSONObject jsonobject1 = JSONObject.fromObject(AOYISIMCardAPI.queryCardInfo(simInfo.getICCID()));
////			if(!"0000".equals(jsonobject1.get("status"))){
////				return new ReturnData(203,jsonobject1.get("message").toString());
////			}
////			List<SIMCardView>simCardInfoList=new ArrayList<>();
////			JSONArray array=JSONArray.fromObject(jsonobject1.get("result"));
////			
////			SIMCardInfo simCardInfo=(SIMCardInfo) JSONObject.toBean(array.getJSONObject(0), SIMCardInfo.class);
////			JSONObject jsonobject = JSONObject.fromObject(AOYISIMCardAPI.querySIMCard(simCardInfo.getIccid()));
////			if(!"0000".equals(jsonobject.get("status"))){
////				return new ReturnData(204,jsonobject1.get("message").toString());
////			}
////			JSONArray arrays=JSONArray.fromObject(jsonobject.get("result"));
////			JSONObject jsonobject2=arrays.getJSONObject(0);
////			SIMCardView sIMCardView=new SIMCardView();	
////
////			if("00".equals(jsonobject2.get("cardstatus").toString())){
////				sIMCardView.setCardStatus(true);
////			}
////			sIMCardView.setImei(imei);
////			simCardInfoList.add(sIMCardView);
////			return new ReturnData(200,"成功",simCardInfoList);
////		}else if((card!=null&&card!="")||((card!=null||card!="")&&(imei==null||imei==""))){//不管imei是否存在 直接查卡号
////			JSONObject jsonobject1 = JSONObject.fromObject(AOYISIMCardAPI.queryCardInfo(card));
////			if(!"0000".equals(jsonobject1.get("status"))){
////				return new ReturnData(203,jsonobject1.get("message").toString());
////			}
////			List<SIMCardInfo>simCardInfoList=new ArrayList<>();
////			JSONArray array=JSONArray.fromObject(jsonobject1.get("result"));
////			for(int i=0;i<array.size();i++){
////				SIMCardInfo simCardInfo=(SIMCardInfo) JSONObject.toBean(array.getJSONObject(i), SIMCardInfo.class);
////				JSONObject jsonobject = JSONObject.fromObject(AOYISIMCardAPI.querySIMCard(simCardInfo.getIccid()));
////				if(!"0000".equals(jsonobject.get("status"))){
////					return new ReturnData(204,jsonobject1.get("message").toString());
////				}
////				JSONArray arrays=JSONArray.fromObject(jsonobject.get("result"));
////				JSONObject jsonobject2=arrays.getJSONObject(0);
////				simCardInfo.setCardstatus(jsonobject2.get("cardstatus").toString());
////				simCardInfo.setGprsstatus(jsonobject2.get("gprsstatus").toString());
////				simCardInfo.setMacstatus(jsonobject2.get("macstatus").toString());
////				simCardInfo.setMonthFlow(jsonobject2.get("flow").toString());
////				simCardInfo.setMsisdn(jsonobject2.get("cardcode").toString());
////				simCardInfoList.add(simCardInfo);
////			}
////			return new ReturnData(200,"成功",simCardInfoList);
////		}else{
////			return new ReturnData(205,"请输入卡号、iccid、imei、imsi任意一个号码！");
////		}		
//		if(imei==null||imei==""){
//			return new ReturnData(210,"imei不能为空");
//		}
//		logger.info("/sim/sim_query_cardstatus====="+imei);
//		String[] imeis=imei.trim().split(",");
//		List<SIMCardView>simCardInfoList=new ArrayList<>();
//		for(String deviceId:imeis){
//			SIMCardInfo simInfo=simCardInfoService.selectByImei(deviceId);
//			if(simInfo!=null){
//				
//			if(simInfo.getOperator()==1){//奥一移动
//				
//			JSONObject jsonobject = JSONObject.fromObject(AOYISIMCardAPI.querySIMCard(simInfo.getIccid()));
//			if(!"0000".equals(jsonobject.get("status"))){
//				return new ReturnData(204,jsonobject.get("message").toString());
//			}
//			JSONArray arrays=JSONArray.fromObject(jsonobject.get("result"));
//			JSONObject jsonobject2=arrays.getJSONObject(0);
//			SIMCardView simCardView=new SIMCardView();
//			if("00".equals(jsonobject2.get("cardstatus").toString())){
//				simCardView.setCardStatus(true);
//			}else{
//				simCardView.setCardStatus(false);
//			}
//			simCardView.setImei(deviceId);
//			simCardInfoList.add(simCardView);
//			
//			}else if(simInfo.getOperator()==2){//宝安移动
//				String result=SIMCardAPI.queryCardInfo(simInfo.getIccid(), Constant.QUERY_SINGLE_CARDINFO, null, null, null);
//				logger.info("result:"+result);
//				if(result==null||result==""){
//					return new ReturnData(208, "查询失败，请重试！");
//				}
//				JSONObject jsonobject = JSONObject.fromObject(result);
//				if(!"0".equals(jsonobject.get("code"))){
//					return new ReturnData(204,jsonobject.get("error").toString());
//				}
//				JSONObject jsonobject1 = JSONObject.fromObject(jsonobject.get("data"));
//				logger.info("", jsonobject1);
//				SIMCardView simCardView=new SIMCardView();
//						simCardView.setImei(deviceId);
//						//simCardView.setIccid(jsonobject1.getString("iccid"));
//						if("停机".equals(jsonobject1.getString("status"))||"预约销户".equals(jsonobject1.getString("status"))){
//							simCardView.setCardStatus(false);
//						}else{
//							simCardView.setCardStatus(true);
//						}
//						simCardInfoList.add(simCardView);
//			}else if(simInfo.getOperator()==3){//泰利诺
//				
//			}
//			}
//		}
//		return new ReturnData(200,"成功",simCardInfoList);		
//	}
//	
//		@RequestMapping(value="/sim_query_aoyiCardInfo",method=RequestMethod.POST)
//		@ResponseBody
//		public Object queryAOYIAllSIMCardInfoByMsisdnOrIMEI(Integer page,Integer operate){
//				return simCardInfoService.selectAll(page,operate);	
//		}
//		
//		@RequestMapping(value="/sim_query_cardstatus",method=RequestMethod.GET)
//		@ResponseBody
//		public Object queryCardStatus(String imei){
//			if(imei==null||imei==""){
//				return new ReturnData(210,"imei不能为空");
//			}
//			logger.info("/sim/sim_query_cardstatus====="+imei);
//			String[] imeis=imei.trim().split(",");
//			logger.info("imeis=========:"+imeis.toString());
//			List<SIMCardView>simCardInfoList=new ArrayList<>();
//			for(String deviceId:imeis){
//				logger.info("deviceId=========:"+deviceId.toString());
//				SIMCardInfo simInfo=simCardInfoService.selectByImei(deviceId);
//				logger.info("simInfo=========:"+simInfo);
//				if(simInfo!=null){	
//				if(simInfo.getOperator()==1){//奥一移动	
//				JSONObject jsonobject = JSONObject.fromObject(AOYISIMCardAPI.querySIMCard(simInfo.getIccid()));
//				if(!"0000".equals(jsonobject.get("status"))){
//					return new ReturnData(204,jsonobject.get("message").toString());
//				}
//				JSONArray arrays=JSONArray.fromObject(jsonobject.get("result"));
//				JSONObject jsonobject2=arrays.getJSONObject(0);
//				SIMCardView simCardView=new SIMCardView();
//				if("00".equals(jsonobject2.get("cardstatus").toString())){
//					simCardView.setCardStatus(true);
//				}else{
//					simCardView.setCardStatus(false);
//				}
//				simCardView.setImei(deviceId);
//				simCardInfoList.add(simCardView);
//				
//				}else if(simInfo.getOperator()==2){//宝安移动
//					String result=SIMCardAPI.queryCardInfo(simInfo.getIccid(), Constant.QUERY_SINGLE_CARDINFO, null, null, null);
//					logger.info("result:"+result);
//					JSONObject jsonobject = JSONObject.fromObject(result);
//					if(!"0".equals(jsonobject.get("code"))){
//						return new ReturnData(204,jsonobject.get("error").toString());
//					}
//					JSONObject jsonobject1 = JSONObject.fromObject(jsonobject.get("data"));
//					logger.info("", jsonobject1);
//					SIMCardView simCardView=new SIMCardView();
//							simCardView.setImei(deviceId);
//							//simCardView.setIccid(jsonobject1.getString("iccid"));
//							if("停机".equals(jsonobject1.getString("status"))||"预约销户".equals(jsonobject1.getString("status"))){
//								simCardView.setCardStatus(false);
//							}else{
//								simCardView.setCardStatus(true);
//							}
//							simCardInfoList.add(simCardView);
//				}else if(simInfo.getOperator()==3){//泰利诺
//					
//				}
//				}
//			}
//			return new ReturnData(200,"成功",simCardInfoList);		
//		}
//		@RequestMapping(value="/sim_query_cardInfo",method=RequestMethod.POST)
//		@ResponseBody
//		public Object querySIMCardInfoByIMEIAndOperate(String imei,Integer operate){
//				return simCardInfoService.selectByImeiAndOperate(imei, operate);
//		}
//		
//		@RequestMapping(value="/sim_query_by_id")
//		@ResponseBody
//		public Object querySIMCardById(String list,String uid){
//			List<Long> idList=new ArrayList<>();
//			logger.info("list:"+list);
//			String[] arr=list.split(",");
//			for(String id:arr){
//				System.out.println(id);
//				if(id.contains("[")){
//					id=id.substring(1, id.length()).trim();
//					System.out.println("id:"+id);
//				}
//				if(id.contains("]")){
//					id=id.substring(0, id.length()-1).trim();
//					System.out.println("id:"+id);
//				}
//				idList.add(Long.parseLong(id));
//			}
//			if(uid==null||uid==""){
//				return new ReturnData(301, "用户信息为空");
//			}
//			return simCardInfoService.selectById(idList,Long.parseLong(uid));
//		}
//	
//}
