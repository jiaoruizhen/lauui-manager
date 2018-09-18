package com.dognessnetwork.ops.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_robot")
public class Robot {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String version;
	private String bluetoothMac;
	private String bluetoothUuid;
	private String wxBlueId;
	private String wifiMac;
	private Long devUid;
	private String fwVer;
	private String password;
	@OneToOne
	private Device device;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWxBlueId() {
		return wxBlueId;
	}
	public void setWxBlueId(String wxBlueId) {
		this.wxBlueId = wxBlueId;
	}
	public String getFwVer() {
		return fwVer;
	}
	public void setFwVer(String fwVer) {
		this.fwVer = fwVer;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getBluetoothMac() {
		return bluetoothMac;
	}
	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}
	public String getBluetoothUuid() {
		return bluetoothUuid;
	}
	public void setBluetoothUuid(String bluetoothUuid) {
		this.bluetoothUuid = bluetoothUuid;
	}
	public String getWifiMac() {
		return wifiMac;
	}
	public void setWifiMac(String wifiMac) {
		this.wifiMac = wifiMac;
	}
	public Long getDevUid() {
		return devUid;
	}
	public void setDevUid(Long devUid) {
		this.devUid = devUid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
