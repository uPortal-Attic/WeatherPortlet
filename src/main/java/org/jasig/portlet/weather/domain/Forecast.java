/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.domain;

import java.io.Serializable;

/**
 * This class models forecasted weather.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class Forecast implements Serializable {

	private static final long serialVersionUID = 7114826846843647795L;
	private String day = null;
	private Integer highTemperature = null;
	private Integer lowTemperature = null;
	private String condition = null;
	private String conditionImgPath = null;
	//this allows for different sizes of for different days
	private Integer conditionImgHeight = null;
	private Integer conditionImgWidth = null;

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Integer getHighTemperature() {
		return highTemperature;
	}

	public void setHighTemperature(Integer highTemperature) {
		this.highTemperature = highTemperature;
	}

	public Integer getLowTemperature() {
		return lowTemperature;
	}

	public void setLowTemperature(Integer lowTemperature) {
		this.lowTemperature = lowTemperature;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public String getConditionImgPath() {
		return conditionImgPath;
	}

	public Integer getConditionImgHeight() {
		return conditionImgHeight;
	}

	public void setConditionImgHeight(Integer conditionImgHeight) {
		this.conditionImgHeight = conditionImgHeight;
	}

	public Integer getConditionImgWidth() {
		return conditionImgWidth;
	}

	public void setConditionImgWidth(Integer conditionImgWidth) {
		this.conditionImgWidth = conditionImgWidth;
	}

	public void setConditionImgPath(String conditionImgPath) {
		this.conditionImgPath = conditionImgPath;
	}
}
