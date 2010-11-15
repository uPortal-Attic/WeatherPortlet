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

package org.jasig.portlet.weather.dao.worldwide;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.jasig.portlet.weather.TemperatureUnit;
import org.jasig.portlet.weather.domain.Forecast;
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
public class WorldWeatherOnlineUnmarshallingTest {

    @Autowired(required = true)
    private ApplicationContext applicationContext;

    private WorldWeatherOnlineDaoImpl dao;
    
    @Before
    public void setUp() {
        dao = new WorldWeatherOnlineDaoImpl();
        dao.setImageMapping(applicationContext.getResource("classpath:/wwo-image-mapping.properties"));
    }
    
    @Test
    public void testMetric() throws JAXBException, IOException, ParseException {
        InputStream is = applicationContext.getResource("classpath:/seattleWeather.xml").getInputStream();
        Weather weather = dao.deserializeWeatherResult(is, TemperatureUnit.C);
        Assert.assertEquals(weather.getForecast().size(), 3);
        Assert.assertEquals(weather.getForecast().toArray(new Forecast[]{})[0].getDay(), "Thu");
        Assert.assertEquals(weather.getForecast().toArray(new Forecast[]{})[0].getImgName(), "01");
        Assert.assertEquals(weather.getForecast().toArray(new Forecast[]{})[0].getImgUrl(), null);
        Assert.assertEquals(weather.getCurrentWeather().getCondition(), "Light drizzle");
        Assert.assertEquals(weather.getCurrentWeather().getImgName(), "12");
        Assert.assertEquals(weather.getCurrentWeather().getTemperature(), new Integer(7));
    }

    @Test
    public void testUS() throws JAXBException, IOException, ParseException {
        InputStream is = applicationContext.getResource("classpath:/seattleWeather.xml").getInputStream();
        Weather weather = dao.deserializeWeatherResult(is, TemperatureUnit.F);
        Assert.assertEquals(weather.getForecast().size(), 3);
        Assert.assertEquals(weather.getForecast().toArray(new Forecast[]{})[0].getDay(), "Thu");
        Assert.assertEquals(weather.getCurrentWeather().getCondition(), "Light drizzle");
        Assert.assertEquals(weather.getCurrentWeather().getTemperature(), new Integer(44));
    }
    
    @Test
    public void testSearch() throws JAXBException, IOException {
        InputStream is = applicationContext.getResource("classpath:/londonSearch.xml").getInputStream();
        List<Location> locations = dao.deserializeSearchResults(is);
        Assert.assertEquals(locations.size(), 3);
        Assert.assertEquals(locations.get(0).getLocationCode(), "London, United Kingdom");
    }

}
