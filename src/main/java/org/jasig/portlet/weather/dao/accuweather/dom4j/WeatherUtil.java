/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather.dom4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jasig.portlet.weather.dao.accuweather.constants.Constants;
import org.jasig.portlet.weather.domain.Forecast;

/**
 * This is the worker class that retrieves weather data. It is called by
 * WeatherDaoImpl.
 * 
 * @see WeatherDaoImpl
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherUtil {

	private Logger logger = Logger.getLogger(WeatherUtil.class);

	private Element root = null;
	private Element units = null;
	private Element local = null;
	private Element currentConditions = null;
	private Element forecast = null;
	private Element planets = null;

	public WeatherUtil(String locationCode, Boolean metric) {
		logger.debug("Retrieving weather xml using DOM4J for location " + locationCode
				+ " with metric " + metric);
		String accuweatherUrl = Constants.BASE_GET_URL + locationCode
				+ "&metric=" + ((metric) ? "1" : "0");
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(accuweatherUrl);
		} catch (DocumentException de) {
			de.printStackTrace();
			throw new RuntimeException("Unable to retrieve xml", de);
		}

		// get top level elements
		root = document.getRootElement();
		units = root.element(Constants.UNITS_TAG);
		local = root.element(Constants.LOCAL_TAG);
		currentConditions = root.element(Constants.CURRENT_TAG);
		forecast = root.element(Constants.FORECAST_TAG);
		planets = root.element(Constants.PLANETS_TAG);
	}

	public String getCity() {
		if (local != null) {
			Element localCity = local.element(Constants.CITY_TAG);
			return (localCity != null) ? localCity.getText() : null;
		} else {
			return null;
		}
	}

	public String getState() {
		if (local != null) {
			Element localState = local.element(Constants.STATE_TAG);
			return (localState != null) ? localState.getText() : null;
		} else {
			return null;
		}
	}

	public Double getLatitude() {
		if (local != null) {
			Element localLat = local.element(Constants.LAT_TAG);
			return (localLat != null) ? Double.valueOf(localLat.getText())
					: null;
		} else {
			return null;
		}
	}

	public Double getLongitude() {
		if (local != null) {
			Element localLon = local.element(Constants.LON_TAG);
			return (localLon != null) ? Double.valueOf(localLon.getText())
					: null;
		} else {
			return null;
		}
	}

	public Integer getCurrentTemperature() {
		if (currentConditions != null) {
			Element currTemp = currentConditions
					.element(Constants.CURR_TEMP_TAG);
			return (currTemp != null) ? Integer.valueOf(currTemp.getText())
					: null;
		} else {
			return null;
		}
	}

	public String getCurrentCondition() {
		if (currentConditions != null) {
			Element currCondition = currentConditions
					.element(Constants.CURR_COND_TAG);
			return (currCondition != null) ? currCondition.getText() : null;
		} else {
			return null;
		}
	}

	public String getCurrentConditionImg() {
		if (currentConditions != null) {
			Element currImgName = currentConditions.element(Constants.CURR_ICON_TAG);
			return (currImgName != null) ? currImgName.getText() : null;
		} else {
			return null;
		}
	}

	public Double getWindSpeed() {
		if (currentConditions != null) {
			Element currWindSpeed = currentConditions
					.element(Constants.CURR_WIND_SPEED_TAG);
			return (currWindSpeed != null) ? Double.valueOf(currWindSpeed
					.getText()) : null;
		} else {
			return null;
		}
	}

	public String getWindDirection() {
		if (currentConditions != null) {
			Element currWindDir = currentConditions
					.element(Constants.CURR_WIND_DIR_TAG);
			return (currWindDir != null) ? currWindDir.getText() : null;
		} else {
			return null;
		}
	}

	public Double getHumidity() {
		if (currentConditions != null) {
			Element currHumidity = currentConditions
					.element(Constants.CURR_HUMIDITY_TAG);
			return (currHumidity != null) ? Double.valueOf(currHumidity
					.getText()
					.substring(0, currHumidity.getText().indexOf('%'))) : null;
		} else {
			return null;
		}
	}

	public String getPressureUnit() {
		if (units != null) {
			Element pressure = units.element(Constants.PRES_TAG);
			return (pressure != null) ? pressure.getText() : null;
		} else {
			return null;
		}
	}

	public String getTemperatureUnit() {
		if (units != null) {
			Element temperature = units.element(Constants.TEMP_TAG);
			return (temperature != null) ? temperature.getText() : null;
		} else {
			return null;
		}
	}

	public String getWindUnit() {
		if (units != null) {
			Element wind = units.element(Constants.SPEED_TAG);
			return (wind != null) ? wind.getText() : null;
		} else {
			return null;
		}
	}

	public Double getPressure() {
		if (currentConditions != null) {
			Element currPressure = currentConditions
					.element(Constants.CURR_PRESSURE_TAG);
			// -999 means pressure is unavailable
			return (currPressure != null && !("-999").equals(currPressure
					.getText())) ? Double.valueOf(currPressure.getText())
					: null;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<Forecast> getForecast() {
		DateFormat df1 = new SimpleDateFormat("h:mm a");

		String sunsetTime = null;
		if (planets != null) {
			Element sun = planets.element(Constants.SUN_TAG);
			Attribute set = sun.attribute(Constants.SUNSET_ATTR);
			sunsetTime = set.getValue();
		}

		if (sunsetTime == null) {
			logger.debug("Unable to retrieve sunset time");
			return null;
		}

		String obsTime = null;
		if (currentConditions != null) {
			Element currentObs = currentConditions.element("observationtime");
			obsTime = currentObs.getText();
		}

		if (obsTime == null) {
			logger.debug("Unable to retrieve observation time");
			return null;
		}

		Date sunsetDate = null;

		try {
			sunsetDate = df1.parse(sunsetTime.trim());
		} catch (ParseException pe) {
			pe.printStackTrace();
			throw new RuntimeException("Unable to parse sunset value");
		}

		Date obsDate = null;

		try {
			obsDate = df1.parse(obsTime.trim());
		} catch (ParseException pe) {
			pe.printStackTrace();
			throw new RuntimeException("Unable to parse observation value");
		}

		Collection<Forecast> forecastCol = new ArrayList<Forecast>();
		List<Element> forecastElements = forecast
				.elements(Constants.FORECAST_DAY_TAG);

		if (forecastElements.size() <= 0) {
			logger.error("Empty forecast list");
			return null;
		}

		for (Element ele : forecastElements) {
			// Have to see what time of day it is to determine what forecast
			// element to get
			Element timeOfDay = null;
			if (sunsetDate.before(obsDate)) {
				// its night
				timeOfDay = ele.element(Constants.NIGHTTIME_TAG);
			} else {
				// its day
				timeOfDay = ele.element(Constants.DAYTIME_TAG);
			}

			Element day = ele.element(Constants.FORECAST_DAYCODE_TAG);
			Element condition = timeOfDay.element(Constants.FORECAST_COND_TAG);
			Element high = timeOfDay.element(Constants.FORECAST_HIGH_TAG);
			Element low = timeOfDay.element(Constants.FORECAST_LOW_TAG);
			Element img = timeOfDay.element(Constants.FORECAST_IMG_ICON_TAG);

			Forecast forecast = new Forecast();
			forecast.setCondition(condition.getText());
			forecast.setDay(day.getText().substring(0, 3)); // Just get 3 letters
			forecast.setImgName(img.getText());
			forecast.setHighTemperature(Integer.valueOf(high.getText()));
			forecast.setLowTemperature(Integer.valueOf(low.getText()));
			forecastCol.add(forecast);
		}

		return forecastCol;
	}

	public String getMoreInformationLink() {
		if (currentConditions != null) {
			Element moreInfo = currentConditions.element(Constants.URL_TAG);
			return (moreInfo != null) ? moreInfo.getText() : null;
		} else {
			return null;
		}
	}
}
