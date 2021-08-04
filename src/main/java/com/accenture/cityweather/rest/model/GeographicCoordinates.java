package com.accenture.cityweather.rest.model;

/**
 * 
 * @author Somnath Mulay
 *
 */
public class GeographicCoordinates {
	private long timeZoneOffset;
	private float latitude;
	private float longitude;

	@Override
	public String toString() {
		return "GeographicCoordinates [timeZoneOffset=" + timeZoneOffset + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public long getTimeZoneOffset() {
		return timeZoneOffset;
	}

	public void setTimeZoneOffset(long timeZoneOffset) {
		this.timeZoneOffset = timeZoneOffset;
	}

}
