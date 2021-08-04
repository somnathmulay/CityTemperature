
package com.accenture.cityweather.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.accenture.cityweather.rest.exception.CityWeatherException;
import com.accenture.cityweather.rest.model.ErrorMessage;
import com.accenture.cityweather.rest.services.TemperatureServices;

/**
 * This resource class is for all temperature related rest services
 * 
 * 
 * @author Somnath Mulay
 *
 */
@Path("/")
public class TemperatureResource {
	public static final Logger LOGGER = LogManager.getLogger(TemperatureResource.class.getName());
	TemperatureServices temperatureServices = new TemperatureServices();

	/**
	 * To get the temperature of the city and the difference between user local time
	 * and city's time
	 * 
	 * @param cityName      name of the city for which temperature will be return
	 * @param userLocalTime local date and time of the user
	 * @param units         unit in which temperature should be return
	 * @return details with the temperature of the city
	 */
	@GET
	@Path("gettempbycityname")
	public Response getTemperatureByCityName(@QueryParam("cityName") String cityName,
			@QueryParam("longitude") float longitude, @QueryParam("latitude") float latitude,
			@QueryParam("userLocalTime") String userLocalTime, @QueryParam("unit") String units) {
		LOGGER.info("gettempbycityname rest web service is called by the user:");
		ErrorMessage errorMessage = new ErrorMessage();
		Response response = null;
		try {
			response = Response.ok().entity(
					temperatureServices.getTemperatureByCityName(cityName, latitude, longitude, units, userLocalTime))
					.build();
		} catch (CityWeatherException exception) {
			errorMessage.setErrorCode(exception.getCityWeatherErrorEnum().getErrorCode());
			errorMessage.setErrorMessage(exception.getCityWeatherErrorEnum().getErrorMessage());
			response = Response.ok().entity(errorMessage).build();
		} catch (Exception exception) {
			errorMessage.setErrorCode("HTTP_204");
			errorMessage.setErrorMessage(exception.getMessage());
			response = Response.ok().entity(errorMessage).build();
		}
		return response;
	}

	/**
	 * To get the temperature of the capital city of the counry and the difference
	 * between user local time and capital city's time of the country
	 * 
	 * @param countryName   name of the country for which capital city will be
	 *                      determine and temperature will be return for it
	 * @param userLocalTime local date and time of the user
	 * @param units         unit in which temperature should be return
	 * @return details with the temperature of the capital city of the country
	 */

	@GET
	@Path("gettempbycountryname")
	public Response getTemperatureByCountryName(@QueryParam("countryName") String countryName,
			@QueryParam("userLocalTime") String userLocalTime, @QueryParam("unit") String units) {
		LOGGER.info("gettempbycityname rest web service is called by the user:");
		ErrorMessage errorMessage = new ErrorMessage();
		Response response = null;
		try {
			response = Response.ok()
					.entity(temperatureServices.getTemperatureByCountryName(countryName, units, userLocalTime)).build();

		} catch (CityWeatherException exception) {
			errorMessage.setErrorCode(exception.getCityWeatherErrorEnum().getErrorCode());
			errorMessage.setErrorMessage(exception.getCityWeatherErrorEnum().getErrorMessage());
			response = Response.ok().entity(errorMessage).build();
		} catch (Exception exception) {
			errorMessage.setErrorCode("HTTP_204");
			errorMessage.setErrorMessage(exception.getMessage());
			response = Response.ok().entity(errorMessage).build();
		}
		return response;
	}

}
