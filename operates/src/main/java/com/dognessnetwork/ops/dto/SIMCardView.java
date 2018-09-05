package com.dognessnetwork.ops.dto;

public class SIMCardView {
	private String iccid;
	private String imsi;
	private String imei;
	private String msisdn;
	private String code;
	private boolean cardStatus;
	
	public boolean isCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(boolean cardStatus) {
		this.cardStatus = cardStatus;
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
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "SIMCardView [iccid=" + iccid + ", imsi=" + imsi + ", imei=" + imei + ", msisdn=" + msisdn + ", code="
				+ code + "]";
	}
	
}
