/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.portlet;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;

import org.jasig.portlet.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.mvc.SimpleFormController;
import org.springframework.web.portlet.util.PortletUtils;

/**
 * This class handles navigation from the edit form.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherEditController extends SimpleFormController {
	
	private IWeatherService weatherService = null;
	
	@Override
	protected void processFormSubmission(ActionRequest request,
			ActionResponse response, Object command, BindException errors)
			throws Exception {
		if (request.getParameter("doneSubmit") != null) {
			PortletUtils.clearAllRenderParameters(response);
			response.setPortletMode(PortletMode.VIEW);
			return;
		}
		
		super.processFormSubmission(request, response, command, errors);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(PortletRequest request) throws Exception {
		return new ModelMap("savedLocations", weatherService.getSavedLocationsMap(request.getPreferences()));
	}

	@Autowired
	public void setWeatherService(IWeatherService weatherService) {
		this.weatherService = weatherService;
	}
	
}
