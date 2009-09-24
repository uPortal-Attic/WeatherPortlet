/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.portlet;

import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.jasig.portlet.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.ParameterizableViewController;

/**
 * This class handles navigation from the edit form.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherEditController extends ParameterizableViewController {
	
	private IWeatherService weatherService = null;
	
	@Autowired
	public void setWeatherService(IWeatherService weatherService) {
		this.weatherService = weatherService;
	}
	
	@Override
	protected ModelAndView handleRenderRequestInternal(RenderRequest request,
			RenderResponse response) throws Exception {
		
		Map<String,String[]> locations = weatherService.getSavedLocationsMap(request.getPreferences());
		
		ModelMap model = new ModelMap("savedLocations", locations);
		return new ModelAndView(getViewName(), model);
	}

}
