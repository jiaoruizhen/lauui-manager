package com.dognessnetwork.ops.common;

import java.util.List;


public class AppConstant {
	public static final String APPKEY="wx21af39d03b12dd37";
	public static final String SECRET="7e98408b4acfde95b6f3f0a5934b7c4f";
	public static final String REDIRECT_URI="/social";
	public static final String REDIS_THIRD_PARTY = "redis_third_party:";
	
	public static final String HTTP_PROXY_SET="true";
	public static final String HTTP_PROXY_HOST="119.29.132.143";
	public static final String HTTP_PROXY_PORT="443";
	
	private static String host;
	private static String fileServerHost;
	private static String resetPassLink;
	private static String registerLink;
	private static String defaultPhotoRrl;
	private static String filePath;
	private static List<String> filterCheckList;
	private static boolean onlinePattern;
	
	
	
	public static String getFileServerHost() {
		return fileServerHost;
	}
	public void setFileServerHost(String fileServerHost) {
		AppConstant.fileServerHost = fileServerHost;
	}
	public static String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		AppConstant.filePath = filePath;
	}
	public static List<String> getFilterCheckList() {
		return filterCheckList;
	}
	public void setFilterCheckList(List<String> filterCheckList) {
		AppConstant.filterCheckList = filterCheckList;
	}
	public static boolean getOnlinePattern() {
		return onlinePattern;
	}
	public void setOnlinePattern(boolean onlinePattern) {
		AppConstant.onlinePattern = onlinePattern;
	}
	public static String getHost() {
		return host;
	}
	public void setHost(String host) {
		AppConstant.host = host;
	}
	public static String getResetPassLink() {
		return resetPassLink;
	}
	public void setResetPassLink(String resetPassLink) {
		AppConstant.resetPassLink = resetPassLink;
	}
	public static String getRegisterLink() {
		return registerLink;
	}
	public void setRegisterLink(String registerLink) {
		AppConstant.registerLink = registerLink;
	}
	public static String getDefaultPhotoRrl() {
		return defaultPhotoRrl;
	}
	public void setDefaultPhotoRrl(String defaultPhotoRrl) {
		AppConstant.defaultPhotoRrl = defaultPhotoRrl;
	}
}
