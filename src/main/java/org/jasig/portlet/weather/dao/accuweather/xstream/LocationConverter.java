/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather.xstream;

import org.jasig.portlet.weather.dao.accuweather.constants.Constants;
import org.jasig.portlet.weather.domain.Location;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Converter class to convert location XML into the location object.
 * 
 * @author Dustin Schultz
 * @version $Id$
 * 
 */
public class LocationConverter implements Converter {

	private String locationCode = null;

	public LocationConverter() {

	}

	public LocationConverter(String locationCode) {
		this.locationCode = locationCode;
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return clazz.equals(Location.class);
	}

	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) {
		// Don't need marshalling
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Location location = new Location();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if (Constants.CITY_TAG.equals(reader.getNodeName())) {
				location.setCity(reader.getValue());
			} else if (Constants.STATE_TAG.equals(reader.getNodeName())) {
				location.setStateOrCountry(reader.getValue());
			} else if (Constants.LAT_TAG.equals(reader.getNodeName())) {
				location.setLatitude(Double.valueOf(reader.getValue()));
			} else if (Constants.LON_TAG.equals(reader.getNodeName())) {
				location.setLongitude(Double.valueOf(reader.getValue()));
			}
			reader.moveUp();
		}
		location.setLocationCode(locationCode);
		return location;
	}

}
