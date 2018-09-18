package com.dognessnetwork.ops.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="sim_card_info")
public class SIMCardInfo {
		@Id
	    @Column(name="id")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		private String msisdn;
		private String iccid;
		private String imsi;
		private String cardstatus;
		private String macstatus;
		private String gprsstatus;
		private String monthFlow;
		private String imei;
		private String mobile;
		private String meals;
		private String flow;
		private String activationdate;
		private String testenddate;
		private String silenceenddate;
		private String billingcycle;
		private String billingdate;
		private String billingenddate;
		private String paytype;
		//套餐情况
		@Size(max=1000)
		private String packageInfo;
		//流量使用情况
		private String flowInfo;
		
		private String balance;
		//开户时间
		private String openTime;
		//1测试期、2沉默期、3库存期、4正使用、5停机、6预约销户
		private Integer status;
		//状态更新时间
		private String statusTime;
		private Long operator;
		
		private String expireTime; // 过期时间
		private String expectRenewAt; // 预计续费至：2018-08-23
		private Boolean isRenewal; // 是否正在提交续费审批
		private @ManyToOne User renewalOper; // 提交续费审批操作人
		
		
		public String getExpectRenewAt() {
			return expectRenewAt;
		}
		public void setExpectRenewAt(String expectRenewAt) {
			this.expectRenewAt = expectRenewAt;
		}
		public Boolean getIsRenewal() {
			return isRenewal;
		}
		public void setIsRenewal(Boolean isRenewal) {
			this.isRenewal = isRenewal;
		}
		public User getRenewalOper() {
			return renewalOper;
		}
		public void setRenewalOper(User renewalOper) {
			this.renewalOper = renewalOper;
		}
		public String getExpireTime() {
			return expireTime;
		}
		public void setExpireTime(String expireTime) {
			this.expireTime = expireTime;
		}
		public Long getOperator() {
			return operator;
		}
		public void setOperator(Long operator) {
			this.operator = operator;
		}
		public String getPackageInfo() {
			return packageInfo;
		}
		public void setPackageInfo(String packageInfo) {
			this.packageInfo = packageInfo;
		}
		public String getFlowInfo() {
			return flowInfo;
		}
		public void setFlowInfo(String flowInfo) {
			this.flowInfo = flowInfo;
		}
		public String getBalance() {
			return balance;
		}
		public void setBalance(String balance) {
			this.balance = balance;
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
		public String getFlow() {
			return flow;
		}
		public void setFlow(String flow) {
			this.flow = flow;
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
		public String getIccid() {
			return iccid;
		}
		public void setIccid(String iccid) {
			this.iccid = iccid;
		}
		public String getImsi() {
			return imsi;
		}
		public void setImsi(String imsi) {
			this.imsi = imsi;
		}
		public String getCardstatus() {
			return cardstatus;
		}
		public void setCardstatus(String cardstatus) {
			this.cardstatus = cardstatus;
		}
		public String getMacstatus() {
			return macstatus;
		}
		public void setMacstatus(String macstatus) {
			this.macstatus = macstatus;
		}
		public String getGprsstatus() {
			return gprsstatus;
		}
		public void setGprsstatus(String gprsstatus) {
			this.gprsstatus = gprsstatus;
		}
		public String getMonthFlow() {
			return monthFlow;
		}
		public void setMonthFlow(String monthFlow) {
			this.monthFlow = monthFlow;
		}
		public String getImei() {
			return imei;
		}
		public void setImei(String imei) {
			this.imei = imei;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getMeals() {
			return meals;
		}
		public void setMeals(String meals) {
			this.meals = meals;
		}
		public String getActivationdate() {
			return activationdate;
		}
		public void setActivationdate(String activationdate) {
			this.activationdate = activationdate;
		}
		public String getTestenddate() {
			return testenddate;
		}
		public void setTestenddate(String testenddate) {
			this.testenddate = testenddate;
		}
		public String getSilenceenddate() {
			return silenceenddate;
		}
		public void setSilenceenddate(String silenceenddate) {
			this.silenceenddate = silenceenddate;
		}
		public String getBillingcycle() {
			return billingcycle;
		}
		public void setBillingcycle(String billingcycle) {
			this.billingcycle = billingcycle;
		}
		public String getBillingdate() {
			return billingdate;
		}
		public void setBillingdate(String billingdate) {
			this.billingdate = billingdate;
		}
		public String getBillingenddate() {
			return billingenddate;
		}
		public void setBillingenddate(String billingenddate) {
			this.billingenddate = billingenddate;
		}
		public String getPaytype() {
			return paytype;
		}
		public void setPaytype(String paytype) {
			this.paytype = paytype;
		}
		@Override
		public String toString() {
			return "SIMCardInfo [id=" + id + ", msisdn=" + msisdn + ", iccid=" + iccid + ", imsi=" + imsi
					+ ", cardstatus=" + cardstatus + ", macstatus=" + macstatus + ", gprsstatus=" + gprsstatus
					+ ", monthFlow=" + monthFlow + ", imei=" + imei + ", mobile=" + mobile + ", meals=" + meals
					+ ", flow=" + flow + ", activationdate=" + activationdate + ", testenddate=" + testenddate
					+ ", silenceenddate=" + silenceenddate + ", billingcycle=" + billingcycle + ", billingdate="
					+ billingdate + ", billingenddate=" + billingenddate + ", paytype=" + paytype + ", packageInfo="
					+ packageInfo + ", flowInfo=" + flowInfo + ", balance=" + balance + ", openTime=" + openTime
					+ ", status=" + status + ", statusTime=" + statusTime + ", operator=" + operator + "]";
		}
		
}
