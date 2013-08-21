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

package org.jasig.portlet.weather.service;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.jasig.portlet.weather.TemperatureUnit;
import org.jasig.portlet.weather.dao.IWeatherDao;
import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * This service class completes the implementation of IWeatherService and makes
 * calls to IWeatherDao to retrieve weather information and find locations.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
@Service
public class WeatherServiceImpl extends AbstractWeatherService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private static final String ADDING_LOCATIONS_TO_CACHE_KEY = "Adding locations-to cache key: %s";
    private static final String RETRIEVING_LOCATIONS_FROM_CACHE_KEY = "Retrieving locations-from cache key: %s";
    private static final String ADDING_WEATHER_TO_CACHE_KEY = "Adding Weather-to cache key: %s";
    private static final String RETRIEVING_WEATHER_FROM_CACHE_KEY = "Retrieving Weather-from cache, key: %s";
    private static final String WEATHER_KEY_TEMPLATE = "%s_%s";
    private IWeatherDao weatherDao = null; // Spring managed.

    @Resource(name="weatherSearchCache")
    private Cache weatherSearchCache;

    @Resource(name="weatherDataCache")
    private Cache weatherDataCache;

    public Collection<Location> find(String location) {
        //no location is set, don't try to search for anything
        if (StringUtils.isBlank(location)) {
            return null;
        }

        Element cachedElement = this.weatherSearchCache.get(location);
        if (cachedElement != null) {
            logDebugInformation(RETRIEVING_LOCATIONS_FROM_CACHE_KEY, location, cachedElement);
            @SuppressWarnings("unchecked")
            final Collection<Location> locations = (Collection<Location>) cachedElement.getValue();
            return locations;
        } else {
            final Collection<Location> locations = weatherDao.find(location);
            cachedElement = new Element(location, locations);
            logDebugInformation(ADDING_LOCATIONS_TO_CACHE_KEY, location, cachedElement);
            this.weatherSearchCache.put(cachedElement);
            return locations;
        }
    }

    /* (non-Javadoc)
     * @see org.jasig.portlet.weather.service.IWeatherService#getWeather(java.lang.String, org.jasig.portlet.weather.TemperatureUnit)
     */
    public Weather getWeather(String locationCode, TemperatureUnit unit) {
        //no locationCode is set, don't try to retrieve anything
        if (StringUtils.isBlank(locationCode)) {
            return null;
        }
        String key = createKeyFromLocationAndUnitOfMeasure(locationCode, unit);
        Element cachedElement = this.weatherDataCache.get(key);
        if (cachedElement != null) {
            logDebugInformation(RETRIEVING_WEATHER_FROM_CACHE_KEY, key, cachedElement);
            @SuppressWarnings("unchecked")
            final Weather weather = (Weather) cachedElement.getValue();
            return weather;
        } else {
            final Weather weather = weatherDao.getWeather(locationCode, unit);
            cachedElement = new Element(key, weather);
            logDebugInformation(ADDING_WEATHER_TO_CACHE_KEY, key, cachedElement);
            this.weatherDataCache.put(cachedElement);
            return weather;
        }
    }

    private String createKeyFromLocationAndUnitOfMeasure(String locationCode, TemperatureUnit temperatureUnitOfMeasure) {
        String key = String.format(WEATHER_KEY_TEMPLATE,locationCode,temperatureUnitOfMeasure.toString());
        return key;
    }

    private void logDebugInformation(String message, String value, Element cachedElement) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format(message, value));
            logger.debug(cachedElement.getValue().toString());
        }
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

    public void setWeatherSearchCache(Cache weatherSearchCache) {
        this.weatherSearchCache = weatherSearchCache;
    }

    public void setWeatherDataCache(Cache weatherDataCache) {
        this.weatherDataCache = weatherDataCache;
    }
}
