package com.dognessnetwork.ops.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sim_card")
public class SIMCard {
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	//号码
	private String msisdn;
	//开户时间
	private String openTime;
	//1测试期、2沉默期、3库存期、4正使用、5停机、6预约销户
	private Integer status;
	//状态更新时间
	private String statusTime;
	//APN名称（全部小写）
	private String apnName;
	private String iccid;
	private String imsi;
	private String imei;
	//套餐情况
	private String packageInfo;
	//流量使用情况
	private String flowInfo;
	
	private String balance;
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getFlowInfo() {
		return flowInfo;
	}

	public void setFlowInfo(String flowInfo) {
		this.flowInfo = flowInfo;
	}

	public String getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(String packageInfo) {
		this.packageInfo = packageInfo;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusTime() {
		return statusTime;
	}
	public void setStatusTime(String statusTime) {
		this.statusTime = statusTime;
	}
	public String getApnName() {
		return apnName;
	}
	public void setApnName(String apnName) {
		this.apnName = apnName;
	}

	@Override
	public String toString() {
		return "SIMCard [id=" + id + ", msisdn=" + msisdn + ", openTime=" + openTime + ", status=" + status
				+ ", statusTime=" + statusTime + ", apnName=" + apnName + ", iccid=" + iccid + ", imsi=" + imsi
				+ ", imei=" + imei + ", packageInfo=" + packageInfo + ", flowInfo=" + flowInfo + ", balance=" + balance
				+ "]";
	}

	

	
	
	
}
