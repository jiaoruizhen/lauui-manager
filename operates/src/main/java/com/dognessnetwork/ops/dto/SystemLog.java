package com.dognessnetwork.ops.dto;

import java.util.Date;

/**
 * @Description:
 * @Author: vesus
 * @CreateDate: 2018/5/20 上午11:12
 * @Version: 1.0
 */
public class SystemLog {


    private String requestip; //操作IP

    private String type ;//  操作类型 1 操作记录 2异常记录

    private String description;// 操作描述

    private Date actiondate ;// 操作时间

    private String exceptioncode ;// 异常code

    private String exceptiondetail ;// 异常详情

    private String actionmethod ;//请求方法

    private String params;//请求参数
    
    private String tableName;
    
    private String actionType;
    

    public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getRequestip() {
        return requestip;
    }

    public void setRequestip(String requestip) {
        this.requestip = requestip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getActiondate() {
        return actiondate;
    }

    public void setActiondate(Date actiondate) {
        this.actiondate = actiondate;
    }

    public String getExceptioncode() {
        return exceptioncode;
    }

    public void setExceptioncode(String exceptioncode) {
        this.exceptioncode = exceptioncode;
    }

    public String getExceptiondetail() {
        return exceptiondetail;
    }

    public void setExceptiondetail(String exceptiondetail) {
        this.exceptiondetail = exceptiondetail;
    }

    public String getActionmethod() {
        return actionmethod;
    }

    public void setActionmethod(String actionmethod) {
        this.actionmethod = actionmethod;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

}
