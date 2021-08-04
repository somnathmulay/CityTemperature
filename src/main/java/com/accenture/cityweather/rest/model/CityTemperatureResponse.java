package com.accenture.cityweather.rest.model;

/**
 * 
 * @author Somnath
 *
 */
public class CityTemperatureResponse {
	private City cityDetails;
	private long differenceInCityTimeAndUserLocalTimeInHours;
	private User userDetails;

	public City getCityDetails() {
		return cityDetails;
	}

	public void setCityDetails(City cityDetails) {
		this.cityDetails = cityDetails;
	}

	@Override
	public String toString() {
		return "CityTemperatureResponse [cityDetails=" + cityDetails + ", differenceInCityTimeAndUserLocalTimeInHours="
				+ differenceInCityTimeAndUserLocalTimeInHours + "]";
	}

	public long getDifferenceInCityTimeAndUserLocalTimeInHours() {
		return differenceInCityTimeAndUserLocalTimeInHours;
	}

	public void setDifferenceInCityTimeAndUserLocalTimeInHours(long differenceInCityTimeAndUserLocalTimeInHours) {
		this.differenceInCityTimeAndUserLocalTimeInHours = differenceInCityTimeAndUserLocalTimeInHours;
	}

	public User getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(User userDetails) {
		this.userDetails = userDetails;
	}

}
