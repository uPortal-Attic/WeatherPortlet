/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.portlet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.jasig.portlet.weather.service.IWeatherService;
import org.jasig.web.service.AjaxPortletSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;

/**
 * This class handles navigation from the edit form.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
@Controller
@RequestMapping("EDIT")
public class WeatherEditController {
	private IWeatherService weatherService = null;
	private AjaxPortletSupport ajaxPortletSupport;
	
	@Autowired
	public void setWeatherService(IWeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@Autowired
    public void setAjaxPortletSupport(AjaxPortletSupport ajaxPortletSupport) {
        this.ajaxPortletSupport = ajaxPortletSupport;
    }

    @RequestMapping
	public ModelAndView renderEditView(RenderRequest request, RenderResponse response) throws Exception {
		Map<String,String[]> locations = weatherService.getSavedLocationsMap(request.getPreferences());
		
		ModelMap model = new ModelMap("savedLocations", locations);
		return new ModelAndView("edit", model);
	}

	@RequestMapping(params = {"action=add"})
    public void addCity(
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "locationCode", required = false) String locationCode,
            @RequestParam(value = "metric", required = false) String metric,
            ActionRequest request, ActionResponse response) throws Exception {
        PortletPreferences prefs = request.getPreferences();
        
        Map<Object, Object> model = new HashMap<Object, Object>();
        
        // validate the submitted data
        if (!StringUtils.hasText(location) || !StringUtils.hasLength(locationCode)) {
            model.put("status", "failure");
            this.ajaxPortletSupport.redirectAjaxResponse("ajax/json", model, request, response);
            return;
        }
        
        // make sure this location isn't already in our list
        String[] existing = prefs.getValues("locationCodes", null);
        if (existing != null && Arrays.binarySearch(existing, locationCode) >= 0) {
            model.put("status", "failure");
            model.put("cause", "DUPLICATE_LOCATION");
            this.ajaxPortletSupport.redirectAjaxResponse("ajax/json", model, request, response);
            return;
        }
        
        // add the new location to the user's preferences
        weatherService.addWeatherLocation(prefs, locationCode, location, metric);

        model.put("status", "success");
        this.ajaxPortletSupport.redirectAjaxResponse("ajax/json", model, request, response);
    }
	


	@RequestMapping(params = {"action=delete"})
    public void deleteCity(ActionRequest request, ActionResponse response) throws Exception {
        Map<Object, Object> model = new HashMap<Object, Object>();

        weatherService.deleteWeatherLocation(request.getPreferences(),  request.getParameter("key"));
        
        model.put("status", "success");
        this.ajaxPortletSupport.redirectAjaxResponse("ajax/json", model, request, response);
    }
}
