/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.service;

import org.jasig.portlet.weather.dao.IWeatherDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This service class makes calls to IWeatherDao to retrieve weather information
 * and find locations.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherService {

	@Autowired
	private IWeatherDao weatherDao = null; // Spring managed.

}
