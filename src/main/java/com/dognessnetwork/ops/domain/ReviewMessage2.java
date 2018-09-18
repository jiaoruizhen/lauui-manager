package com.dognessnetwork.ops.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="review_message2")
public class ReviewMessage2 {

	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String iccid;
	
	private String imei;
	
	private String cardStatus;
	
	private String meals;
	
	private String flow;
	
	private String billingcycle;
	
	private String billingenddate;
	
	private Long operator;

	private Long userId;
	
	//评审状态：0、未提交   1、运营经理审核  2、总经办审核    3、运营人员留档
	private Integer status;
	
	private Long renewaer;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRenewaer() {
		return renewaer;
	}

	public void setRenewaer(Long renewaer) {
		this.renewaer = renewaer;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getMeals() {
		return meals;
	}

	public void setMeals(String meals) {
		this.meals = meals;
	}

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	public String getBillingcycle() {
		return billingcycle;
	}

	public void setBillingcycle(String billingcycle) {
		this.billingcycle = billingcycle;
	}

	public String getBillingenddate() {
		return billingenddate;
	}

	public void setBillingenddate(String billingenddate) {
		this.billingenddate = billingenddate;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public Long getUser() {
		return userId;
	}

	public void setUser(Long userId) {
		this.userId = userId;
	}
	
	
}
