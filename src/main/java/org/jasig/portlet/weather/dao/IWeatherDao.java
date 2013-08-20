/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.portlet.weather.dao;

import java.util.Collection;

import org.jasig.portlet.weather.TemperatureUnit;
import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;

import com.googlecode.ehcache.annotations.Cacheable;

/**
 * Weather data access interface. Implement this interface to retrieve weather
 * information from source.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public interface IWeatherDao {

	/**
	 * Gets the weather from an implemented source.
	 * 
	 * @param locationCode
	 *            A string value representing the location to retrieve weather
	 *            from.
	 * @param metric
	 *            A boolean value representing metric or not.
	 * @return A Weather object representing the current weather and an optional
	 *         forecast.
	 */
	public Weather getWeather(String locationCode, TemperatureUnit unit);

	/**
	 * @param location
	 *            A String representing a location to find
	 * @return A collection of locations representing the possible location or
	 *         an empty or null collection representing location not found.
	 */
	public Collection<Location> find(String location);
	
    public String getWeatherProviderName();

    public String getWeatherProviderLink();

}
