package com.dognessnetwork.ops.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 静态常量
 * 系统使用账号及全局参数
 * @author Administrator
 *
 */
public class Constant {
	
    public final static Logger logger = LoggerFactory.getLogger(Constant.class);
    
	//设备服务路径
	public static String HTTP_KEY = "";
	public static String TEST_CN_URL = "";
	public static String H5_PARENT_URL = "";
	public static Integer QR_SIZE=400;
	public static String FILE_PATH="/usr/local/apache-tomcat-8.5.28/webapps/img/createQrCode/";
	public static final String LOGIN_FAIL_COUNT = "login_fail_count";
	public final static String TOKEN = "x-auth-token";
	public static final String REGISTER_TOKEN = "register_token:";
	public static final String REGISTLINK="http://localhost:8888/users/regist";
	public static final String DEFAULT_LOGIN_JSON_URL = "/page/login.html";
    /**
     * 默认的用户名密码登录请求处理url
     */
	public final static String DEFAULT_LOGIN_URL = "/authentication/login";
	static {
		try {
			Properties prop = new Properties();
			InputStream in = Constant.class.getClassLoader().getResourceAsStream("config.properties");
			prop.load(in);
			HTTP_KEY = prop.getProperty("HTTP_KEY");
			TEST_CN_URL = prop.getProperty("TEST_CN_URL");
			H5_PARENT_URL = prop.getProperty("H5_PARENT_URL");
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	
	
	//宝安移动
	public static final String BAOAN_MOBILE_URL="http://120.197.89.173:8081/openapi/router";
	//public static final String BAOAN_SECRET="4a39e761fb96f8331e44b816eada21da";
	public static final String BAOAN_FORMAT="json";
	public static final String BAOAN_VERSION="3.0";
	//奥一
	public static final String AOYI_PARTNERCODE="3364";
	public static final String AOYI_SERVICECODE="g8brac";
	public static final String AOYI_PASSWORD="3364g8brac";
	
	
	
	//多尼斯
	public static final String APPKEY = "qtah5sy9w4";
	public static final String SECRET = "4a39e761fb96f8331e44b816eada21da";
	public static final String TRANSID="2002674365";
	//航晟
//	public static final String APPKEY = "z45ggerbka";
//	public static final String SECRET = "bb0ca352c408e20bb4d1a9dd32a250dc";
//	public static final String TRANSID="2002693218";
	//正向代理配置
	public static final String HTTP_PROXY_SET="true";
	public static final String HTTP_PROXY_HOST="119.29.132.143";
	public static final String HTTP_PROXY_PORT="443";
	
	//查询单个卡号的信息
	public static final String QUERY_SINGLE_CARDINFO="triopi.member.info.single.query";
	//查询单个卡号的相关号码 triopi.member.iccid.single.query
	public static final String QUERY_SINGLE_CARDID="triopi.member.iccid.single.query";
	//单个号码激活时间查询triopi.member.actitime.single.query
	public static final String QUERY_SINGLE_CARD_ACTITIME="triopi.member.actitime.single.query";
	
	
	//批量查詢卡号信息查询 triopi.member.info.batch.query
	public static final String QUERY_BATCH_CARDINFO="triopi.member.info.batch.query";
	//批量查询卡号
	public static final String QUERY_BATCH_CARDID="triopi.member.iccid.batch.query";
	//批量查询卡号激活时间
	public static final String QUERY_BATCH_CARD_ACTITIME="triopi.member.actitime.batch.query";
	//批量查询卡号月流量情况 triopi.member.dailyflow.realtime.batch.query
	public static final String QUERY_BATCH_CARD_DAILYFLOW="triopi.member.dailyflow.realtime.batch.query";
	//批量查询卡号日流量情况 
	public static final String QUERY_BATCH_CARD_DAYFLOW="triopi.member.dailyflow.batch.query";
	//批量查询卡号历史日流量情况
	public static final String QUERY_BATCH_CARD_HISTORY_DAYFLOW="triopi.member.dailyflow.batch.history.query";
	//批量卡号流量池流量查询
	public static final String QUERY_BATCH_CARD_POOL_USEDFLOW="triopi.pool.member.usedflow.batch.query";
	//批量号码月账单查询
	public static final String QUERY_BATCH_CARD_MONTHFEE="triopi.member.monthfee.batch.query";
	
	//单个号码余额查询
	public static final String QUERY_SINGLE_CARD_BALANCE="triopi.member.balance.query";
	
	//集团流量池查询
	public static final String QUERY_GROUP_POOL_USEDFLOW="triopi.pool.usedflow.query";
	//集团月账单查询
	public static final String QUERY_GROUP_MONTHFEE="triopi.group.monthfee.query";
	//全量查询卡号信息
	public static final String QUERY_GROUP_ALLCARDINFO="triopi.member.info.all.query";
	

	//sim卡实时信息查询
		public static final String AOYI_MOBILE_SEARCHTERMTERMINFO="http://api.m2m10086.com:89/M2MSearchTermInfo.ashx";
		//sim卡基本信息查询
		public static final String AOYI_MOBILE_SEARCHINFO="http://api.m2m10086.com:89/M2MSearchInfo.ashx";
		//查询剩余流量
		public static final String AOYI_MOBILE_RESTFLOW="http://api.m2m10086.com:89/M2MSurplusFlow.ashx";
	
}
