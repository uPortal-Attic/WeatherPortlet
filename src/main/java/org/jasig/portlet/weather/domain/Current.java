/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.domain;

import java.io.Serializable;

/**
 * This class models current weather.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class Current implements Serializable {

	private static final long serialVersionUID = 2204725860007975876L;
	private Double temperature = null;
	private String condition = null;
	private Double wind = null;
	private String windDirection = null;
	private String humidity = null;

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Double getWind() {
		return wind;
	}

	public void setWind(Double wind) {
		this.wind = wind;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
}
