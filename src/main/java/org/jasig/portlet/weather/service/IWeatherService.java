package org.jasig.portlet.weather.service;

import java.util.Collection;
import java.util.Map;

import javax.portlet.PortletPreferences;

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
	
	public Collection<Weather> getAllWeather(PortletPreferences preferences);

	public Collection<Location> find(String location);
	
	public Map<String, String[]> getSavedLocationsMap(PortletPreferences prefs);
	
	public void addWeatherLocation(PortletPreferences prefs, String locationCode, String location, String metric);
	
	public void deleteWeatherLocation(PortletPreferences prefs, String locationCode);

}
