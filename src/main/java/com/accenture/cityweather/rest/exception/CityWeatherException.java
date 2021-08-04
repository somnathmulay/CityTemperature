package com.accenture.cityweather.rest.exception;
/**
 * This is to handle the custom exception of the application
 * @author Somnath Mulay
 *
 */
public class CityWeatherException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	CityWeatherErrorEnum cityWeatherErrorEnum;

	public CityWeatherErrorEnum getCityWeatherErrorEnum() {
		return cityWeatherErrorEnum;
	}

	public void setCityWeatherErrorEnum(CityWeatherErrorEnum cityWeatherErrorEnum) {
		this.cityWeatherErrorEnum = cityWeatherErrorEnum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CityWeatherException(CityWeatherErrorEnum cityWeatherErrorEnum) {
		this.cityWeatherErrorEnum = cityWeatherErrorEnum;
	}

	public CityWeatherException() {
	}
}
