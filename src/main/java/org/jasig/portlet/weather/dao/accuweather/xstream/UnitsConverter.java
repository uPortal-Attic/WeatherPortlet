/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather.xstream;

import org.jasig.portlet.weather.dao.accuweather.constants.Constants;
import org.jasig.portlet.weather.domain.Weather;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Converter class to convert unit XML into weather object.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class UnitsConverter implements Converter {
	
	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return true;
	}

	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) {
		// Don't need marshalling
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		//we need the parent object
		Weather weather = (Weather)context.get("weather");
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if (Constants.TEMP_TAG.equals(reader.getNodeName())) {
				weather.setTemperatureUnit(reader.getValue());
			} else if (Constants.SPEED_TAG.equals(reader.getNodeName())) {
				weather.setWindUnit(reader.getValue());
			} else if (Constants.PRES_TAG.equals(reader.getNodeName())) {
				weather.setPressureUnit(reader.getValue());
			}
			reader.moveUp();
		}
		return weather;
	}

}
