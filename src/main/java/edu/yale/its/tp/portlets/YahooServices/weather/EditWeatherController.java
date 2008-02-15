/*
 * Created on Feb 15, 2008
 *
 * Copyright(c) Yale University, Feb 15, 2008.  All rights reserved.
 * (See licensing and redistribution disclosures at end of this file.)
 * 
 */
package edu.yale.its.tp.portlets.YahooServices.weather;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.SimpleFormController;

public class EditWeatherController extends SimpleFormController {

	private static Log log = LogFactory.getLog(EditWeatherController.class);

	public EditWeatherController() {
		setCommandClass(YahooWeatherPreferences.class);
		setCommandName("prefs");
	}

	protected Object formBackingObject(PortletRequest request) throws Exception {
		YahooWeatherPreferences prefs = new YahooWeatherPreferences();
		PortletPreferences preferences = request.getPreferences();
		String[] location = preferences.getValues("location", new String[]{"06518"});
		String tempUnit = preferences.getValue("tempUnit", "f");
		prefs.setTempUnit(tempUnit);
		prefs.setLocation(location);
		return prefs;
	}

	protected void processFormSubmission(ActionRequest request,
			ActionResponse response, Object command, BindException errors) {
		YahooWeatherPreferences weatherPrefs = (YahooWeatherPreferences) command;
		PortletPreferences preferences = request.getPreferences();
		try {
			preferences.setValues("location", weatherPrefs.getLocation());
			preferences.setValue("tempUnit", weatherPrefs.getTempUnit());
			preferences.store();
			log.debug("Saved new preferred weather location: "
					+ weatherPrefs.getLocation());
		} catch (Exception ex) {
			log.error("Failed to store user's prefered weather locations", ex);
		}
		try {
			response.setPortletMode(PortletMode.VIEW);
		} catch (PortletModeException e) {
			log.error("Error setting weather portlet back to view mode", e);
		}
	}

	protected ModelAndView renderFormSubmission(RenderRequest request,
			RenderResponse response, Object command, BindException errors)
			throws Exception {
		log.debug("Rendering form");
		return showForm(request, response, errors);
	}
}

/*
 * EditWeatherController.java
 * 
 * Copyright (c) Feb 15, 2008 Yale University. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE, ARE EXPRESSLY DISCLAIMED. IN NO EVENT SHALL
 * YALE UNIVERSITY OR ITS EMPLOYEES BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED, THE COSTS OF PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED IN ADVANCE OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Redistribution and use of this software in source or binary forms, with or
 * without modification, are permitted, provided that the following conditions
 * are met.
 * 
 * 1. Any redistribution must include the above copyright notice and disclaimer
 * and this list of conditions in any related documentation and, if feasible, in
 * the redistributed software.
 * 
 * 2. Any redistribution must include the acknowledgment, "This product includes
 * software developed by Yale University," in any related documentation and, if
 * feasible, in the redistributed software.
 * 
 * 3. The names "Yale" and "Yale University" must not be used to endorse or
 * promote products derived from this software.
 */