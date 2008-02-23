/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather.xstream;

import java.util.ArrayList;
import java.util.Collection;

import org.jasig.portlet.weather.dao.accuweather.constants.Constants;
import org.jasig.portlet.weather.domain.Location;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Main converter class for finding a location and building the collection of
 * locations. This class calls the {@link ResultConverter} through the convertAnother
 * method.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class FinderConverter implements Converter {

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return clazz.equals(Collection.class);
	}

	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) {
		// Don't need marshalling
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Collection<Location> locations = new ArrayList<Location>();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if (Constants.CITYLIST_TAG.equals(reader.getNodeName())) {
				//read through each location tag and add it to the collection
				while (reader.hasMoreChildren()) {
					reader.moveDown();
					Location location = (Location) context.convertAnother(locations, Location.class, new ResultConverter());
					locations.add(location);
					reader.moveUp();
				}
			}
			reader.moveUp();
		}
		return locations;
	}
}
