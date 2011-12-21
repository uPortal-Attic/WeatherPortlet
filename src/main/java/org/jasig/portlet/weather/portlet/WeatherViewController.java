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

package org.jasig.portlet.weather.portlet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    protected final Log logger = LogFactory.getLog(this.getClass());
    
	private IWeatherService weatherService = null; // Spring managed

    @Autowired
    public void setWeatherService(IWeatherService weatherService) {
        this.weatherService = weatherService;
    }
    
    private IViewSelector viewSelector;
    
    @Autowired
    public void setViewSelector(IViewSelector viewSelector) {
        this.viewSelector = viewSelector;
    }

    @RequestMapping("VIEW")
	public ModelAndView viewWeather(RenderRequest request, RenderResponse response) {
        final PortletPreferences prefs = request.getPreferences();
        final List<SavedLocation> savedLocations = this.weatherService.getSavedLocations(prefs);
        
        final List<Weather> weatherList = new ArrayList<Weather>(savedLocations.size());
        final List<SavedLocation> errorList = new ArrayList<SavedLocation>(savedLocations.size());
        for (final SavedLocation savedLocation : savedLocations) {
            try {
                final Weather weather = this.weatherService.getWeather(savedLocation.code, savedLocation.temperatureUnit);
                weatherList.add(weather);
            }
            catch (RuntimeException re) {
                errorList.add(savedLocation);
                this.logger.warn("Failed to load weather for " + savedLocation, re);
            }
        }
        
        final Map<String, Object> model = new LinkedHashMap<String, Object>();
        model.put("weathers", weatherList);
        model.put("errors", errorList);
		model.put("serviceName", this.weatherService.getWeatherProviderName());
		model.put("serviceUrl", this.weatherService.getWeatherProviderLink());

        // indicate if the current user is a guest (unauthenticated) user
        model.put( "isGuest", request.getRemoteUser() == null || request.getRemoteUser().equalsIgnoreCase( "guest" ) );
        
        String extension = viewSelector.getViewNameExtension(request);
        String viewName = "view".concat(extension);
        
		//show view.jsp with a model named 'weather' populated weather data
		return new ModelAndView(viewName, model);
	}
}
