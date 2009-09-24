/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.portlet;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.jasig.portlet.weather.service.IWeatherService;
import org.jasig.web.portlet.mvc.AbstractAjaxController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Handles deleting weather locations from portlet preferences.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherDeleteController extends AbstractAjaxController {
	
	private IWeatherService weatherService = null;

	@Autowired
	public void setWeatherService(IWeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@Override
	protected Map<Object, Object> handleAjaxRequestInternal(ActionRequest request,
			ActionResponse response) throws Exception {
		Map<Object, Object> model = new HashMap<Object, Object>();

		weatherService.deleteWeatherLocation(request.getPreferences(), 
				request.getParameter("key"));
		
        model.put("status", "success");
		return model;
	}
	
}