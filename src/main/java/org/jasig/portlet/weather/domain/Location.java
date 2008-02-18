/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.domain;

import java.io.Serializable;

/**
 * This class models an international location.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class Location implements Serializable {

	private static final long serialVersionUID = -4301186984139190062L;
	private String locationCode = null; // zipcode or some other code that represents this location
	private String city = null;
	private String stateOrCountry = null;
	private Double latitude = null;
	private Double longitude = null;

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateOrCountry() {
		return stateOrCountry;
	}

	public void setStateOrCountry(String stateOrCountry) {
		this.stateOrCountry = stateOrCountry;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getLocationCode() {
		return locationCode;
	}
}
