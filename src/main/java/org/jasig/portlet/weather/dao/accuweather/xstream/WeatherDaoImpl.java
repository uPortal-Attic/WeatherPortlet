/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather.xstream;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.jasig.portlet.weather.dao.IWeatherDao;
import org.jasig.portlet.weather.dao.accuweather.constants.Constants;
import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;

import com.thoughtworks.xstream.XStream;

/**
 * AccuWeather.com weather data implementation using Xstream to parse XML.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherDaoImpl implements IWeatherDao {
	
	private static final Logger logger = Logger.getLogger(WeatherDaoImpl.class);
		
	@SuppressWarnings("unchecked")
	public Collection<Location> find(String location) {
		String accuweatherUrl = Constants.BASE_FIND_URL + location;
		URL urlObj = null;
		URLConnection connection = null;
		XStream xstream = new XStream();
		xstream.alias("adc_database", Collection.class);
		xstream.registerConverter(new FinderConverter());
		Collection<Location> locations = null;
		try {
			urlObj = new URL(accuweatherUrl);
			connection = urlObj.openConnection();
			logger.debug("Retrieving location xml for " + location + " using Xstream");
			locations = (Collection<Location>)xstream.fromXML(connection.getInputStream());
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
			throw new RuntimeException("Unable to build url");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new RuntimeException("Unable to get connection or create locations collection from xml");
		}
		return (locations != null && locations.size() > 0) ? locations : null;
	}

	public Weather getWeather(String locationCode, Boolean metric) {
		String accuweatherUrl = Constants.BASE_GET_URL + locationCode + "&metric=" + ((metric) ? "1" : "0");
		URL urlObj = null;
		URLConnection connection = null;
		XStream xstream = new XStream();
		xstream.registerConverter(new WeatherConverter(locationCode));
		xstream.alias("adc_database", Weather.class);
		Weather weather = null;
		try {
			urlObj = new URL(accuweatherUrl);
			connection = urlObj.openConnection();
			logger.debug("Retrieving weather xml using Xstream for location " + locationCode + " with metric " + metric);
			weather = (Weather)xstream.fromXML(connection.getInputStream());
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
			throw new RuntimeException("Unable to build url");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new RuntimeException("Unable to get connection or create weather object from XML");
		}
		return weather;
	}

}
