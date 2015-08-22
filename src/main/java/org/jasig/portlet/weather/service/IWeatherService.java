/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
	 * Adds a weather location.
	 *
	 * @param prefs portlet preferences
	 * @param locationCode location code
	 * @param location location
	 * @param unit temperatur unit
	 * @throws DuplicateLocationException If a location with the specified code already exists
	 */
	public SavedLocation addWeatherLocation(PortletPreferences prefs, String locationCode, String location, TemperatureUnit unit);
	
	public void deleteWeatherLocation(PortletPreferences prefs, String locationCode);

	public List<SavedLocation> getSavedLocations(PortletPreferences prefs);
    
	public void saveLocations(List<SavedLocation> savedLocations, PortletPreferences prefs);
	
	public String getWeatherProviderName();
	
	public String getWeatherProviderLink();
	
}
