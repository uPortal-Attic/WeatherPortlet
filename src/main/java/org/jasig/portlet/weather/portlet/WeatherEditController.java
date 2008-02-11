/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.portlet;

import org.jasig.portlet.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.portlet.mvc.SimpleFormController;

/**
 * This class handles all interactions from the edit form.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherEditController extends SimpleFormController {

	@Autowired
	private WeatherService weatherService = null; // Spring managed

}
