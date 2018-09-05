package com.dognessnetwork.ops.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.dognessnetwork.ops.utils.MD5Util;
import com.dognessnetwork.ops.utils.StringToolsUtil;

public class AOYISIMCardAPI {
	public static SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhhmmssSSS");

	public static String queryCardInfo(String card){
		String url="http://api.m2m10086.com:89/M2MSearchInfo.ashx";
		String requestTime=fmt.format(new Date())+StringToolsUtil.createNoncestr(8);
				String partnercode=Constant.AOYI_PARTNERCODE;
				String servicecode=Constant.AOYI_SERVICECODE;
				String password=Constant.AOYI_PASSWORD;
				String key=card;
				String signstr=password+requestTime+key;
				String sign=MD5Util.MD5Lower(signstr.toLowerCase()); //md5 32位小写加密  
				
				String soapRequestData="{" +
						"\"partnercode\":\""+partnercode+"\"" +
						",\"servicecode\":\""+servicecode+"\"" +
						",\"requesttime\":\""+requestTime+"\"" +
						",\"sign\":\""+sign+"\"" +
						",\"key\":\""+key+"\"" +
						"}";  
						 
				PostMethod postMethod = new PostMethod(url);
				// 然后把Soap请求数据添加到PostMethod中
				byte[] b=null;
				InputStream is=null;
				String soapResponseData="";
				try {
					b = soapRequestData.toString().getBytes("utf-8"); 
					is = new ByteArrayInputStream(b, 0, b.length);
					RequestEntity re = new InputStreamRequestEntity(is, b.length,"text/json; charset=UTF-8");
					postMethod.setRequestEntity(re);
					HttpClient httpClient = new HttpClient();
					int status = httpClient.executeMethod(postMethod);
					System.out.println("status:"+status);
					if(status==200){
						soapResponseData = postMethod.getResponseBodyAsString();
					}
				} catch (Exception e) {
					
					e.printStackTrace();
					
				} finally{
					if(is!=null){
						try {
							is.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				System.out.print(soapResponseData);
				return soapResponseData;
	}
	public static String querySIMCard(String card){
		String url="http://api.m2m10086.com:89/M2MSearchTermInfo.ashx";
		String requestTime=fmt.format(new Date())+StringToolsUtil.createNoncestr(8);
				String partnercode=Constant.AOYI_PARTNERCODE;
				String servicecode=Constant.AOYI_SERVICECODE;
				String password=Constant.AOYI_PASSWORD;
				String key=card;
				String status="1_2_3_4";
				String signstr=password+requestTime+key+status;
				String sign=MD5Util.MD5Lower(signstr.toLowerCase()); //md5 32位小写加密  
				
				String soapRequestData="{" +
						"\"partnercode\":\""+partnercode+"\"" +
						",\"servicecode\":\""+servicecode+"\"" +
						",\"requesttime\":\""+requestTime+"\"" +
						",\"sign\":\""+sign+"\"" +
						",\"key\":\""+key+"\"" +
						",\"status\":\""+status+"\"" +
						"}";  
						 
				PostMethod postMethod = new PostMethod(url);
				// 然后把Soap请求数据添加到PostMethod中
				byte[] b=null;
				InputStream is=null;
				String soapResponseData="";
				try {
					b = soapRequestData.toString().getBytes("utf-8"); 
					is = new ByteArrayInputStream(b, 0, b.length);
					RequestEntity re = new InputStreamRequestEntity(is, b.length,"text/json; charset=UTF-8");
					postMethod.setRequestEntity(re);
					HttpClient httpClient = new HttpClient();
					int code = httpClient.executeMethod(postMethod);
					System.out.println("code:"+code);
					if(code==200){
						soapResponseData = postMethod.getResponseBodyAsString();
					}
				} catch (Exception e) {
					
					e.printStackTrace();
					
				} finally{
					if(is!=null){
						try {
							is.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				System.out.print(soapResponseData);
				return soapResponseData;
		
	}
	
	public static void main(String []args) {

    	System.out.println(querySIMCard("89460800241057857767"));

   }
}
