/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.portlet;

/**
 * This class is used for binding/saving data from the edit form.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherEditCommand {

	private String location = null; //this represents the text box
	private String locationCode = null; //this is internal and passed to getWeather
	private String metric = null; //this represents the drop down

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}
}
