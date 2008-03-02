/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.jasig.portlet.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.mvc.SimpleFormController;
import org.springframework.web.portlet.util.PortletUtils;

/**
 * Handles deleting weather locations from portlet preferences.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherDeleteController extends SimpleFormController {
	
	private IWeatherService weatherService = null;

	@Override
	protected void onSubmitAction(ActionRequest request,
			ActionResponse response, Object command, BindException errors)
			throws Exception {
		weatherService.deleteWeatherLocation(request.getPreferences(), request.getParameter("key"));
		//Don't do any rendering with this controller
		PortletUtils.clearAllRenderParameters(response);
	}

	@Autowired
	public void setWeatherService(IWeatherService weatherService) {
		this.weatherService = weatherService;
	}
	
}