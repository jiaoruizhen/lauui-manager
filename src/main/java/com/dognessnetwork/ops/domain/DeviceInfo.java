package com.dognessnetwork.ops.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="device_info")
public class DeviceInfo {
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	
	@Column(name="device_id")
	    private String deviceId;

	@Column(name="sale_area")
	    private Long saleArea;
	//
//	    private String areaName;
	    //   private Integer saleArea;
		@Column(name="app_user")
	    private Long appUser;
	//    
//	    private String username;
		@Column(name="product_id")
	    private String productId;
		
		//private DeviceProduct deviceProduct;
	@Column(name="fw_ver")
	    private String fwVer;
	@Column(name="fw_build")
	    private String fwBuild;
	@Column(name="status")
	    private Integer status;
	@Column(name="sim_id")
	    private Long simId;
//	    private String ICCID;
	@Column(name="dead_date")
	    private String deadDate;
	    
//	    private String statusName;
	@Column(name="click_update")
	    private Boolean clickUpdate;
		
		//private Distributor distributor;
	@Column(name="create_time")
	    private String createTime;
	@Column(name="edit_time")
	    private String editTime;
	@Column(name="operator")
	    private Long operator;
	@Column(name="iccid_imei")
	private String cardId;
	   // private SimInfo simInfo;

		public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}

		public Long getSaleArea() {
			return saleArea;
		}

		public void setSaleArea(Long saleArea) {
			this.saleArea = saleArea;
		}

		public Long getAppUser() {
			return appUser;
		}

		public void setAppUser(Long appUser) {
			this.appUser = appUser;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public String getFwVer() {
			return fwVer;
		}

		public void setFwVer(String fwVer) {
			this.fwVer = fwVer;
		}

		public String getFwBuild() {
			return fwBuild;
		}

		public void setFwBuild(String fwBuild) {
			this.fwBuild = fwBuild;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public Long getSimId() {
			return simId;
		}

		public void setSimId(Long simId) {
			this.simId = simId;
		}

		

		public Boolean getClickUpdate() {
			return clickUpdate;
		}

		public void setClickUpdate(Boolean clickUpdate) {
			this.clickUpdate = clickUpdate;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getEditTime() {
			return editTime;
		}

		public void setEditTime(String editTime) {
			this.editTime = editTime;
		}

		public Long getOperator() {
			return operator;
		}

		public void setOperator(Long operator) {
			this.operator = operator;
		}

		public String getDeadDate() {
			return deadDate;
		}

		public void setDeadDate(String deadDate) {
			this.deadDate = deadDate;
		}

		@Override
		public String toString() {
			return "DeviceInfo [id=" + id + ", deviceId=" + deviceId + ", saleArea=" + saleArea + ", appUser=" + appUser
					+ ", productId=" + productId + ", fwVer=" + fwVer + ", fwBuild=" + fwBuild + ", status=" + status
					+ ", simId=" + simId + ", deadDate=" + deadDate + ", clickUpdate=" + clickUpdate + ", createTime="
					+ createTime + ", editTime=" + editTime + ", operator=" + operator + ", cardId=" + cardId + "]";
		}

		
	   
}
