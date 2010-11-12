package org.jasig.portlet.weather.dao.worldwide.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="data")
public class WeatherData {

    private CurrentCondition condition;
    
    private List<WeatherForecast> forecasts;
    
    private String weatherUrl;

    public CurrentCondition getCondition() {
        return condition;
    }

    @XmlElement(name="current_condition")
    public void setCondition(CurrentCondition condition) {
        this.condition = condition;
    }

    public List<WeatherForecast> getForecasts() {
        return forecasts;
    }

    @XmlElement(name="weather")
    public void setForecasts(List<WeatherForecast> forecasts) {
        this.forecasts = forecasts;
    }

    public String getWeatherUrl() {
        return weatherUrl;
    }

}
