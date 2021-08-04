/**
 * 
 */
package com.accenture.cityweather.rest.dao;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.accenture.cityweather.rest.exception.CityWeatherErrorEnum;
import com.accenture.cityweather.rest.exception.CityWeatherException;
import com.accenture.cityweather.rest.model.City;
import com.accenture.cityweather.rest.model.CityTemperatureResponse;
import com.accenture.cityweather.rest.model.GeographicCoordinates;
import com.accenture.cityweather.rest.model.User;
import com.accenture.cityweather.rest.util.CommonUtility;
import com.accenture.cityweather.rest.util.TemperatureUtility;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This class is Data operation class in which all the data related operation
 * get performed
 * 
 * @author Somnath Mulay
 *
 */
public class TemperatureDao {
	private static final Logger LOGGER = LogManager.getLogger(TemperatureDao.class.getName());

	/**
	 * 
	 * @return city temperature details with other details
	 * @throws IOException
	 */
	public static CityTemperatureResponse getTemperatureByCityName(String cityName, float latitude, float longitude,
			String units, String userLocalTime) throws Exception {
		LOGGER.info("Inside the get tempreture by using city name || getTemperatureByCityName() ");
		CityTemperatureResponse cityTemperatureResponse = new CityTemperatureResponse();
		/**
		 * get okhttp client to call rest web service
		 */
		OkHttpClient httpclient = CommonUtility.getHttpClient();
		/**
		 * build the request as per the input from the user
		 * 
		 */
		Request request = new Request.Builder().url(TemperatureUtility
				.buildQueryToGetTemperatureByCityNameOrGeographicalLocation(cityName, latitude, longitude, units)).get()
				.build();
		LOGGER.info("URL to get Temp:" + request.url());
		/**
		 * call the web service using okhhtp client
		 */
		try (Response response = httpclient.newCall(request).execute()) {
			/**
			 * check the response code to confirm the status of the request
			 */
			if (response.code() == 200 && response.message().equalsIgnoreCase("Ok")) {
				/**
				 * parse the response to get the data from itt
				 */
				City cityDetails = new City();
				String responseString = response.body().string();
				/**
				 * city details
				 */
				JSONObject weatherDataObject = new JSONObject(responseString);
				cityDetails.setCityName(weatherDataObject.getString("name"));
				JSONObject mainJsonObject = weatherDataObject.getJSONObject("main");
				cityDetails.setTemperature(mainJsonObject.getFloat("temp"));
				JSONObject countryDetailsJsonObject = weatherDataObject.getJSONObject("sys");
				cityDetails.setCountryName(countryDetailsJsonObject.getString("country"));
				/**
				 * Geographical details of the city
				 */
				GeographicCoordinates geoCoord = new GeographicCoordinates();
				JSONObject geoCoOrdObject = weatherDataObject.getJSONObject("coord");
				geoCoord.setLongitude(geoCoOrdObject.getFloat("lon"));
				geoCoord.setLatitude(geoCoOrdObject.getFloat("lat"));
				cityDetails.setGeographicalCoordinates(geoCoord);
				int timezoneOffset = weatherDataObject.getInt("timezone");
				geoCoord.setTimeZoneOffset(timezoneOffset);
				cityTemperatureResponse.setCityDetails(cityDetails);
				/**
				 * user details
				 */
				User userDetails = new User();
				userDetails.setUserLocalTime(userLocalTime);
				OffsetDateTime odt = OffsetDateTime.now(ZoneId.systemDefault());
				long zoneOffset = odt.getOffset().getTotalSeconds();
				userDetails.setUserLocalTimeZoneOffset(zoneOffset);
				cityTemperatureResponse.setUserDetails(userDetails);
				/**
				 * difference between local time and city's time
				 */
				long difference = CommonUtility.getTimeDifferenceBitweenLocalAndCityTime(timezoneOffset, userLocalTime);
				cityTemperatureResponse.setDifferenceInCityTimeAndUserLocalTimeInHours(difference);

			} else {
				/**
				 * check the response code incase of failure and return proper message
				 */
				if (response.code() == 404) {
					throw new CityWeatherException(CityWeatherErrorEnum.CW_ERROR_NOT_FOUND_CITY);
				} else {
					throw new Exception("Exception occured while getting city temperature");
				}
			}
		}

		LOGGER.info("Exiting the get tempreture by using city name || getTemperatureByCityName() ");
		return cityTemperatureResponse;
	}

	/**
	 * 
	 * @return country's capital city temperature details with other details
	 * @throws Exception
	 */
	public static CityTemperatureResponse getTemperatureByCountryName(String countryName, String units,
			String userLocalTime) throws Exception {
		LOGGER.info("Inside the get tempreture by using citcountryy name ||  getTemperatureByCityName() ");
		CityTemperatureResponse cityTemperatureResponse = new CityTemperatureResponse();
		/**
		 * get the capital city using country name
		 */
		String capitalCity = CommonUtility.getCapitalCity(countryName);

		if (capitalCity != null && !capitalCity.isEmpty()) {
			/**
			 * if you get the capital city of the country then get temperature and other
			 * details of the city
			 */
			cityTemperatureResponse = TemperatureDao.getTemperatureByCityName(capitalCity, 0.0f, 0.0f, units,
					userLocalTime);
		} else {
			throw new CityWeatherException(CityWeatherErrorEnum.CW_ERROR_NOT_FOUND_COUNTRY);
		}
		LOGGER.info("Exiting the get tempreture by using citcountryy name ||  getTemperatureByCityName() ");
		return cityTemperatureResponse;
	}

}
