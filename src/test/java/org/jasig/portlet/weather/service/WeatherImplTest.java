/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import java.util.Collection;

import org.jasig.portlet.weather.domain.Current;
import org.jasig.portlet.weather.domain.Forecast;
import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * General test class for WeatherDaoImpl. I tried to write this class to be able
 * to test all implementations of IWeatherDao and any failures are a good
 * indication that IWeatherDao may not be implemented correctly.
 * 
 * @author Dustin Schultz
 * @version $Id$
 * @see WeatherDaoImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/testContext-accuweather.xml" })
public class WeatherImplTest {

	@Autowired
	private IWeatherService weatherService = null;

	// Static expected test data. Final because they are read-only.
	private static final String CITY_CODE = "84648";
	private static final String CITY_NAME = "Nephi";
	private static final String CITY_STATE = "UT";
	private static final String CITY_STATE_FULL = "Utah";
	private static final int DAY_LENGTH = 3;

	@Test
	public void getWeather() {
		Weather weather = null;

		// make sure a null location returns null
		weather = weatherService.getWeather(null, null);
		assertNull("Invalid weather object", weather);

		Boolean metric = false;
		weather = weatherService.getWeather(CITY_CODE, metric);
		assertNotNull("Invalid weather object", weather);

		// Test the values of the retrieved location
		Location retrievedLocation = weather.getLocation();
		assertEquals("Invalid city", CITY_NAME, retrievedLocation.getCity());
		assertEquals("Invalid state", CITY_STATE, retrievedLocation
				.getStateOrCountry());
		assertEquals("Invalid code", CITY_CODE, retrievedLocation
				.getLocationCode());

		/*
		 * We can't really test specific values here since they are ever
		 * changing but we can make sure they're not null. We also only test
		 * fundamental elements that are assumed to always exist.
		 */
		Current currentWeather = weather.getCurrentWeather();
		assertNotNull("Invalid temperature", currentWeather.getTemperature());
		assertNotNull("Invalid condition", currentWeather.getCondition());
		assertNotNull("Invalid condition img path", currentWeather
				.getConditionImgPath());
		assertNotNull("Invalid condition img height", currentWeather
				.getConditionImgHeight());
		assertNotNull("Invalid condition img width", currentWeather
				.getConditionImgWidth());

		// a forecast is not required so ensure we have one
		Collection<Forecast> forecastCol = weather.getForecast();
		if (forecastCol != null) {
			for (Forecast forecast : forecastCol) {
				assertNotNull(forecast);
				assertNotNull("Invalid forecast condition", forecast
						.getCondition());
				assertNotNull("Invalid forecast condition img path", forecast
						.getConditionImgPath());
				assertNotNull("Invalid forecast condition img height", forecast
						.getConditionImgHeight());
				assertNotNull("Invalid forecast condition img width", forecast
						.getConditionImgWidth());
				assertNotNull("Invalid forecast high", forecast
						.getHighTemperature());
				assertNotNull("Invalid forecast low", forecast
						.getLowTemperature());
				assertNotNull("Invalid forecast day", forecast.getDay());
				// ensure its length 3
				assertEquals("Invalid day size", DAY_LENGTH, forecast.getDay()
						.length());
			}
		}

		// Make sure we have non null units of measurement
		assertNotNull("Invalid pressure unit", weather.getPressureUnit());
		assertNotNull("Invalid temperature unit", weather.getTemperatureUnit());
		assertNotNull("Invalid wind unit", weather.getWindUnit());
	}

	@Test
	public void find() {
		// Search by zip code
		Collection<Location> location1 = weatherService.find(CITY_CODE);
		assertNotNull("Did not find location 1", location1);
		// This should only loop once, if it doesn't that's good, it'll fail
		for (Location location : location1) {
			assertEquals("Invalid code", CITY_CODE, location.getLocationCode());
			assertEquals("Invalid city name", CITY_NAME, location.getCity());
			if (location.getStateOrCountry().length() > 2) {
				assertEquals("Invalid state", CITY_STATE_FULL, location
						.getStateOrCountry());
			} else {
				assertEquals("Invalid state", CITY_STATE, location
						.getStateOrCountry());
			}
		}

		// Search by city name
		Collection<Location> location2 = weatherService.find(CITY_NAME);
		assertNotNull("Did not find location 2", location2);
		// This should only loop once, if it doesn't that's good, it'll fail
		for (Location location : location2) {
			assertEquals("Invalid code", CITY_CODE, location.getLocationCode());
			assertEquals("Invalid city name", CITY_NAME, location.getCity());
			if (location.getStateOrCountry().length() > 2) {
				assertEquals("Invalid state", CITY_STATE_FULL, location
						.getStateOrCountry());
			} else {
				assertEquals("Invalid state", CITY_STATE, location
						.getStateOrCountry());
			}
		}

		// Produce a search with no results
		Collection<Location> location3 = weatherService.find("00000");
		assertNull("Should not have found location 3", location3);
	}
}
