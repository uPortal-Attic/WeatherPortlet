/*
 * Created on Feb 15, 2008
 *
 * Copyright(c) Yale University, Feb 15, 2008.  All rights reserved.
 * (See licensing and redistribution disclosures at end of this file.)
 * 
 */
package edu.yale.its.tp.portlets.YahooServices.weather;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class YahooWeatherCurrentConditions {

	private String windChill;

	private String windDirection;

	private String windSpeed;

	private String humidity;

	private String visibility;

	private String pressure;

	private String pressureDirection;

	private String sunriseTime;

	private String sunsetTime;

	private String conditionDescription;

	private String conditionCode;

	private String temp;

	public YahooWeatherCurrentConditions(Element e) {
		NodeList n = e.getElementsByTagName("yweather:wind");
		if (n.getLength() > 0) {
			Element l = (Element) n.item(0);
			if (l.getAttribute("chill") != null)
				this.windChill = l.getAttribute("chill");
			if (l.getAttribute("direction") != null)
				this.windDirection = l.getAttribute("direction");
			if (l.getAttribute("speed") != null)
				this.windSpeed = l.getAttribute("speed");
		}
		n = e.getElementsByTagName("yweather:atmosphere");
		if (n.getLength() > 0) {
			Element l = (Element) n.item(0);
			if (l.getAttribute("humidity") != null)
				this.humidity = l.getAttribute("humidity");
			if (l.getAttribute("visibility") != null)
				this.visibility = l.getAttribute("visibility");
			if (l.getAttribute("pressure") != null)
				this.pressure = l.getAttribute("pressure");
			if (l.getAttribute("rising") != null)
				this.pressureDirection = l.getAttribute("rising");
		}
		n = e.getElementsByTagName("yweather:astronomy");
		if (n.getLength() > 0) {
			Element l = (Element) n.item(0);
			if (l.getAttribute("sunrise") != null)
				this.sunriseTime = l.getAttribute("sunrise");
			if (l.getAttribute("sunset") != null)
				this.sunsetTime = l.getAttribute("sunset");
		}
		n = e.getElementsByTagName("yweather:condition");
		if (n.getLength() > 0) {
			Element l = (Element) n.item(0);
			if (l.getAttribute("text") != null)
				this.conditionDescription = l.getAttribute("text");
			if (l.getAttribute("code") != null)
				this.conditionCode = l.getAttribute("code");
			if (l.getAttribute("temp") != null)
				this.temp = l.getAttribute("temp");
		}

	}

	public String getWindDirectionDescription() {
		int dir = Integer.valueOf(this.windDirection);
		if (dir <= 22 || dir >= 337) {
			return "N";
		} else if (dir > 22 && dir <= 67) {
			return "NE";
		} else if (dir > 67 && dir <= 112) {
			return "E";
		} else if (dir > 112 && dir <= 157) {
			return "SE";
		} else if (dir > 157 && dir <= 202) {
			return "S";
		} else if (dir > 202 && dir <= 247) {
			return "SW";
		} else if (dir > 247 && dir <= 292) {
			return "W";
		} else {
			return "NW";
		}
	}

	public String getConditionCode() {
		return conditionCode;
	}

	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}

	public String getConditionDescription() {
		return conditionDescription;
	}

	public void setConditionDescription(String conditionDescription) {
		this.conditionDescription = conditionDescription;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getPressureDirection() {
		return pressureDirection;
	}

	public void setPressureDirection(String pressureDirection) {
		this.pressureDirection = pressureDirection;
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

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getWindChill() {
		return windChill;
	}

	public void setWindChill(String windChill) {
		this.windChill = windChill;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

}

/*
 * YahooWeatherCurrentConditions.java
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