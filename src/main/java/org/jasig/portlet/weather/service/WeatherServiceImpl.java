/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.service;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.jasig.portlet.weather.TemperatureUnit;
import org.jasig.portlet.weather.dao.IWeatherDao;
import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service class completes the implementation of IWeatherService and makes
 * calls to IWeatherDao to retrieve weather information and find locations.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
@Service
public class WeatherServiceImpl extends AbstractWeatherService {
	private IWeatherDao weatherDao = null; // Spring managed.

    public Collection<Location> find(String location) {
		//no location is set, don't try to search for anything
		if (StringUtils.isBlank(location)) {
			return null;
		}
		return weatherDao.find(location);
	}

	/* (non-Javadoc)
     * @see org.jasig.portlet.weather.service.IWeatherService#getWeather(java.lang.String, org.jasig.portlet.weather.TemperatureUnit)
     */
    public Weather getWeather(String locationCode, TemperatureUnit unit) {
		//no locationCode is set, don't try to retrieve anything
	    if (StringUtils.isBlank(locationCode)) {
			return null;
		}
		return weatherDao.getWeather(locationCode, unit);
	}
	
	@Autowired
	public void setWeatherDao(IWeatherDao weatherDao) {
		this.weatherDao = weatherDao;
	}

    public String getWeatherProviderLink() {
        return this.weatherDao.getWeatherProviderLink();
    }

    public String getWeatherProviderName() {
        return this.weatherDao.getWeatherProviderName();
    }
}
