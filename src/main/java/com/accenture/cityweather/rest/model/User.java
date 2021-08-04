package com.accenture.cityweather.rest.model;

/**
 * 
 * @author Somnath Mulay
 *
 */
public class User {
	private String userLocalTime;
	private Long userLocalTimeZoneOffset;

	public String getUserLocalTime() {
		return userLocalTime;
	}

	@Override
	public String toString() {
		return "User [userLocalTime=" + userLocalTime + ", userLocalTimeZoneOffset=" + userLocalTimeZoneOffset + "]";
	}

	public Long getUserLocalTimeZoneOffset() {
		return userLocalTimeZoneOffset;
	}

	public void setUserLocalTimeZoneOffset(Long userLocalTimeZoneOffset) {
		this.userLocalTimeZoneOffset = userLocalTimeZoneOffset;
	}

	public void setUserLocalTime(String userLocalTime) {
		this.userLocalTime = userLocalTime;
	}

}
