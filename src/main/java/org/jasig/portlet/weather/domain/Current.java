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
	private Integer temperature = null;
	private String condition = null;
	private String conditionImgPath = null; //relative path
	private Integer conditionImgWidth = null;
	private Integer conditionImgHeight = null;
	private Double windSpeed = null;
	private String windDirection = null;
	private Integer humidity = null;
	private Double pressure = null;

	public Integer getTemperature() {
		return temperature;
	}

	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(Double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public Integer getHumidity() {
		return humidity;
	}

	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

	public String getConditionImgPath() {
		return conditionImgPath;
	}

	public Integer getConditionImgWidth() {
		return conditionImgWidth;
	}

	public void setConditionImgWidth(Integer conditionImgWidth) {
		this.conditionImgWidth = conditionImgWidth;
	}

	public Integer getConditionImgHeight() {
		return conditionImgHeight;
	}

	public void setConditionImgHeight(Integer conditionImgHeight) {
		this.conditionImgHeight = conditionImgHeight;
	}

	public void setConditionImgPath(String conditionImgPath) {
		this.conditionImgPath = conditionImgPath;
	}

	public Double getPressure() {
		return pressure;
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}	
}
