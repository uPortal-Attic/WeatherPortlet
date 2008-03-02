/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.service;

import java.util.Collection;

import org.jasig.portlet.weather.dao.IWeatherDao;
import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This service class completes the implementation of IWeatherService and makes
 * calls to IWeatherDao to retrieve weather information and find locations.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherServiceImpl extends AbstractWeatherService {

	private IWeatherDao weatherDao = null; // Spring managed.

	public Collection<Location> find(String location) {
		//no location is set, don't try to search for anything
		if (location == null || location.length() == 0) {
			return null;
		}
		return weatherDao.find(location);
	}

	public Weather getWeather(String locationCode, Boolean metric) {
		//no locationCode is set, don't try to retrieve anything
		if (locationCode == null) {
			return null;
		}
		return weatherDao.getWeather(locationCode, metric);
	}
	
	@Autowired
	public void setWeatherDao(IWeatherDao weatherDao) {
		this.weatherDao = weatherDao;
	}
}
