/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather;

import java.util.Collection;

import org.jasig.portlet.weather.dao.IWeatherDao;
import org.jasig.portlet.weather.domain.Current;
import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;

/**
 * AccuWeather.com weather data implementation. Methods delegate work to
 * respective worker classes.
 * 
 * @see LocationUtil
 * @see WeatherUtil
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherDaoImpl implements IWeatherDao {

	public Collection<Location> find(String location) {
		LocationUtil locationUtil = new LocationUtil(location);
		return locationUtil.getLocations();
	}

	public Weather getWeather(String locationCode, Boolean metric) {
		// delegate parsing to WeatherUtil class
		WeatherUtil weatherUtil = new WeatherUtil(locationCode, metric);

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
		currentWeather.setConditionImgPath(weatherUtil
				.getCurrentConditionImgPath());
		currentWeather.setConditionImgHeight(weatherUtil
				.getImgConditionHeight());
		currentWeather.setConditionImgWidth(weatherUtil.getImgConditionWidth());
		currentWeather.setTemperature(weatherUtil.getCurrentTemperature());
		currentWeather.setHumidity(weatherUtil.getHumidity());
		currentWeather.setPressure(weatherUtil.getPressure());
		currentWeather.setWindSpeed(weatherUtil.getWindSpeed());
		currentWeather.setWindDirection(weatherUtil.getWindDirection());
		weather.setCurrentWeather(currentWeather);

		// set the forecast collection
		weather.setForecast(weatherUtil.getForecast());

		// set logo path, height, width, and more information link
		weather.setLogoPath(weatherUtil.getLogoPath());
		weather.setMoreInformationLink(weatherUtil.getMoreInformationLink());
		weather.setLogoHeight(weatherUtil.getLogoHeight());
		weather.setLogoWidth(weatherUtil.getLogoWidth());

		// set units
		weather.setPressureUnit(weatherUtil.getPressureUnit());
		weather.setWindUnit(weatherUtil.getWindUnit());
		weather.setTemperatureUnit(weatherUtil.getTemperatureUnit());

		// set provided by
		weather.setProvidedBy("Weather provided by AccuWeather.com");

		return weather;
	}
}
