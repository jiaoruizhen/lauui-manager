package com.dognessnetwork.ops.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="t_device")
public class Device {
    @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //类型
    private String type;
    //是否在线
    private Boolean online;
    //是否绑定
    private Boolean isBind;
    //设备编码
    @PrimaryKeyJoinColumn
    private String deviceCode;
    //区域
    private String domain;
    //二维码URL
    private String qrUrl;
    //是否支持微信
    private Boolean isWechat;
    //上线时间
    private Long onlineTime;
    //下线时间
    private Long offlineTime;
    //设备名
    private String devName;
    //设备生产商
    private String producter;
    
    private Long petUserId;
    
    private Long petId;
    //批次
    private String batchCode;
    
    @Override
	public String toString() {
		return "Device [id=" + id + ", type=" + type + ", online=" + online + ", isBind=" + isBind + ", deviceCode="
				+ deviceCode + ", domain=" + domain + ", qrUrl=" + qrUrl + ", isWechat=" + isWechat + ", onlineTime="
				+ onlineTime + ", offlineTime=" + offlineTime + ", devName=" + devName + ", producter=" + producter
				+ ", petUserId=" + petUserId + ", petId=" + petId + ", batchCode=" + batchCode + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public Boolean getIsBind() {
		return isBind;
	}

	public void setIsBind(Boolean isBind) {
		this.isBind = isBind;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getQrUrl() {
		return qrUrl;
	}

	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}

	public Boolean getIsWechat() {
		return isWechat;
	}

	public void setIsWechat(Boolean isWechat) {
		this.isWechat = isWechat;
	}

	public Long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Long onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Long getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Long offlineTime) {
		this.offlineTime = offlineTime;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public String getProducter() {
		return producter;
	}

	public void setProducter(String producter) {
		this.producter = producter;
	}

	public Long getPetUserId() {
		return petUserId;
	}

	public void setPetUserId(Long petUserId) {
		this.petUserId = petUserId;
	}

	public Long getPetId() {
		return petId;
	}

	public void setPetId(Long petId) {
		this.petId = petId;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}


}
