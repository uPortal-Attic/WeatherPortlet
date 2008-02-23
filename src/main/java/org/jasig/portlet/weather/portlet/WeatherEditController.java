/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;

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
	
}
