/**
 * 
 */
package com.accenture.cityweather.rest.model;

import com.accenture.cityweather.rest.exception.CityWeatherErrorEnum;

/**
 * @author Parth
 *
 */
public class ErrorMessage {
	private String errorMessage;
	private String errorCode;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorMessage(String errorMessage, String errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public ErrorMessage(CityWeatherErrorEnum cityWeatherErrorEnum) {
		this.errorMessage = cityWeatherErrorEnum.getErrorMessage();
		this.errorCode = cityWeatherErrorEnum.getErrorCode();
	}

	public ErrorMessage() {

	}

	@Override
	public String toString() {
		return "ErrorMessage [errorMessage=" + errorMessage + ", errorCode=" + errorCode + "]";
	}
}
