/**
 * 
 */
package com.accenture.cityweather.rest.exception;

/**
 * @author Somnath Mulay
 *
 */
public enum CityWeatherErrorEnum {

	CW_ERROR_INVALID_INPUT_CITY_ENDPOINT("Invalid input provided,Please provide at least city name(cityName) or geographical co-ordinates(latitude and longitude)", "HTTP_400"),
	CW_ERROR_INVALID_INPUT_COUNTRY_ENDPOINT("Invalid input provided,Please provide country name as(countryName)", "HTTP_400"),
	CW_ERROR_INVALID_INPUT_USER_LOCAL_TIME("Invalid input provided,Please provide user local time as(userLocalTime)", "HTTP_400"),
	CW_ERROR_INVALID_INPUT_USER_LOCAL_TIME_FORMAT("Invalid user local time format provided,Please provide user local time as(userLocalTime) in the format of 'yyyy-MM-dd HH:mm'", "HTTP_400"),
	CW_ERROR_NOT_FOUND_COUNTRY("Country with the given name not found", "HTTP_404"),
	CW_ERROR_NOT_FOUND_CITY("City with the given name not found", "HTTP_404");

	public final String errorMessage;
	public final String errorCode;

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	private CityWeatherErrorEnum(String errorMessage, String errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

}
