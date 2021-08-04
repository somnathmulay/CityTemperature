package com.accenture.cityweather.rest.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.accenture.cityweather.rest.dao.TemperatureDao;
import com.accenture.cityweather.rest.model.CityTemperatureResponse;

/**
 * This class is service class for the temperature resources
 * 
 * @author Somnath Mulay
 *
 */
public class TemperatureServices {
	public static final Logger LOGGER = LogManager.getLogger(TemperatureServices.class.getName());

	/**
	 * @throws Exception Exception in case any failure in procesing of the code
	 * 
	 * 
	 */
	public CityTemperatureResponse getTemperatureByCityName(String cityName, float latitude, float longitude,
			String units, String userLocalTime) throws Exception {
		LOGGER.info("Inside the get temperature by city name service || getTemperatureByCityName() ");
		return TemperatureDao.getTemperatureByCityName(cityName, latitude, longitude, units, userLocalTime);
	}

	/**
	 * @throws Exception in case any failure in procesing of the code
	 * 
	 * 
	 */
	public CityTemperatureResponse getTemperatureByCountryName(String countryName, String units, String userLocalTime)
			throws Exception {
		LOGGER.info("Inside the get temperature by country name service || getTemperatureByCountryName() ");
		return TemperatureDao.getTemperatureByCountryName(countryName, units, userLocalTime);
	}
}
