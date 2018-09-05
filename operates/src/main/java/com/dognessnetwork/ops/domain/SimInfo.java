package com.dognessnetwork.ops.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sim_info")
public class SimInfo {
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	
	 
    private String ICCID;
	
	
    private Integer status;
	 
	
    private String activateTime;
	 
	
    private String stopTime;
	 
	
    private String updateTime;
	 

    private String createTime;
	 
	
    private Long saleArea;
	 
	
    private Long simArea;
	 

    private String monthFlow;
	 

    private String iccidStatus;
	 
	
    private Long operator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getICCID() {
		return ICCID;
	}

	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getActivateTime() {
		return activateTime;
	}

	public void setActivateTime(String activateTime) {
		this.activateTime = activateTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getSaleArea() {
		return saleArea;
	}

	public void setSaleArea(Long saleArea) {
		this.saleArea = saleArea;
	}

	public Long getSimArea() {
		return simArea;
	}

	public void setSimArea(Long simArea) {
		this.simArea = simArea;
	}

	public String getMonthFlow() {
		return monthFlow;
	}

	public void setMonthFlow(String monthFlow) {
		this.monthFlow = monthFlow;
	}

	public String getIccidStatus() {
		return iccidStatus;
	}

	public void setIccidStatus(String iccidStatus) {
		this.iccidStatus = iccidStatus;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "SimInfo [id=" + id + ", ICCID=" + ICCID + ", status=" + status + ", activateTime=" + activateTime
				+ ", stopTime=" + stopTime + ", updateTime=" + updateTime + ", createTime=" + createTime + ", saleArea="
				+ saleArea + ", simArea=" + simArea + ", monthFlow=" + monthFlow + ", iccidStatus=" + iccidStatus
				+ ", operator=" + operator + "]";
	}
	 
	
	
}
