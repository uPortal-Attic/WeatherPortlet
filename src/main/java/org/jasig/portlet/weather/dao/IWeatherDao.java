/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao;

import java.util.Collection;

import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;

/**
 * Weather data access interface. Implement this interface to retrieve weather
 * information from source.
 * 
 * @author Dustin Schultz
 */
public interface IWeatherDao {

	/**
	 * Gets the weather from an implemented source.
	 * 
	 * @param location
	 *            A location object representing the location of weather to
	 *            retrieve.
	 * @return A Weather object representing the current weather and an optional
	 *         forecast.
	 */
	public Weather getWeather(Location location);

	/**
	 * @param location
	 *            A String representing a location to find
	 * @return A collection of locations representing the possible location or
	 *         an empty or null collection representing location not found.
	 */
	public Collection<Location> find(String location);

}
