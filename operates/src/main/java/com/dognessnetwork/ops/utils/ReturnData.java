package com.dognessnetwork.ops.utils;

public class ReturnData {
	private int code;
	private String msg;
	private Object data;
	private String string;
	private Integer status;
	private Object datas;
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}
	
	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ReturnData(int code,String msg) {
		this.code=code;
		this.msg=msg;
	}

	public Object getDatas() {
		return datas;
	}

	public void setDatas(Object datas) {
		this.datas = datas;
	}

	public ReturnData(int code, String msg, Object object) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = object;
	}
	
	public ReturnData(int code, String msg, String String) {
		this.code = code;
		this.msg = msg;
		this.string = String;
	}
	
	public ReturnData(int code, String msg, Object data, String string) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.string = string;
	}

	public ReturnData(int code, String msg, Integer status) {
		super();
		this.code = code;
		this.msg = msg;
		this.status = status;
	}

	public ReturnData(int code, String msg, Object data, String string, Integer status) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.string = string;
		this.status = status;
	}

	public ReturnData(int code, String msg, Object data, Integer status) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.status = status;
	}
	public ReturnData(int code, String msg, Object data,Object datas) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.datas = datas;	
	}

	@Override
	public String toString() {
		return "ReturnData [code=" + code + ", msg=" + msg + ", data=" + data + ", string=" + string + ", status="
				+ status + ", datas=" + datas + "]";
	}
	

	
	

}
