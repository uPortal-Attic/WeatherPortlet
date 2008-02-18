/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather;

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

	// Read only values
	private static final String BASE = "images/accuweather/";
	private static final String EXTENSION = ".png";
	private static final Integer LOGO_WIDTH = 150;
	private static final Integer LOGO_HEIGHT = 12;
	private static final Integer CONDITION_WIDTH = 64;
	private static final Integer CONDITION_HEIGHT = 40;

	public WeatherUtil(String locationCode, Boolean metric) {
		logger.debug("Retrieving weather xml for location " + locationCode
				+ " with metric " + metric);
		String accuweatherUrl = "http://uport.accu-weather.com/widget/uport/weather-data.asp?location="
				+ locationCode + "&metric=" + ((metric) ? "1" : "0");
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(accuweatherUrl);
		} catch (DocumentException de) {
			de.printStackTrace();
			throw new RuntimeException("Unable to retrieve xml", de);
		}
		logger.debug("Successfully retrieved weather xml");

		// get top level elements
		root = document.getRootElement();
		units = root.element("units");
		local = root.element("local");
		currentConditions = root.element("currentconditions");
		forecast = root.element("forecast");
		planets = root.element("planets");
	}

	public String getCity() {
		if (local != null) {
			Element localCity = local.element("city");
			return (localCity != null) ? localCity.getText() : null;
		} else {
			return null;
		}
	}

	public String getState() {
		if (local != null) {
			Element localState = local.element("state");
			return (localState != null) ? localState.getText() : null;
		} else {
			return null;
		}
	}

	public Double getLatitude() {
		if (local != null) {
			Element localLat = local.element("lat");
			return (localLat != null) ? Double.valueOf(localLat.getText())
					: null;
		} else {
			return null;
		}
	}

	public Double getLongitude() {
		if (local != null) {
			Element localLon = local.element("lon");
			return (localLon != null) ? Double.valueOf(localLon.getText())
					: null;
		} else {
			return null;
		}
	}

	public Integer getCurrentTemperature() {
		if (currentConditions != null) {
			Element currTemp = currentConditions.element("temperature");
			return (currTemp != null) ? Integer.valueOf(currTemp.getText())
					: null;
		} else {
			return null;
		}
	}

	public String getCurrentCondition() {
		if (currentConditions != null) {
			Element currCondition = currentConditions.element("weathertext");
			return (currCondition != null) ? currCondition.getText() : null;
		} else {
			return null;
		}
	}

	public String getCurrentConditionImgPath() {
		if (currentConditions != null) {
			Element currImgPath = currentConditions.element("weathericon");
			return (currImgPath != null) ? BASE + currImgPath.getText()
					+ EXTENSION : null;
		} else {
			return null;
		}
	}

	public Double getWindSpeed() {
		if (currentConditions != null) {
			Element currWindSpeed = currentConditions.element("windspeed");
			return (currWindSpeed != null) ? Double.valueOf(currWindSpeed
					.getText()) : null;
		} else {
			return null;
		}
	}

	public String getWindDirection() {
		if (currentConditions != null) {
			Element currWindDir = currentConditions.element("winddirection");
			return (currWindDir != null) ? currWindDir.getText() : null;
		} else {
			return null;
		}
	}

	public Integer getHumidity() {
		if (currentConditions != null) {
			Element currHumidity = currentConditions.element("humidity");
			return (currHumidity != null) ? Integer.valueOf(currHumidity
					.getText()
					.substring(0, currHumidity.getText().indexOf('%'))) : null;
		} else {
			return null;
		}
	}

	public String getPressureUnit() {
		if (units != null) {
			Element pressure = units.element("pres");
			return (pressure != null) ? pressure.getText() : null;
		} else {
			return null;
		}
	}

	public String getTemperatureUnit() {
		if (units != null) {
			Element temperature = units.element("temp");
			return (temperature != null) ? temperature.getText() : null;
		} else {
			return null;
		}
	}

	public String getWindUnit() {
		if (units != null) {
			Element wind = units.element("speed");
			return (wind != null) ? wind.getText() : null;
		} else {
			return null;
		}
	}

	public Double getPressure() {
		if (currentConditions != null) {
			Element currPressure = currentConditions.element("pressure");
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
			Element sun = planets.element("sun");
			Attribute set = sun.attribute("set");
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
		List<Element> forecastElements = forecast.elements("day");

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
				timeOfDay = ele.element("nighttime");
			} else {
				// its day
				timeOfDay = ele.element("daytime");
			}

			Element day = ele.element("daycode");
			Element condition = timeOfDay.element("txtshort");
			Element high = timeOfDay.element("hightemperature");
			Element low = timeOfDay.element("lowtemperature");
			Element img = timeOfDay.element("weathericon");

			Forecast forecast = new Forecast();
			forecast.setCondition(condition.getText());
			forecast.setConditionImgPath(BASE + img.getText() + EXTENSION);
			forecast.setDay(day.getText().substring(0, 3)); // Just get 3
			// letters
			forecast.setHighTemperature(Integer.valueOf(high.getText()));
			forecast.setLowTemperature(Integer.valueOf(low.getText()));
			forecast.setConditionImgWidth(CONDITION_WIDTH);
			forecast.setConditionImgHeight(CONDITION_HEIGHT);
			forecastCol.add(forecast);
		}

		return forecastCol;
	}

	public String getMoreInformationLink() {
		if (currentConditions != null) {
			Element moreInfo = currentConditions.element("url");
			return (moreInfo != null) ? moreInfo.getText() : null;
		} else {
			return null;
		}
	}

	public String getLogoPath() {
		return BASE + "logo" + EXTENSION;
	}

	public Integer getLogoHeight() {
		return LOGO_HEIGHT;
	}

	public Integer getLogoWidth() {
		return LOGO_WIDTH;
	}

	public Integer getImgConditionHeight() {
		return CONDITION_HEIGHT;
	}

	public Integer getImgConditionWidth() {
		return CONDITION_WIDTH;
	}
}
