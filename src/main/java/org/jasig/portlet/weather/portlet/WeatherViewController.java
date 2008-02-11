/* Copyright 2008 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package org.jasig.portlet.weather.portlet;

import org.jasig.portlet.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.portlet.mvc.AbstractController;

/**
 * This class handles retrieves information and displays the view page.
 * 
 * @author Dustin Schultz
 */
public class WeatherViewController extends AbstractController {
	
	@Autowired
	private WeatherService weatherService = null; //Spring managed

}
