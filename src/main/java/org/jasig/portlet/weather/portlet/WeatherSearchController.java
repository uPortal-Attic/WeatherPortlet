/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.portlet;

import java.util.Collection;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.SimpleFormController;

/**
 * Handles searching for locations
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherSearchController extends SimpleFormController {

	private IWeatherService weatherService = null; // Spring managed
	
	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmitRender(RenderRequest request,
			RenderResponse response, Object command, BindException errors)
			throws Exception {
		WeatherEditCommand formData = (WeatherEditCommand)command;
		Map map = errors.getModel();
		if (StringUtils.hasText(formData.getLocation())) {
			Collection<Location> locations = weatherService.find(formData.getLocation());
			map.put("locationResults", locations);
			if (locations == null) {
				errors.rejectValue("location", "location.not.found");
			}
		} else {
			errors.rejectValue("location", "invalid.location");
		}
		//we're returning a new model so we have to add saved locations again to display them
		map.put("savedLocations", weatherService.getSavedLocationsMap(request.getPreferences()));
		return showForm(request, response, errors, map);
	}
	
	
	@Autowired
	public void setWeatherService(IWeatherService weatherService) {
		this.weatherService = weatherService;
	}
}