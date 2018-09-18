package com.dognessnetwork.ops.dto;

public enum APIStatus {
	
	SUCCESS(1000, "success"),
	FAILURE(2000, "fail"),
	USERNAME_NOT_EXIST(4101, "用户名不存在！"),
	USERNAME_IS_NULL(4102,"用户名为空！"),
	USERNAME_IS_EXIST(4103, "用户名已存在！"),
	
	ROLE_NOT_EXIST(4104, "该角色不存在！"),
	ROLE_IS_EXIST(4105, "该角色已存在！"),
	
	USER_NOT_EXIST(4106,"该用户不存在！"),
	
	
	RESOURCE_NOT_EXIST(4107,"该权限不存在！"),
	DEVICE_BIND_SUCCESS(4200, "device bind success"),
	DEVICE_BIND_FAILURE(4201, "device bind fail"),

	LOGIN_TIMEOUT(1001,"login timeout"),
	PARAM_IS_EMPTY(1003,"param is null"),
	DEVICE_HAS_BINDED_USER(4102, "device is bind user"),
	DEVICE_NOT_BIND_USER(4103, "device not bind user"),
	
	DEVICE_HAS_BINDED_PET(4104, "device is bind pet "),
	DEVICE_NOT_BIND_PET(4105, "device not bind pet"),
	
	INVALID_PARAMETER(4106,"invalid_parameter"),
	INSERT_DUPLICATE(4107, "insert repeat"),
	INCORRECT_STRING(4108,"UncategorizedSQLException Incorrect string value"),
	DATA_TOOLONG(4109,"Data too long for column"),
	
	INVALID_SIGN(4110, "invalid sign"), 
	INTERNAL_SERVER_ERROR(500,"internal_server_error"),
	INVALID_TOKEN(4111,"invalid token"), //用户token错误
	ERROR_3026(3026, "The user account has not been activated. Mail has been Resend! Please enter your mail box to activate."),
	PET_NOT_EXIST(4112, "pet not exist"),	
	PUBLISH_EXIST(4113, "publish is exist"),	
	PUBLISH_NOT_EXIST(4114, "publish not exist"),
	PRODUCTER_HAS_NONE_PRODUCTER(4115,"this producter does not have products"),
	ERROR_3019(3019, "Missing x-auth-token or invalid, reloading correctly and retrying!"),
	;

	private int status;
	private String message;

	private APIStatus(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

}
