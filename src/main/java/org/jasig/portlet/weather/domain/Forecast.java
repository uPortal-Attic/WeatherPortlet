/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * This class models forecasted weather.
 * 
 * @author Dustin Schultz
 */
public class Forecast implements Serializable {

	private static final long serialVersionUID = 7114826846843647795L;
	private Date day = null;
	private Double high = null;
	private Double low = null;
	private String condition = null;

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
}
