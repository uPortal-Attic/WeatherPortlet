/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather.dom4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jasig.portlet.weather.domain.Location;

/**
 * This is the worker class that finds locations. It is called from
 * WeatherDaoImpl
 * 
 * @see WeatherDaoImpl
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class LocationUtil {

	private Logger logger = Logger.getLogger(LocationUtil.class);
	private Element root = null;
	
	private static String BASE_URL = "http://uport.accu-weather.com/widget/uport/city-find.asp?location=";

	public LocationUtil(String location) {
		logger.debug("Retrieving location xml for " + location + " using DOM4J");
		String accuweatherUrl = BASE_URL + location;
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(accuweatherUrl);
		} catch (DocumentException de) {
			de.printStackTrace();
			throw new RuntimeException("Unable to retrieve xml", de);
		}

		// get top level element
		root = document.getRootElement();
	}

	@SuppressWarnings("unchecked")
	public Collection<Location> getLocations() {
		Collection<Location> locations = new ArrayList<Location>();
		Element citylist = root.element("citylist");
		List<Element> locationElements = citylist.elements("location");
		if (locationElements == null || locationElements.size() == 0) {
			logger.debug("Location not found");
			return null;
		}
		for (Element ele : locationElements) {
			Location location = new Location();
			Attribute city = ele.attribute("city");
			Attribute stateOrCountry = ele.attribute("state");
			Attribute locationCode = ele.attribute("location");
			location.setCity(city.getValue());
			location.setStateOrCountry(stateOrCountry.getValue());
			location.setLocationCode(locationCode.getValue());
			locations.add(location);
		}
		return locations;
	}

}
