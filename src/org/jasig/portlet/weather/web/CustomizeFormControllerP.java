/* Copyright 2008 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package org.jasig.portlet.weather.web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.weather.Station;
import org.jasig.portlet.weather.WeatherObservationService;
import org.jasig.portlet.weather.bus.ConfigurationManager;
import org.jasig.portlet.weather.bus.Config;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.portlet.mvc.AbstractWizardFormController;
import org.springframework.web.portlet.ModelAndView;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;


public class CustomizeFormControllerP  extends AbstractWizardFormController {
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private ConfigurationManager configurationManager;

	public void setConfigurationManager(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}
	
	@Override
	protected Map<String, List<?>> referenceData(PortletRequest request, Object command,
			Errors errors, int page) throws Exception {
		Map<String, List<?>> data = null;
		if (page == 0){
			data = new HashMap<String,List<?>>();
			logger.error("getting states");
			List<String> states = new ArrayList<String>(WeatherObservationService.getStates());
			data.put("listStates",states);
		}
		
		if (page==1){
			data = new HashMap<String, List<?>>();
			Config c = (Config)command;
			String state = c.getState();
			logger.error("getting stations for state "+state);
			if (state == null){
				state = "TX";
			}
			List<Station> stations = WeatherObservationService.getStations(state);
			data.put("listStations",stations);
		}
		return data;
	}
	
	protected void processFinish(ActionRequest request,
			ActionResponse response,
			Object command,
			BindException errors)
	throws Exception {
		logger.info("***************saving*********************");
		Config c = (Config)command;
		logger.info("state: "+c.getState()+" station:"+c.getStationId());
		logger.info("************************************");
		configurationManager.setConfig((Config)command);
		
		PortletPreferences prefs = request.getPreferences();
		prefs.setValue("stationId", c.getStationId());
		prefs.setValue("state", c.getState());
		prefs.store();
		response.setRenderParameter("action", "");
		response.setWindowState(WindowState.NORMAL);
	}
	
	protected void processCancel(ActionRequest request,
            ActionResponse response,
            Object command,
            BindException errors)
	  throws Exception {
		response.setRenderParameter("action", "");
	}
	
	protected ModelAndView renderInvalidSubmit(RenderRequest request, RenderResponse response) throws Exception {
		return null;
	}

	protected void handleInvalidSubmit(ActionRequest request, ActionResponse response) throws Exception {
		response.setRenderParameter("action", "");
	}
}
