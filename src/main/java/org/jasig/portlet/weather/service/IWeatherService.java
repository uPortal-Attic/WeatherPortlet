package org.jasig.portlet.weather.service;

import java.util.Collection;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.jasig.portlet.weather.DuplicateLocationException;
import org.jasig.portlet.weather.TemperatureUnit;
import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;

/**
 * Weather Service interface. Interfaced in case others want to implement the
 * service calls differently.
 * 
 * @author Dustin Schultz
 */
public interface IWeatherService {

	public Weather getWeather(String locationCode, TemperatureUnit unit);

	public Collection<Location> find(String location);
	
	/**
	 * @throws DuplicateLocationException If a location with the specified code already exists
	 */
	public SavedLocation addWeatherLocation(PortletPreferences prefs, String locationCode, String location, TemperatureUnit unit);
	
	public void deleteWeatherLocation(PortletPreferences prefs, String locationCode);

	public List<SavedLocation> getSavedLocations(PortletPreferences prefs);
    
	public void saveLocations(List<SavedLocation> savedLocations, PortletPreferences prefs);
	
	public String getWeatherProviderName();
	
	public String getWeatherProviderLink();
	
}
