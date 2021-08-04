package com.accenture.cityweather.rest.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.accenture.cityweather.rest.config.OpenWeatherMapAPIConstant;
import com.accenture.cityweather.rest.config.ReadConfig;
import com.accenture.cityweather.rest.config.RestCountriesConstant;
import com.accenture.cityweather.rest.exception.CityWeatherErrorEnum;
import com.accenture.cityweather.rest.exception.CityWeatherException;

import okhttp3.HttpUrl;
import okhttp3.HttpUrl.Builder;
/**
 * This is to handle all temperature relates utility operations
 * @author Somnath Mulay
 */
public class TemperatureUtility {
	private static final Logger LOGGER = LogManager.getLogger(TemperatureUtility.class.getName());

	public static HttpUrl buildQueryToGetTemperatureByCityNameOrGeographicalLocation(String cityName, float latitude,
			float longitude, String unit) {
		LOGGER.info(
				"Inside the build url to get the tempreture ||buildQueryToGetTemperatureByCountryNameOrGeographicalLocation() :: cityName:"
						+ cityName + " Latitude:" + latitude + " longitude: " + longitude + " units: " + unit);
		/**
		 * Check user input is corrrect or not if not throw exception with the proper
		 * message if it s valid process further
		 */
		if ((cityName == null || cityName.isEmpty()) && ((latitude == 0.0f) && (longitude == 0.0f))) {
			throw new CityWeatherException(CityWeatherErrorEnum.CW_ERROR_INVALID_INPUT_CITY_ENDPOINT);
		}

		/**
		 * read properties from the confg file and build the http url
		 */
		ReadConfig readConfig = new ReadConfig();
		Builder url = new HttpUrl.Builder().scheme("https")
				.host(readConfig.getProperty(OpenWeatherMapAPIConstant.OPEN_WEATHER_MAP_HOST_URI))
				.addEncodedPathSegments(
						readConfig.getProperty(OpenWeatherMapAPIConstant.OPEN_WEATHER_MAP_RELATIVE_URL));
		/**
		 * Add query params to the request
		 */
		if (cityName != null && !cityName.isEmpty()) {
			url.addEncodedQueryParameter("q", cityName);
		} else {
			url.addEncodedQueryParameter("lat", String.valueOf(latitude));
			url.addEncodedQueryParameter("lon", String.valueOf(longitude));
		}
		/**
		 * add api key to the request
		 */
		url.addEncodedQueryParameter("appid",
				readConfig.getProperty(OpenWeatherMapAPIConstant.OPEN_WEATHER_MAP_API_KEY));
		// check for only three units(metric is default)
		if (unit != null && !unit.isEmpty()) {
			url.addQueryParameter("units", unit);
		} else {
			url.addQueryParameter("units", "metric");
		}
		LOGGER.info(
				"Exiting the build url to get the tempreture ||buildQueryToGetTemperatureByCountryNameOrGeographicalLocation() ::"
						+ url.toString());
		return url.build();
	}

	public static HttpUrl buildQueryToGetTemperatureByCountryNameOrGeographicalLocation(String countryName) {
		LOGGER.info(
				"Inside the build url to get the tempreture ||buildQueryToGetTemperatureByCountryNameOrGeographicalLocation() :: countryName:"
						+ countryName);
		/**
		 * check required params for the request
		 */
		if (countryName == null || countryName.isEmpty()) {
			throw new CityWeatherException(CityWeatherErrorEnum.CW_ERROR_INVALID_INPUT_COUNTRY_ENDPOINT);
		}
		/**
		 * read config params from the properties file and build the url
		 */
		ReadConfig readConfig = new ReadConfig();
		Builder url = new HttpUrl.Builder().scheme("https")
				.host(readConfig.getProperty(RestCountriesConstant.REST_COUNTRIES_HOST_URI))
				.addEncodedPathSegments(readConfig.getProperty(RestCountriesConstant.REST_COUNTRIES_RELATIVE_URL));
		/**
		 * add path parameters to the request
		 */
		if (countryName != null && !countryName.isEmpty()) {
			url.addEncodedPathSegment(countryName);
		}

		LOGGER.info(
				"Exiting the build url to get the tempreture ||buildQueryToGetTemperatureByCountryNameOrGeographicalLocation() ::"
						+ url.toString());
		return url.build();
	}

}
