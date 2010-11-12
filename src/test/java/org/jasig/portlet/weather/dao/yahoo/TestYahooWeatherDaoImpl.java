package org.jasig.portlet.weather.dao.yahoo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/testContext.xml")
public class TestYahooWeatherDaoImpl {

    YahooWeatherDaoImpl dao;

    @Autowired(required = true)
    private IYahooWeatherParsingService weatherParsingService;
    
    @Autowired(required = true)
    private IYahooLocationParsingService locationParsingService;
    
    @Autowired(required = true)
    private ApplicationContext applicationContext;
    
    @Test
    public void test() throws JAXBException, IOException {
        InputStream is = applicationContext.getResource("classpath:/2502265.xml").getInputStream();
        Weather weather = weatherParsingService.parseWeather(is);
        Assert.assertEquals(weather.getForecast().size(), 2);
        Assert.assertEquals(weather.getCurrentWeather().getCondition(), "Fair");
    }
    
    @Test
    public void testSearch() throws JAXBException, IOException {
        InputStream is = applicationContext.getResource("classpath:/yahooLondonSearch.xml").getInputStream();
        List<Location> locations = locationParsingService.parseLocations(is);
        Assert.assertEquals(locations.size(), 10);
    }

}
