/**
 * 
 */
package com.accenture.cityweather.rest.model;

/**
 * @author Somnath Mulay
 *
 */
public class City {

	private String cityName;
	private GeographicCoordinates geographicalCoordinates;
	private String cityLocalTime;
	private String countryName;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityName() {
		return cityName;
	}

	public String getCityLocalTime() {
		return cityLocalTime;
	}

	public void setCityLocalTime(String cityLocalTime) {
		this.cityLocalTime = cityLocalTime;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public GeographicCoordinates getGeographicalCoordinates() {
		return geographicalCoordinates;
	}

	public void setGeographicalCoordinates(GeographicCoordinates geographicalCoordinates) {
		this.geographicalCoordinates = geographicalCoordinates;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	private float temperature;

}
