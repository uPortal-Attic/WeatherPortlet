/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.portlet.weather.dao.worldwide.xml;

import javax.xml.bind.annotation.XmlElement;

public class CurrentCondition {

    private String observationTime;
    private int tempC;
    private int tempF;
    private int weatherCode;
    private String weatherIconUrl;
    private String description;
    private double speedMiles;
    private double speedKmph;
    private String windDir;
    private double humidity;
    private double pressure;

    public String getObservationTime() {
        return observationTime;
    }

    @XmlElement(name = "observation_time")
    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
    }

    public int getTempC() {
        return tempC;
    }

    @XmlElement(name = "temp_C")
    public void setTempC(int tempC) {
        this.tempC = tempC;
    }

    public int getTempF() {
        return tempF;
    }

    @XmlElement(name = "temp_F")
    public void setTempF(int tempF) {
        this.tempF = tempF;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    @XmlElement(name = "weatherCode")
    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    @XmlElement(name = "weatherIconUrl")
    public void setWeatherIconUrl(String weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement(name = "weatherDesc")
    public void setDescription(String description) {
        this.description = description;
    }

    public double getSpeedMiles() {
        return speedMiles;
    }

    @XmlElement(name = "windspeedMiles")
    public void setSpeedMiles(double speedMiles) {
        this.speedMiles = speedMiles;
    }

    public double getSpeedKmph() {
        return speedKmph;
    }

    @XmlElement(name = "windspeedKmph")
    public void setSpeedKmph(double speedKmph) {
        this.speedKmph = speedKmph;
    }

    public String getWindDir() {
        return windDir;
    }

    @XmlElement(name = "winddir16Point")
    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public double getHumidity() {
        return humidity;
    }

    @XmlElement(name = "humidity")
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    @XmlElement(name = "pressure")
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

}
