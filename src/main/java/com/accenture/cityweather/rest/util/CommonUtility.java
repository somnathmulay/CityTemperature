package com.accenture.cityweather.rest.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.accenture.cityweather.rest.exception.CityWeatherErrorEnum;
import com.accenture.cityweather.rest.exception.CityWeatherException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This is to handle all common utility operations of the application
 * 
 * @author Somnath Mulay
 *
 */
public class CommonUtility {

	private static final Logger LOGGER = LogManager.getLogger(CommonUtility.class.getName());

	/**
	 * 
	 * @return ok http client
	 */
	public static OkHttpClient getHttpClient() {
		return new OkHttpClient();
	}

	/**
	 * 
	 * @param countryName to get the capital city of the country
	 * @return name of the capital city
	 * @throws Exception if any occurs while getting the capital city
	 */
	public static String getCapitalCity(String countryName) throws Exception {
		LOGGER.info("Inside the get capital city by using country name ||  getCapitalCity() ");
		String capitalCityName = "";
		/**
		 * Check first that the capital city is present in the capital city cache or not
		 * if present return from the cache if not present then fetch from rest api and
		 * then put in to the cache to use next time
		 */

		if (AppCache.CAPITAL_CITY_CACHE == null || !AppCache.CAPITAL_CITY_CACHE.containsKey(countryName)) {
			if (AppCache.CAPITAL_CITY_CACHE == null)
				AppCache.CAPITAL_CITY_CACHE = new ConcurrentHashMap<String, String>();
			/**
			 * get okhttp client
			 */
			OkHttpClient httpclient = CommonUtility.getHttpClient();
			/**
			 * create request
			 */
			Request request = new Request.Builder()
					.url(TemperatureUtility.buildQueryToGetTemperatureByCountryNameOrGeographicalLocation(countryName))
					.get().build();
			LOGGER.info("URL to get city:" + request.url());
			/**
			 * execute the request
			 */
			try (Response response = httpclient.newCall(request).execute()) {
				/**
				 * check the response code to confirm the status of the request
				 */
				if (response.code() == 200) {
					/**
					 * parse the response from rest api of restcountries.eu
					 */
					String responseString = response.body().string();
					JSONArray countryObjects = new JSONArray(responseString);
					for (int i = 0; i < countryObjects.length(); i++) {
						JSONObject countryObject = countryObjects.getJSONObject(i);
						/**
						 * iterate of the response from the rest api for countries to get user specific
						 * entry
						 */
						if (countryObject.getString("name").equalsIgnoreCase(countryName)
								|| (countryObject.has("alpha2Code")
										&& countryObject.getString("alpha2Code").equalsIgnoreCase(countryName))
								|| (countryObject.has("alpha3Code")
										&& countryObject.getString("alpha3Code").equalsIgnoreCase(countryName))) {
							String capitalCity = countryObject.has("capital") ? countryObject.getString("capital") : "";
							LOGGER.info("Country :" + countryName + " Capital Name:" + capitalCity);
							capitalCityName = capitalCity;
							AppCache.CAPITAL_CITY_CACHE.put(countryName, capitalCityName);
						}
					}

				} else {
					LOGGER.info("Status code from response::" + response.code() + " message:" + response.message());
					if (response.code() == 404) {
						throw new CityWeatherException(CityWeatherErrorEnum.CW_ERROR_NOT_FOUND_COUNTRY);
					} else {
						throw new Exception("Exception occured while getting capital city of the country");
					}
				}
			}
		} else {
			LOGGER.info("Checking inside the cache for country:" + countryName + "is present :"
					+ AppCache.CAPITAL_CITY_CACHE.containsKey(countryName));
			capitalCityName = AppCache.CAPITAL_CITY_CACHE.get(countryName);
		}
		return capitalCityName;
	}

	/**
	 * 
	 * @param zoneOffset    zoneoffset of the city
	 * @param userLocalTime user local time to determine the difference between
	 *                      city's time and local time
	 * @return
	 */
	public static long getTimeDifferenceBitweenLocalAndCityTime(int zoneOffset, String userLocalTime) {
		if (userLocalTime == null || userLocalTime.isEmpty()) {
			LOGGER.error("User local time is not provided,Please provide user local time");
			throw new CityWeatherException(CityWeatherErrorEnum.CW_ERROR_INVALID_INPUT_USER_LOCAL_TIME);
		}
		/**
		 * parse user inout date in local date format
		 */
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTimeFromUser = null;
		try {
			dateTimeFromUser = LocalDateTime.parse(userLocalTime, formatter);
		} catch (DateTimeParseException exception) {
			LOGGER.error("Exception Occured while parsing the uset local time", exception);
			throw new CityWeatherException(CityWeatherErrorEnum.CW_ERROR_INVALID_INPUT_USER_LOCAL_TIME_FORMAT);
		}
		/**
		 * get local time using zoneoffset
		 */

		LocalDateTime currentDateOfCity = LocalDateTime.now(ZoneOffset.ofTotalSeconds(zoneOffset));
		/**
		 * find difference between two local date time
		 */
		long diffeerenceInSeconds = Duration.between(dateTimeFromUser, currentDateOfCity).getSeconds();
		/**
		 * convert seconds into the hours
		 */
		long hours = diffeerenceInSeconds / (60 * 60);
		return hours;
	}
}
