/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import org.apache.log4j.Logger;
import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;

/**
 * Provides partial implementation of service methods. Delegates remaining
 * methods to be implemented by subclass.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public abstract class AbstractWeatherService implements IWeatherService, MessageSourceAware {
	
	private MessageSourceAccessor messageSourceAccessor = null; //needed for resolving messages.properties values	
	private static final Logger logger = Logger.getLogger(AbstractWeatherService.class);

	public abstract Collection<Location> find(String location);
	
	public abstract Weather getWeather(String locationCode, Boolean metric);

	public Map<String, String[]> getSavedLocationsMap(PortletPreferences prefs) {
		String[] locationCodes = prefs.getValues("locationCodes", null);
		String[] locations = prefs.getValues("locations", null);
		String[] metrics = prefs.getValues("metrics", null);
		if (locationCodes != null && locations != null && metrics != null) {
			Map<String, String[]> savedLocationsMap = new HashMap<String, String[]>();
			int length = locationCodes.length;
			for (int i = 0; i < length; i++) {
				String[] locationAttrs = new String[2];
				locationAttrs[0] = locations[i];
				locationAttrs[1] = metrics[i].toLowerCase().equals("true") ? messageSourceAccessor.getMessage("edit.metric.option") : messageSourceAccessor.getMessage("edit.standard.option");
				logger.debug(locationCodes[i] + " --> " + locationAttrs[0] + ", " + locationAttrs[1]);
				savedLocationsMap.put(locationCodes[i], locationAttrs);
			}
			return savedLocationsMap;
		}
		return null;
	}

	public void addWeatherLocation(PortletPreferences prefs,
			String locationCode, String location, String metric) {
		logger.debug("Saving locationCode " + locationCode);
		logger.debug("Saving location " + location);
		logger.debug("Saving metric value of " + metric);
		String[] locationCodes = prefs.getValues("locationCodes", new String[0]);
		String[] locations = prefs.getValues("locations", new String[0]);
		String[] metrics = prefs.getValues("metrics", new String[0]);
		String[] newLocationCodes = addItemToArray(locationCodes, locationCode);
		String[] newLocations = addItemToArray(locations, location);
		String[] newMetrics = addItemToArray(metrics, metric);
		try {
			prefs.setValues("locationCodes", newLocationCodes);
			prefs.setValues("locations", newLocations);
			prefs.setValues("metrics", newMetrics);
			prefs.store();
		} catch (ReadOnlyException roe) {
			roe.printStackTrace();
		} catch (ValidatorException ve) {
			ve.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void deleteWeatherLocation(PortletPreferences prefs,
			String locationCode) {
		logger.debug("Deleting location referred to by locationCode " + locationCode);
		String[] locationCodes = prefs.getValues("locationCodes", null);
		String[] locations = prefs.getValues("locations", null);
		String[] metrics = prefs.getValues("metrics", null);
		int index = findIndex(locationCodes, locationCode);
		String[] newLocationCodes = deleteItemFromArray(locationCodes, index);
		String[] newLocations = deleteItemFromArray(locations, index);
		String[] newMetrics = deleteItemFromArray(metrics, index);
		try {
			prefs.setValues("locationCodes", newLocationCodes);
			prefs.setValues("locations", newLocations);
			prefs.setValues("metrics", newMetrics);
			prefs.store();
		} catch (ReadOnlyException roe) {
			roe.printStackTrace();
		} catch (ValidatorException ve) {
			ve.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	}

	private String[] addItemToArray(String[] srcArray, String item) {
		ArrayList<String> newSrcArray = new ArrayList<String>(Arrays.asList(srcArray));
		newSrcArray.add(item);
		return newSrcArray.toArray(new String[newSrcArray.size()]);
	}
	
	private String[] deleteItemFromArray(String[] srcArray, Integer index) {
		if (index == null) {
			logger.debug("Index to remove was null, returning same srcArray");
			return srcArray;
		}
		ArrayList<String> newSrcArray = new ArrayList<String>(Arrays.asList(srcArray));
		newSrcArray.remove(index.intValue());
		return (newSrcArray.size() > 0 ? newSrcArray.toArray(new String[newSrcArray.size()]) : null);
	}
	
	private Integer findIndex(String[] locationCodes, String locationCode) {
		for (int i = 0; i < locationCodes.length; i++) {
			if (locationCodes[i].equals(locationCode)) {
				logger.debug("Found locationCode " + locationCode + " at index " + i);
				return i;
			}
		}
		return null;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}
}
