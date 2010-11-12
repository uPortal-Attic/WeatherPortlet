package org.jasig.portlet.weather.dao.worldwide.xml;

import javax.xml.bind.annotation.XmlElement;

public class WeatherForecast {

    private String date;
    private int tempMaxC;
    private int tempMaxF;
    private int tempMinC;
    private int tempMinF;
    private int windspeedMiles;
    private int windspeedKmph;
    private String windDirection;
    private int weatherCode;
    private String iconUrl;
    private String description;

    public String getDate() {
        return date;
    }

    @XmlElement(name = "date")
    public void setDate(String date) {
        this.date = date;
    }

    public int getTempMaxC() {
        return tempMaxC;
    }

    @XmlElement(name = "tempMaxC")
    public void setTempMaxC(int tempMaxC) {
        this.tempMaxC = tempMaxC;
    }

    public int getTempMaxF() {
        return tempMaxF;
    }

    @XmlElement(name = "tempMaxF")
    public void setTempMaxF(int tempMaxF) {
        this.tempMaxF = tempMaxF;
    }

    public int getTempMinC() {
        return tempMinC;
    }

    @XmlElement(name = "tempMinC")
    public void setTempMinC(int tempMinC) {
        this.tempMinC = tempMinC;
    }

    public int getTempMinF() {
        return tempMinF;
    }

    @XmlElement(name = "tempMinF")
    public void setTempMinF(int tempMinF) {
        this.tempMinF = tempMinF;
    }

    public int getWindspeedMiles() {
        return windspeedMiles;
    }

    @XmlElement(name = "windspeedMiles")
    public void setWindspeedMiles(int windspeedMiles) {
        this.windspeedMiles = windspeedMiles;
    }

    public int getWindspeedKmph() {
        return windspeedKmph;
    }

    @XmlElement(name = "windspeedKmph")
    public void setWindspeedKmph(int windspeedKmph) {
        this.windspeedKmph = windspeedKmph;
    }

    public String getWindDirection() {
        return windDirection;
    }

    @XmlElement(name = "winddir16Point")
    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    @XmlElement(name = "weatherCode")
    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    @XmlElement(name = "weatherIconUrl")
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement(name = "weatherDesc")
    public void setDescription(String description) {
        this.description = description;
    }

}
