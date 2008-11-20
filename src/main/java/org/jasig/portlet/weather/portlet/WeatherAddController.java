/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import org.jasig.portlet.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.mvc.SimpleFormController;
import org.springframework.web.portlet.util.PortletUtils;

/**
 * Handles adding weather locations to portlet preferences
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherAddController extends SimpleFormController {
	
	private IWeatherService weatherService = null;
	
	@Override
	protected void processFormSubmission(ActionRequest request,
			ActionResponse response, Object command, BindException errors)
			throws Exception {
		if (request.getParameter("cancelSearchSubmit") != null) {
			PortletUtils.clearAllRenderParameters(response);
			return;
		}
		
		super.processFormSubmission(request, response, command, errors);
	}

	@Override
	protected void onSubmitAction(ActionRequest request,
			ActionResponse response, Object command, BindException errors)
			throws Exception {
		WeatherEditCommand formData = (WeatherEditCommand)command;
		PortletPreferences prefs = request.getPreferences();
		//make sure they chose a location
		if (StringUtils.hasText(formData.getLocationCode())) {
			String locationCode = formData.getLocationCode().substring((formData.getLocationCode().indexOf('+') + 1), formData.getLocationCode().length());
			String location = formData.getLocationCode().substring(0, formData.getLocationCode().indexOf('+'));;
			String metric = formData.getMetric();
			weatherService.addWeatherLocation(prefs, locationCode, location, metric);
		} 
		
		//Don't do any rendering with this controller
		PortletUtils.clearAllRenderParameters(response);
	}

	@Autowired
	public void setWeatherService(IWeatherService weatherService) {
		this.weatherService = weatherService;
	}
}
