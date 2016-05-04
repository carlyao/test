package com.upchina.model;

import javax.persistence.Transient;

public class BaseModel{
	
	@Transient
	private String time;
	
	@Transient
	private String deviceType;
	
	
	@Transient
	private String platformType;
	
	@Transient
	private String appId;
	
	@Transient
	private String appVersion;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}


	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
}
