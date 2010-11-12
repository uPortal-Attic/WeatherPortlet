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
