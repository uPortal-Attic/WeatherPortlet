/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.portlet.weather.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jasig.portlet.weather.DuplicateLocationException;
import org.jasig.portlet.weather.TemperatureUnit;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.PermissionDeniedDataAccessException;

/**
 * Provides partial implementation of service methods. Delegates remaining
 * methods to be implemented by subclass.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public abstract class AbstractWeatherService implements IWeatherService {
    private static final String METRICS = "metrics";
    private static final String UNITS = "units";
    private static final String LOCATIONS = "locations";
    private static final String LOCATION_CODES = "locationCodes";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /* (non-Javadoc)
     * @see org.jasig.portlet.weather.service.IWeatherService#getSavedLocations(javax.portlet.PortletPreferences)
     */
    public List<SavedLocation> getSavedLocations(PortletPreferences prefs) {
        final String[] locationCodes = prefs.getValues(LOCATION_CODES, new String[0]);
        final String[] locations = prefs.getValues(LOCATIONS, new String[0]);
        
        String[] units = prefs.getValues(UNITS,  null);
        final String[] metrics = prefs.getValues(METRICS,  null);
        //Handling for old metrics flag approach for storing temp units
        if (metrics != null) {
            units = new String[metrics.length];
            
            for (int index = 0; index < metrics.length; index++) {
                if (Boolean.parseBoolean(metrics[index])) {
                    units[index] = TemperatureUnit.C.toString();
                }
                else {
                    units[index] = TemperatureUnit.F.toString();
                }
            }
        }
        
        final List<SavedLocation> savedLocations = new ArrayList<SavedLocation>(locationCodes.length);
        for (int locationIndex = 0; locationIndex < locationCodes.length; locationIndex++) {
            if (locationCodes[locationIndex] == null) {
                logger.warn("A null location was stored at index '{}' this should be resolved when SavedLocations are next stored for this user", locationIndex);
                continue;
            }
            
            savedLocations.add(new SavedLocation(
                    locationCodes[locationIndex],
                    locationIndex < locations.length ? locations[locationIndex] : null,
                    locationIndex < units.length ? TemperatureUnit.safeValueOf(units[locationIndex]) : TemperatureUnit.F
            ));
        }
        
        return savedLocations;
    }

    /* (non-Javadoc)
     * @see org.jasig.portlet.weather.service.IWeatherService#saveLocations(java.util.List, javax.portlet.PortletPreferences)
     */
    public void saveLocations(List<SavedLocation> savedLocations, PortletPreferences prefs) {
        final List<String> locationCodes = new ArrayList<String>(savedLocations.size());
        final List<String> locations = new ArrayList<String>(savedLocations.size());
        final List<String> metrics = new ArrayList<String>(savedLocations.size());
        
        for (final SavedLocation savedLocation : savedLocations) {
            locationCodes.add(savedLocation.code);
            locations.add(savedLocation.name);
            metrics.add(savedLocation.temperatureUnit.toString());
        }
        
        try {
            prefs.setValues(LOCATION_CODES, locationCodes.toArray(new String[locationCodes.size()]));
            prefs.setValues(LOCATIONS, locations.toArray(new String[locations.size()]));
            prefs.setValues(UNITS, metrics.toArray(new String[metrics.size()]));
            prefs.reset(METRICS); //Clear value, handling legacy preferences structure
            prefs.store();
        } 
        catch (ReadOnlyException roe) {
            throw new PermissionDeniedDataAccessException("Failed to save preferences due to one being marked read-only. The portlet preferences locationCode, locations, and metrics must not be read only.", roe);
        } 
        catch (ValidatorException ve) {
            throw new DataIntegrityViolationException("Validation of saved locations preferences failed", ve);
        } 
        catch (IOException ioe) {
            throw new RuntimeException("Failed to store saved locations preferences due to IO error", ioe);
        }
    }
    
    /* (non-Javadoc)
     * @see org.jasig.portlet.weather.service.IWeatherService#addWeatherLocation(javax.portlet.PortletPreferences, java.lang.String, java.lang.String, org.jasig.portlet.weather.TemperatureUnit)
     */
    public SavedLocation addWeatherLocation(PortletPreferences prefs, String locationCode, String location, TemperatureUnit unit) {
        final List<SavedLocation> savedLocations = new ArrayList<SavedLocation>(this.getSavedLocations(prefs));
        
        final SavedLocation newLocation = new SavedLocation(locationCode, location, unit);
        if (savedLocations.contains(newLocation)) {
            throw new DuplicateLocationException("A location already exists for code '" + locationCode + "'");
        }
        
        savedLocations.add(newLocation);
        this.saveLocations(savedLocations, prefs);
        
        return newLocation;
    }
    
    public void deleteWeatherLocation(PortletPreferences prefs, String locationCode) {
        final List<SavedLocation> savedLocations = new ArrayList<SavedLocation>(this.getSavedLocations(prefs));
        
        for (final Iterator<SavedLocation> savedLocationItr = savedLocations.iterator(); savedLocationItr.hasNext();) {
            final SavedLocation savedLocation = savedLocationItr.next();
            if (savedLocation.code.equals(locationCode)) {
                savedLocationItr.remove();
                break;
            }
        }
        
        this.saveLocations(savedLocations, prefs);
    }
}
