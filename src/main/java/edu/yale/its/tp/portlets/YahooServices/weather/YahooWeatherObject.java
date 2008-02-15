/*
 * Created on Feb 15, 2008
 *
 * Copyright(c) Yale University, Feb 15, 2008.  All rights reserved.
 * (See licensing and redistribution disclosures at end of this file.)
 * 
 */
package edu.yale.its.tp.portlets.YahooServices.weather;

import java.io.Serializable;
import java.util.List;

public class YahooWeatherObject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String location;
	private String city;
	private String region;
	private String country;
	private String tempUnits;
	private String distUnits;
	private String pressureUnits;
	private String speedUnits;
	private double latitude;
	private double longitude;
	private int windChill;
	private int windDirection;
	private int windSpeed;
	private int humidity;
	private int visibility;
	private float pressure;
	private int pressureDirection;
	private String sunriseTime;
	private String sunsetTime;
	private String conditionDescription;
	private int conditionCode;
	private int temp;
	private String link;
	
	private List<YahooWeatherForecast> forecasts;
	
	public YahooWeatherObject() { }
	
	/**
	 * 
	 * @return
	 */
	public String getWindDisplayText() {

		// if there's no wind, the direction doesn't matter
		if (this.windSpeed == 0)
			return "0" + speedUnits;

		// get a human-friendly description of the wind direction
		String dirTxt = "";
		int dir = Integer.valueOf(this.windDirection);
		if (dir <= 22 || dir >= 337) {
			dirTxt = "N";
		} else if (dir > 22 && dir <= 67) {
			dirTxt = "NE";
		} else if (dir > 67 && dir <= 112) {
			dirTxt = "E";
		} else if (dir > 112 && dir <= 157) {
			dirTxt = "SE";
		} else if (dir > 157 && dir <= 202) {
			dirTxt = "S";
		} else if (dir > 202 && dir <= 247) {
			dirTxt = "SW";
		} else if (dir > 247 && dir <= 292) {
			dirTxt = "W";
		} else {
			dirTxt = "NW";
		}

		return this.windSpeed + " " + this.speedUnits + " " + dirTxt;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getConditionDescription() {
		return conditionDescription;
	}

	public void setConditionDescription(String conditionDescription) {
		this.conditionDescription = conditionDescription;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDistUnits() {
		return distUnits;
	}

	public void setDistUnits(String distUnits) {
		this.distUnits = distUnits;
	}

	public List<YahooWeatherForecast> getForecasts() {
		return forecasts;
	}

	public void setForecasts(List<YahooWeatherForecast> forecasts) {
		this.forecasts = forecasts;
	}

	public String getPressureUnits() {
		return pressureUnits;
	}

	public void setPressureUnits(String pressureUnits) {
		this.pressureUnits = pressureUnits;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSpeedUnits() {
		return speedUnits;
	}

	public void setSpeedUnits(String speedUnits) {
		this.speedUnits = speedUnits;
	}

	public String getSunriseTime() {
		return sunriseTime;
	}

	public void setSunriseTime(String sunriseTime) {
		this.sunriseTime = sunriseTime;
	}

	public String getSunsetTime() {
		return sunsetTime;
	}

	public void setSunsetTime(String sunsetTime) {
		this.sunsetTime = sunsetTime;
	}

	public String getTempUnits() {
		return tempUnits;
	}

	public void setTempUnits(String tempUnits) {
		this.tempUnits = tempUnits;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getWindChill() {
		return windChill;
	}

	public void setWindChill(int windChill) {
		this.windChill = windChill;
	}

	public int getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(int windDirection) {
		this.windDirection = windDirection;
	}

	public int getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(int windSpeed) {
		this.windSpeed = windSpeed;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	public int getPressureDirection() {
		return pressureDirection;
	}

	public void setPressureDirection(int pressureDirection) {
		this.pressureDirection = pressureDirection;
	}

	public int getConditionCode() {
		return conditionCode;
	}

	public void setConditionCode(int conditionCode) {
		this.conditionCode = conditionCode;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public float getPressure() {
		return pressure;
	}

	public void setPressure(float pressure) {
		this.pressure = pressure;
	}

	
	
}

/*
 * YahooWeatherObject.java
 * 
 * Copyright (c) Feb 15, 2008 Yale University. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE, ARE EXPRESSLY DISCLAIMED. IN NO EVENT SHALL
 * YALE UNIVERSITY OR ITS EMPLOYEES BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED, THE COSTS OF PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED IN ADVANCE OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Redistribution and use of this software in source or binary forms, with or
 * without modification, are permitted, provided that the following conditions
 * are met.
 * 
 * 1. Any redistribution must include the above copyright notice and disclaimer
 * and this list of conditions in any related documentation and, if feasible, in
 * the redistributed software.
 * 
 * 2. Any redistribution must include the acknowledgment, "This product includes
 * software developed by Yale University," in any related documentation and, if
 * feasible, in the redistributed software.
 * 
 * 3. The names "Yale" and "Yale University" must not be used to endorse or
 * promote products derived from this software.
 */