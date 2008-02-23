/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather.xstream;

import org.apache.log4j.Logger;
import org.jasig.portlet.weather.dao.accuweather.constants.Constants;
import org.jasig.portlet.weather.domain.Location;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Converter class to convert location results from searching for a city.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class ResultConverter implements Converter {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(ResultConverter.class);

	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) {
		// Don't need marshalling
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Location location = new Location();
		location.setCity(reader.getAttribute(Constants.CITY_ATTR));
		location.setStateOrCountry(reader.getAttribute(Constants.STATE_ATTR));
		location.setLocationCode(reader.getAttribute(Constants.LOCATION_ATTR));
		return location;
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return clazz.equals(Location.class);
	}

}
