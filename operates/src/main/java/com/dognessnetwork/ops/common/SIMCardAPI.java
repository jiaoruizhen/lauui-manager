package com.dognessnetwork.ops.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dognessnetwork.ops.domain.SIMCard;
import com.dognessnetwork.ops.dto.SIMCardView;
import com.dognessnetwork.ops.utils.DescUtil;
import com.dognessnetwork.ops.utils.HttpRequestUtil;
import com.dognessnetwork.ops.utils.SignUtil;
import com.dognessnetwork.ops.utils.StringToolsUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SIMCardAPI {

	public static SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	/**
	 * 解析单个号码结果
	 * @param result
	 * @return
	 */
	public static SIMCard parseRresult(String result){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject jsonobject = JSONObject.fromObject(result);
		if(!"0".equals(jsonobject.get("code").toString())){
			return null;
		}
		SIMCard sim=new SIMCard();
		sim.setMsisdn(jsonobject.get("msisdn").toString());
		sim.setIccid(jsonobject.get("iccid").toString());
		//1测试期、2沉默期、3库存期、4正使用、5停机、6预约销户
		if("测试期".equals(jsonobject.get("status").toString())){
			sim.setStatus(1);	
		}
		if("沉默期".equals(jsonobject.get("status").toString())){
			sim.setStatus(2);	
		}
		if("库存期".equals(jsonobject.get("status").toString())){
			sim.setStatus(3);	
		}
		if("正使用".equals(jsonobject.get("status").toString())){
			sim.setStatus(4);	
		}
		if("停机".equals(jsonobject.get("status").toString())){
			sim.setStatus(5);	
		}
		if("预约销户".equals(jsonobject.get("status").toString())){
			sim.setStatus(6);	
		}
		
		JSONObject array=JSONObject.fromObject(jsonobject.get("packages"));
		JSONArray list=JSONArray.fromObject(array.get("list"));
		sim.setPackageInfo(list.toString());
		try {
	
			sim.setOpenTime(sdf1.format(sdf.parse(jsonobject.get("openTime").toString())));//mainProdCodeproductCode
			sim.setStatusTime(sdf1.format(sdf.parse(jsonobject.get("statusTime").toString())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return sim;
	}
	
/**
 * 封装查询方法
 * @param msisdns
 * @param iccids
 * @param imeis
 * @param imsis
 * @param method
 * @return
 */
	public static String  queryCardInfo(String iccids,String method,String month,String date,String groupCode){
		
		Map<String,String>map=new HashMap<String,String>();
		
		map.put("v", Constant.BAOAN_VERSION);
		map.put("appKey",Constant.APPKEY);
		map.put("format",Constant.BAOAN_FORMAT);
		
	String transID=Constant.TRANSID+fmt.format(new Date())+StringToolsUtil.createNoncestr(4);	
		map.put("transID",transID);
		map.put("method", method);
		String param ="";
		if(groupCode==null){
			if(iccids.contains(",")){
				map.put("iccids", iccids.substring(0, iccids.lastIndexOf(",")).trim());
				if(month!=null){
					map.put("month", month.trim());
				}
				if(date!=null){
					map.put("date", date.trim());
				}
				String sign = SignUtil.sign(map, Constant.SECRET);
				param = "transID="+transID+"&format="+Constant.BAOAN_FORMAT+"&v="+Constant.BAOAN_VERSION+"&appKey="+Constant.APPKEY+"&method="+method+"&iccids="+iccids.substring(0, iccids.lastIndexOf(",")).trim()+"&sign="+sign;
				System.out.println(sign);
			}else{
				map.put("iccid", iccids.trim());
				if(month!=null){
					map.put("month", month.trim());
				}
				if(date!=null){
					map.put("date", date.trim());
				}
				String sign = SignUtil.sign(map, Constant.SECRET);
				param = "transID="+transID+"&format="+Constant.BAOAN_FORMAT+"&v="+Constant.BAOAN_VERSION+"&appKey="+Constant.APPKEY+"&method="+method+"&iccid="+iccids.trim()+"&sign="+sign;
				System.out.println(sign);
			}
			
		}else{
			map.put("groupCode", groupCode);
			if(month!=null){
				map.put("month", month.trim());
				String sign = SignUtil.sign(map, Constant.SECRET);
				param = "transID="+transID+"&format="+Constant.BAOAN_FORMAT+"&v="+Constant.BAOAN_VERSION+"&appKey="+Constant.APPKEY+"&method="+method+"&groupCode="+groupCode.trim()+"&sign="+sign+"&month="+month;
			}else{
				String sign = SignUtil.sign(map, Constant.SECRET);
				param = "transID="+transID+"&format="+Constant.BAOAN_FORMAT+"&v="+Constant.BAOAN_VERSION+"&appKey="+Constant.APPKEY+"&method="+method+"&groupCode="+groupCode.trim()+"&sign="+sign;
				System.out.println(sign);
			}
		}
		String result="";
		
		try {
			result=DescUtil.decrypt(HttpRequestUtil.load(Constant.BAOAN_MOBILE_URL,param), Constant.SECRET);
			//System.out.println(DescUtil.decrypt(HttpRequestUtil.load(Constant.BAOAN_MOBILE_URL, param), Constant.SECRET));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}
	/**
	 * 封装查询卡号方法
	 * @param msisdns
	 * @param iccids
	 * @param imeis
	 * @param imsis
	 * @return
	 */
	public static String queryCardID(String msisdns,String iccids,String imeis,String imsis){
		Map<String,String>map=new HashMap<String,String>();
		map.put("v", Constant.BAOAN_VERSION);
		map.put("appKey",Constant.APPKEY);
		map.put("format",Constant.BAOAN_FORMAT);
		String date=Constant.TRANSID+fmt.format(new Date())+StringToolsUtil.createNoncestr(4);	
		map.put("transID",date);
		map.put("method", Constant.QUERY_BATCH_CARDID);
		String result="";
	if(iccids != null&&iccids !="") {
		map.put("iccids", iccids.trim());
		String sign = SignUtil.sign(map, Constant.SECRET);
		String param = "?transID="+date+"&format="+Constant.BAOAN_FORMAT+"&v="+Constant.BAOAN_VERSION+"&appKey="+Constant.APPKEY+"&method="+Constant.QUERY_BATCH_CARDID+"&iccids="+iccids.trim()+"&sign="+sign;
		System.out.println(sign);
		try {
			result=DescUtil.decrypt(HttpRequestUtil.sendPost(Constant.BAOAN_MOBILE_URL+param), Constant.SECRET);
			//System.out.println(DescUtil.decrypt(HttpRequestUtil.load(Constant.BAOAN_MOBILE_URL, param), Constant.SECRET));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		
		return result;
	}else
	if(msisdns != null&&msisdns !="") {
		map.put("msisdns", msisdns.trim());
		String sign = SignUtil.sign(map, Constant.SECRET);
		String param = "?transID="+date+"&format="+Constant.BAOAN_FORMAT+"&v="+Constant.BAOAN_VERSION+"&appKey="+Constant.APPKEY+"&method="+Constant.QUERY_BATCH_CARDID+"&msisdns="+msisdns.trim()+"&sign="+sign;
		System.out.println(sign);
		try {
			result=DescUtil.decrypt(HttpRequestUtil.sendPost(Constant.BAOAN_MOBILE_URL+param), Constant.SECRET);
			//System.out.println(DescUtil.decrypt(HttpRequestUtil.load(Constant.BAOAN_MOBILE_URL, param), Constant.SECRET));
		} catch (Exception e) {
			e.printStackTrace();
		}
System.out.println(result);
		
		return result;
	}else
	if(imeis != null&&imeis !="") {
		map.put("imeis", imeis.trim());
		String sign = SignUtil.sign(map, Constant.SECRET);
		String param = "?transID="+date+"&format="+Constant.BAOAN_FORMAT+"&v="+Constant.BAOAN_VERSION+"&appKey="+Constant.APPKEY+"&method="+Constant.QUERY_BATCH_CARDID+"&imeis="+imeis.trim()+"&sign="+sign;
		System.out.println(sign);
		try {
			result=DescUtil.decrypt(HttpRequestUtil.sendPost(Constant.BAOAN_MOBILE_URL+param), Constant.SECRET);
			//System.out.println(DescUtil.decrypt(HttpRequestUtil.load(Constant.BAOAN_MOBILE_URL, param), Constant.SECRET));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
System.out.println(result);
		
		return result;
	}else
	if(imsis != null&&imsis !="") {
		map.put("imsis", imsis.trim());
		String sign = SignUtil.sign(map, Constant.SECRET);
		String param = "?transID="+date+"&format="+Constant.BAOAN_FORMAT+"&v="+Constant.BAOAN_VERSION+"&appKey="+Constant.APPKEY+"&method="+Constant.QUERY_BATCH_CARDID+"&imsis="+imsis.trim()+"&sign="+sign;
		System.out.println(sign);
		try {
			result=DescUtil.decrypt(HttpRequestUtil.sendPost(Constant.BAOAN_MOBILE_URL+param), Constant.SECRET);
			//System.out.println(DescUtil.decrypt(HttpRequestUtil.load(Constant.BAOAN_MOBILE_URL, param), Constant.SECRET));
		} catch (Exception e) {
			e.printStackTrace();
		}
			System.out.println(result);
		
		return result;
		}else{
			return null;	
		}	
	}
	/**
	 * 查询单个号码的余额
	 * @param msisdn
	 * @param method
	 * @return
	 */
	public static String querySingleBalance(String msisdn,String method){
		Map<String,String>map=new HashMap<String,String>();
		
		map.put("v", Constant.BAOAN_VERSION);
		map.put("appKey",Constant.APPKEY);
		map.put("format",Constant.BAOAN_FORMAT);
		
		String transID=Constant.TRANSID+fmt.format(new Date())+StringToolsUtil.createNoncestr(4);	
		map.put("transID",transID);
		map.put("method", method);
		String result="";
		if(msisdn==null||msisdn==""){
			return null;
		}else{
			map.put("msisdn", msisdn.trim());
			String sign = SignUtil.sign(map, Constant.SECRET);
			String param = "?transID="+transID+"&format="+Constant.BAOAN_FORMAT+"&v="+Constant.BAOAN_VERSION+"&appKey="+Constant.APPKEY+"&method="+method+"&msisdn="+msisdn.trim()+"&sign="+sign;
			System.out.println(sign);
			
			try {
				result=DescUtil.decrypt(HttpRequestUtil.sendPost(Constant.BAOAN_MOBILE_URL+param), Constant.SECRET);
				System.out.println(DescUtil.decrypt(HttpRequestUtil.load(Constant.BAOAN_MOBILE_URL, param), Constant.SECRET));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
		
	}
	
	/**
	 * 解析批量卡号查询的结果
	 * @param result
	 * @return
	 */
	public static List<SIMCardView> parseResult(String result){
		System.out.println(result);
		if(result==""||result==null){
			return null;
		}
		JSONObject jsonobject = JSONObject.fromObject(result);
		JSONObject jsonobject1 = JSONObject.fromObject(jsonobject);
//		SIMCard sim=(SIMCard) JSONObject.toBean(JSONArray.fromObject(JSONObject.fromObject(JSONObject.fromObject(jsonobject1.get("data")).get("msisdnList")).get("list")), SIMCard.class);
		if(!"0".equals(jsonobject.get("code"))){
			return null;
		}
		JSONArray json=JSONArray.fromObject(JSONObject.fromObject(JSONObject.fromObject(jsonobject1.get("data")).get("msisdnList")).get("list"));
		List<SIMCardView>list =new ArrayList<SIMCardView>();
		if(json.size()>0){  
			  for(int i=0;i<json.size();i++){
				  SIMCardView sv=(SIMCardView) JSONObject.toBean(json.getJSONObject(i),SIMCardView.class);
				  list.add(sv);
			  }
		}
		return list;
	}
}
