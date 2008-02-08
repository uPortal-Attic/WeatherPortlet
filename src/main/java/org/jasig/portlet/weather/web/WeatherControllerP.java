/* Copyright 2008 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package org.jasig.portlet.weather.web;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.weather.Observation;
import org.jasig.portlet.weather.Station;
import org.jasig.portlet.weather.WeatherObservationService;
import org.jasig.portlet.weather.bus.ConfigurationManager;
import org.springframework.web.portlet.mvc.AbstractController;
import org.springframework.web.portlet.ModelAndView;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public class WeatherControllerP extends AbstractController {
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private ConfigurationManager configurationManager;
    
	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public void setConfigurationManager(ConfigurationManager configurationManger) {
		this.configurationManager = configurationManger;
	}	
	
	@Override
	protected ModelAndView handleRenderRequestInternal(
			RenderRequest request, RenderResponse response) throws Exception {
        
		PortletPreferences prefs = request.getPreferences();
		
		String stationId = prefs.getValue("stationId", "KLBB");
		String state = prefs.getValue("state", "TX");

        // lookup the state in the configuration
        List<Station> stations = WeatherObservationService.getStations(state);
        
        Map<String, Object> myModel = new HashMap<String, Object>();
        // find the station object and place it in the model
        String url = null;
        for (Station station: stations){
        	if (station.getStationId().equals(stationId)){
                myModel.put("station", station);
                url = station.getXmlUrl();
                break;
        	}
        }
		
    	Observation obs = WeatherObservationService.getObservation(url);
		
        myModel.put("obs", obs);

        return new ModelAndView("weather", "model", myModel);
	}
}
