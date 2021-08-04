package com.accenture.cityweather.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
/**
 * This is main sub-resource for the application which will handle all temperature related operations
 * @author Somnath Mulay
 *
 */
@Path("rest/v0")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class CityWheatherAppResource {
	
	/**
	 * This rest related to the temperature of the city
	 * @return
	 */
	@Path("temperature")
	public TemperatureResource temperatureResource() {
		return new TemperatureResource();
	}
	
	
	//Other Rest can be added here like humidity,wind and all like temperature as follows
	/**
	 * 
	 * @return
	 */
	@Path("wind")
	public WindResource windResource() {
		return new WindResource();
	}
}

