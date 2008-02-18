package org.jasig.portlet.weather.service;

import java.util.Collection;

import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;

/**
 * Weather Service interface. Interfaced in case others want to implement the
 * service calls differently.
 * 
 * @author Dustin Schultz
 */
public interface IWeatherService {

	public Weather getWeather(String locationCode, Boolean metric);

	public Collection<Location> find(String location);

}
