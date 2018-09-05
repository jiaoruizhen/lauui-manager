package com.dognessnetwork.ops.domain;



public class PackageInfo {
	
	private String pkgCode;

	private String pkgEfftTime;

	private String pkgExpireTime;
	
	private String pkgName;
	
	private String pkgStatus;
	
	private String subSprodId;
	public String getPkgCode() {
		return pkgCode;
	}

	public void setPkgCode(String pkgCode) {
		this.pkgCode = pkgCode;
	}

	public String getPkgEfftTime() {
		return pkgEfftTime;
	}

	public void setPkgEfftTime(String pkgEfftTime) {
		this.pkgEfftTime = pkgEfftTime;
	}

	public String getPkgExpireTime() {
		return pkgExpireTime;
	}

	public void setPkgExpireTime(String pkgExpireTime) {
		this.pkgExpireTime = pkgExpireTime;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getPkgStatus() {
		return pkgStatus;
	}

	public void setPkgStatus(String pkgStatus) {
		this.pkgStatus = pkgStatus;
	}

	public String getSubSprodId() {
		return subSprodId;
	}

	public void setSubSprodId(String subSprodId) {
		this.subSprodId = subSprodId;
	}

	@Override
	public String toString() {
		return "PackageInfo [pkgCode=" + pkgCode + ", pkgEfftTime=" + pkgEfftTime + ", pkgExpireTime=" + pkgExpireTime
				+ ", pkgName=" + pkgName + ", pkgStatus=" + pkgStatus + ", subSprodId=" + subSprodId + "]";
	}

	
}
