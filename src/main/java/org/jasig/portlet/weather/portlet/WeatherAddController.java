/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.portlet;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import net.sf.json.JSONObject;

import org.jasig.portlet.weather.service.IWeatherService;
import org.jasig.web.portlet.mvc.AbstractAjaxController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * Handles adding weather locations to portlet preferences
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherAddController extends AbstractAjaxController {
	
	private IWeatherService weatherService = null;
	
	@Autowired
	public void setWeatherService(IWeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@Override
	protected Map<Object, Object> handleAjaxRequestInternal(ActionRequest request,
			ActionResponse response) throws Exception {
		PortletPreferences prefs = request.getPreferences();
		
		Map<Object, Object> model = new HashMap<Object, Object>();
		
		// collect the parameters for the new location
		String location = request.getParameter("location");
		String locationCode = request.getParameter("locationCode");
		String metric = request.getParameter("metric");
		
		// validate the submitted data
		if (!StringUtils.hasText(location) || !StringUtils.hasLength(locationCode)) {
	        model.put("status", "failure");
			return model;
		}
		
		// add the new location to the user's preferences
		weatherService.addWeatherLocation(prefs, locationCode, location, metric);

        model.put("status", "success");
		return model;
	}
}
