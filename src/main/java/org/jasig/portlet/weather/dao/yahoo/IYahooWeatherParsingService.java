package org.jasig.portlet.weather.dao.yahoo;

import java.io.InputStream;

import org.jasig.portlet.weather.domain.Weather;

public interface IYahooWeatherParsingService {

    public Weather parseWeather(InputStream xml);
    
}
