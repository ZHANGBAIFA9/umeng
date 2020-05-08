package com.umeng.phone.domain;

/**
 * 硬件信息
 */
public class DeviceInfo {
	private String deviceId;            //设备唯一标识
	private String appPlatform;         //平台
	private String brand;                //品牌
	private String osType;              //操作系统,平台版本
	private String deviceStyle;         //机型

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getAppPlatform() {
		return appPlatform;
	}

	public void setAppPlatform(String appPlatform) {
		this.appPlatform = appPlatform;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getDeviceStyle() {
		return deviceStyle;
	}

	public void setDeviceStyle(String deviceStyle) {
		this.deviceStyle = deviceStyle;
	}
}
