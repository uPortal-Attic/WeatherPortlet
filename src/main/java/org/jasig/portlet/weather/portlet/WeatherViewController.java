/* Copyright 2008 The JA-SIG Collaborative.  All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.portlet;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.jasig.portlet.weather.domain.Weather;
import org.jasig.portlet.weather.service.IWeatherService;
import org.jasig.portlet.weather.service.SavedLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

/**
 * This class handles retrieves information and displays the view page.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
@Controller
public class WeatherViewController {
	private IWeatherService weatherService = null; // Spring managed

    @Autowired
    public void setWeatherService(IWeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @RequestMapping("VIEW")
	public ModelAndView viewWeather(RenderRequest request, RenderResponse response) {
        final PortletPreferences prefs = request.getPreferences();
        final List<SavedLocation> savedLocations = this.weatherService.getSavedLocations(prefs);
        
        final List<Weather> weatherList = new ArrayList<Weather>();
        for (final SavedLocation savedLocation : savedLocations) {
            final Weather weather = this.weatherService.getWeather(savedLocation.code, savedLocation.temperatureUnit);
            weatherList.add(weather);
        }
		
		//show view.jsp with a model named 'weather' populated weather data
		return new ModelAndView("view", "weathers", weatherList);
	}
}
