/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather.dom4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jasig.portlet.weather.dao.accuweather.constants.Constants;
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

	public LocationUtil(HttpClient httpClient, String location) {
		String accuweatherUrl = null;
		try {
			accuweatherUrl = Constants.BASE_FIND_URL
					+ URLEncoder.encode(location, Constants.URL_ENCODING);
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
			throw new RuntimeException("Unable to encode url with "
					+ Constants.URL_ENCODING + " encoding");
		}
		SAXReader reader = new SAXReader();
		HttpMethod getMethod = new GetMethod(accuweatherUrl);
		InputStream inputStream = null;
		Document document = null;
		
		try {
			// Execute the method.
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + getMethod.getStatusLine());
				throw new RuntimeException("Unable to retrieve locations from feed, invalid status code");
			}
			// Read the response body
			inputStream = getMethod.getResponseBodyAsStream();
			logger.debug("Retrieving location xml for " + location + " using DOM4J");
			document = reader.read(inputStream);
		} catch (HttpException e) {
			logger.error("Fatal protocol violation", e);
			throw new RuntimeException("Unable to retrieve locations from feed, http protocol exception");
		} catch (IOException e) {
			logger.error("Fatal transport error", e);
			throw new RuntimeException("Unable to retrieve locations from feed, IO exception");
		} catch (DocumentException e) {
			logger.error("Document Exception while retrieving locations, most likely there is a problem with parsing the document");
			throw new RuntimeException("Unable to retrieve xml", e);
		} finally {
			//try to close the inputstream
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.warn("Unable to close input stream while retrieving locations");
			}
			//release the connection
			getMethod.releaseConnection();
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
