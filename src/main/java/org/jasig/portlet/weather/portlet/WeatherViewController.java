/* Copyright 2008 The JA-SIG Collaborative.  All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.portlet;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.jasig.portlet.weather.domain.Weather;
import org.jasig.portlet.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractController;

/**
 * This class handles retrieves information and displays the view page.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherViewController extends AbstractController {

	private IWeatherService weatherService = null; // Spring managed

	@Override
	protected ModelAndView handleRenderRequestInternal(RenderRequest request,
			RenderResponse response) throws Exception {
		PortletPreferences prefs = request.getPreferences();
		String locationCode = prefs.getValue("locationCode", null);
		String metrics = prefs.getValue("metric", null);
		Weather weather = weatherService.getWeather(locationCode, (metrics != null) ? new Boolean(metrics) : null);
		//show view.jsp with a model named 'weather' populated weather data
		return new ModelAndView("view", "weather", weather);
	}

	@Autowired
	public void setWeatherService(IWeatherService weatherService) {
		this.weatherService = weatherService;
	}
}
