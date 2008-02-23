/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather.xstream;

import java.util.ArrayList;
import java.util.Collection;

import org.jasig.portlet.weather.dao.accuweather.constants.Constants;
import org.jasig.portlet.weather.domain.Forecast;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Converter class to convert forecast objects into a forecast collection.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class ForecastsConverter implements Converter {
	
	String timeOfDay = null; // forecast depends on time of day

	public ForecastsConverter(String timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) {
		// Don't need marshalling

	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Collection<Forecast> forecasts = new ArrayList<Forecast>();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if (Constants.FORECAST_DAY_TAG.equals(reader.getNodeName())) {
				Forecast forecast = (Forecast) context.convertAnother(forecasts, Forecast.class, new ForecastConverter(timeOfDay));
				forecasts.add(forecast);
			}
			reader.moveUp();
		}
		return forecasts;
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return true;
	}

}
