/* Copyright 2008 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package org.jasig.portlet.weather;

public class Observation {
	String location;
	String stationId;
	String weather;
	String iconUrlBase;
	String iconUrlName;
	String tempF;
	String windMph;
	String windDir;
	String heatIndexF;
	String windchillF;
	String relativeHumidity;
	
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getIconUrlBase() {
		return iconUrlBase;
	}
	public void setIconUrlBase(String iconUrlBase) {
		this.iconUrlBase = iconUrlBase;
	}
	public String getIconUrlName() {
		return iconUrlName;
	}
	public void setIconUrlName(String iconUrlName) {
		this.iconUrlName = iconUrlName;
	}
	public String getTempF() {
		return tempF;
	}
	public void setTempF(String tempF) {
		this.tempF = tempF;
	}
	public String getWindMph() {
		return windMph;
	}
	public void setWindMph(String windMph) {
		this.windMph = windMph;
	}
	public String getWindDir() {
		return windDir;
	}
	public void setWindDir(String windDir) {
		this.windDir = windDir;
	}
	public String getHeatIndexF() {
		return heatIndexF;
	}
	public void setHeatIndexF(String heatIndexF) {
		this.heatIndexF = heatIndexF;
	}
	public String getWindchillF() {
		return windchillF;
	}
	public void setWindchillF(String windchillF) {
		this.windchillF = windchillF;
	}
	public String getRelativeHumidity() {
		return relativeHumidity;
	}
	public void setRelativeHumidity(String relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
