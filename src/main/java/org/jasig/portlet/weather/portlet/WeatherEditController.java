/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.portlet;

import java.util.Collection;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.SimpleFormController;
import org.springframework.web.portlet.util.PortletUtils;

/**
 * This class handles all interactions from the edit form.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherEditController extends SimpleFormController {

	private IWeatherService weatherService = null; // Spring managed
	
	@Override
	protected boolean suppressValidation(PortletRequest request) {
		if (request.getParameter("cancel") != null) {
			return true;
		}
		
		return super.suppressValidation(request);
	}

	@Override
	protected void processFormSubmission(ActionRequest request,
			ActionResponse response, Object command, BindException errors)
			throws Exception {
		if (request.getParameter("cancel") != null) {
			PortletUtils.clearAllRenderParameters(response);
			response.setPortletMode(PortletMode.VIEW);
			return;
		}
		
		super.processFormSubmission(request, response, command, errors);
	}

	@Override
	protected Object formBackingObject(PortletRequest request) throws Exception {
		PortletPreferences prefs = request.getPreferences();
		String location = prefs.getValue("location", null);
		String metric = prefs.getValue("metric", null);
		WeatherEditCommand command = new WeatherEditCommand();
		//fill the form with any previous preferences
		command.setLocation(location);
		command.setMetric(metric);
		return command;
	}

	@Override
	protected void onSubmitAction(ActionRequest request,
			ActionResponse response, Object command, BindException errors)
			throws Exception {
		//TODO: FIX THIS, logic is incorrect and messy
		WeatherEditCommand formData = (WeatherEditCommand)command;
		PortletPreferences prefs = request.getPreferences();
		String location = prefs.getValue("location", null);
		//if locationCode is set, they chose a location
		if (formData.getCode() != null) {
			if (formData.getLocation().equals(location)) {
				//location is the same as in preferences, don't save, don't search
				PortletUtils.clearAllRenderParameters(response);
				response.setPortletMode(PortletMode.VIEW);
			} else {
				//location is different or a new location, save it, don't search again
				prefs.setValue("location", formData.getLocation());
				prefs.setValue("locationCode", formData.getCode());
				prefs.setValue("metric", formData.getMetric());
				prefs.store();
				PortletUtils.clearAllRenderParameters(response);
				response.setPortletMode(PortletMode.VIEW);
			}
		}
		//if we get here, it means we want to search, which happens in onSubmitRender
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmitRender(RenderRequest request,
			RenderResponse response, Object command, BindException errors)
			throws Exception {
		//TODO: FIX THIS
		WeatherEditCommand formData = (WeatherEditCommand)command;
		Collection<Location> locations = weatherService.find(formData.getLocation());
		Map map = errors.getModel();
		map.put("locations", locations);
		return new ModelAndView("edit", map);
	}

	@Autowired
	public void setWeatherService(IWeatherService weatherService) {
		this.weatherService = weatherService;
	}
	
}
