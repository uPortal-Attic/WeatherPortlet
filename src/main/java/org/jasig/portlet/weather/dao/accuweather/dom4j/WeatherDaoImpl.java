/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather.dom4j;

import java.util.Collection;

import org.apache.commons.httpclient.HttpClient;
import org.jasig.portlet.weather.dao.IWeatherDao;
import org.jasig.portlet.weather.domain.Current;
import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;

/**
 * AccuWeather.com weather data implementation. Methods delegate work to
 * respective worker classes which use DOM4J.
 * 
 * @see LocationUtil
 * @see WeatherUtil
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherDaoImpl implements IWeatherDao {
	
	//Define the HttpClient here and pass it so we only define one instance
	private final HttpClient httpClient = new HttpClient();
	
	//Default timeout of 5 seconds
	private int connectionTimeout = 5000;
	
	//Default timeout of 5 seconds
	private int readTimeout = 5000;

	public WeatherDaoImpl() {
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(readTimeout);
	}
	
	public Collection<Location> find(String location) {
		LocationUtil locationUtil = new LocationUtil(httpClient, location);
		return locationUtil.getLocations();
	}

	public Weather getWeather(String locationCode, Boolean metric) {
		// delegate parsing to WeatherUtil class
		WeatherUtil weatherUtil = new WeatherUtil(httpClient, locationCode, metric);

		Weather weather = new Weather();

		// fill and set the location object
		Location location = new Location();
		location.setCity(weatherUtil.getCity());
		location.setStateOrCountry(weatherUtil.getState());
		location.setLatitude(weatherUtil.getLatitude());
		location.setLongitude(weatherUtil.getLongitude());
		location.setLocationCode(locationCode);
		weather.setLocation(location);

		// fill and set the current object
		Current currentWeather = new Current();
		currentWeather.setCondition(weatherUtil.getCurrentCondition());
		currentWeather.setTemperature(weatherUtil.getCurrentTemperature());
		currentWeather.setHumidity(weatherUtil.getHumidity());
		currentWeather.setPressure(weatherUtil.getPressure());
		currentWeather.setWindSpeed(weatherUtil.getWindSpeed());
		currentWeather.setWindDirection(weatherUtil.getWindDirection());
		currentWeather.setImgName(weatherUtil.getCurrentConditionImg());
		weather.setCurrentWeather(currentWeather);

		// set the forecast collection
		weather.setForecast(weatherUtil.getForecast());

		// set more information link
		weather.setMoreInformationLink(weatherUtil.getMoreInformationLink());

		// set units
		weather.setPressureUnit(weatherUtil.getPressureUnit());
		weather.setWindUnit(weatherUtil.getWindUnit());
		weather.setTemperatureUnit(weatherUtil.getTemperatureUnit());

		return weather;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

}
