/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.portlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.jasig.portlet.weather.DuplicateLocationException;
import org.jasig.portlet.weather.TemperatureUnit;
import org.jasig.portlet.weather.service.IWeatherService;
import org.jasig.portlet.weather.service.SavedLocation;
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
        final PortletPreferences preferences = request.getPreferences();
        final List<SavedLocation> savedLocations = this.weatherService.getSavedLocations(preferences);
		
		ModelMap model = new ModelMap("savedLocations", savedLocations);
		return new ModelAndView("edit", model);
	}

	@RequestMapping(params = {"action=add"})
    public void addCity(
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "locationCode", required = false) String locationCode,
            @RequestParam(value = "unit", required = false) TemperatureUnit unit,
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
        try {
            final SavedLocation savedLocation = this.weatherService.addWeatherLocation(prefs, locationCode, location, unit == null ? TemperatureUnit.F : unit);
            model.put("status", "success");
            model.put("location", savedLocation);
        }
        catch (DuplicateLocationException dle) {
            model.put("status", "failure");
            model.put("cause", "DUPLICATE_LOCATION");
        }
        
        this.ajaxPortletSupport.redirectAjaxResponse("ajax/json", model, request, response);
    }
	
	@RequestMapping(params = {"action=saveOrder"})
    public void saveCityOrder(
            @RequestParam(value = "locationCodes") String locationCodes[],
            ActionRequest request, ActionResponse response) throws Exception {

	    final Map<Object, Object> model = new HashMap<Object, Object>();
        
	    
	    final PortletPreferences prefs = request.getPreferences();
	    final List<SavedLocation> savedLocations = this.weatherService.getSavedLocations(prefs);
	    
	    if (savedLocations.size() != locationCodes.length) {
	        model.put("status", "failure");
	        model.put("message", "updated locations array is not the same size (" + locationCodes.length + ") as the saved locations array (" + savedLocations.size() + ")");
            this.ajaxPortletSupport.redirectAjaxResponse("ajax/json", model, request, response);
            return;
	    }
	    
	    final Map<String, SavedLocation> locations = new LinkedHashMap<String, SavedLocation>();
	    for (final SavedLocation savedLocation : savedLocations) {
	        locations.put(savedLocation.code, savedLocation);
	    }
	    
	    final List<SavedLocation> updatedLocations = new ArrayList<SavedLocation>(savedLocations.size());
	    for (final String locationCode : locationCodes) {
	        updatedLocations.add(locations.get(locationCode));
	    }
	    
	    this.weatherService.saveLocations(updatedLocations, prefs);
	    
        model.put("status", "success");
        this.ajaxPortletSupport.redirectAjaxResponse("ajax/json", model, request, response);
	}
	
    @RequestMapping(params = {"action=updateUnits"})
    public void updateCityUnits(
            @RequestParam(value = "locationCode") String locationCode,
            @RequestParam(value = "unit") TemperatureUnit unit,
            ActionRequest request, ActionResponse response) throws Exception {
        
        final Map<Object, Object> model = new HashMap<Object, Object>();
        
        final PortletPreferences prefs = request.getPreferences();
        final List<SavedLocation> savedLocations = this.weatherService.getSavedLocations(prefs);
        
        final List<SavedLocation> updatedLocations = new ArrayList<SavedLocation>(savedLocations.size());
        for (final SavedLocation savedLocation : savedLocations) {
            if (savedLocation.code.equals(locationCode)) {
                updatedLocations.add(new SavedLocation(
                        savedLocation.code, 
                        savedLocation.name, 
                        unit));
            }
            else {
                updatedLocations.add(savedLocation);
            }
        }
        
        this.weatherService.saveLocations(updatedLocations, prefs);
        
        model.put("status", "success");
        this.ajaxPortletSupport.redirectAjaxResponse("ajax/json", model, request, response);
    }
	
	@RequestMapping(params = {"action=delete"})
    public void deleteCity(
            @RequestParam(value = "locationCode") String locationCode,
            ActionRequest request, ActionResponse response) throws Exception {
        Map<Object, Object> model = new HashMap<Object, Object>();

        final PortletPreferences prefs = request.getPreferences();
        this.weatherService.deleteWeatherLocation(prefs, locationCode);
        
        model.put("status", "success");
        this.ajaxPortletSupport.redirectAjaxResponse("ajax/json", model, request, response);
    }
}
