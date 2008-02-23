/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.domain;

import java.io.Serializable;
import java.util.Collection;

/**
 * This class models general weather for a certain location including the
 * current weather and optional forecast weather.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class Weather implements Serializable {

	private static final long serialVersionUID = -3036893410887609714L;
	private Current currentWeather = null;
	private Location location = null;
	private Collection<Forecast> forecast = null;
	private String pressureUnit = null;
	private String temperatureUnit = null;
	private String windUnit = null;
	private String moreInformationLink = null; //for this weather object

	public Current getCurrentWeather() {
		return currentWeather;
	}

	public void setCurrentWeather(Current currentWeather) {
		this.currentWeather = currentWeather;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Collection<Forecast> getForecast() {
		return forecast;
	}

	public void setForecast(Collection<Forecast> forecast) {
		this.forecast = forecast;
	}

	public String getPressureUnit() {
		return pressureUnit;
	}

	public void setPressureUnit(String pressureUnit) {
		this.pressureUnit = pressureUnit;
	}

	public String getTemperatureUnit() {
		return temperatureUnit;
	}

	public void setTemperatureUnit(String temperatureUnit) {
		this.temperatureUnit = temperatureUnit;
	}

	public String getWindUnit() {
		return windUnit;
	}

	public void setWindUnit(String windUnit) {
		this.windUnit = windUnit;
	}

	public String getMoreInformationLink() {
		return moreInformationLink;
	}

	public void setMoreInformationLink(String moreInformationLink) {
		this.moreInformationLink = moreInformationLink;
	}
}
