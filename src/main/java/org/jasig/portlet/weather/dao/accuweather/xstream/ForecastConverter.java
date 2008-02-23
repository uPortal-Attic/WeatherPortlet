/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather.xstream;

import org.jasig.portlet.weather.dao.accuweather.constants.Constants;
import org.jasig.portlet.weather.domain.Forecast;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Converter class to convert forecast XML into forecast objects.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class ForecastConverter implements Converter {

	private String timeOfDay = null; // forecast depends on time of day

	public ForecastConverter() {

	}

	public ForecastConverter(String timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return clazz.equals(Forecast.class);
	}

	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) {
		// Don't need marshalling
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Forecast forecast = new Forecast();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if (Constants.FORECAST_DAYCODE_TAG.equals(reader.getNodeName())) {
				forecast.setDay(reader.getValue().substring(0, 3));
			} else if (timeOfDay.equals(reader.getNodeName())) {
				// either in nighttime or daytime tag
				while (reader.hasMoreChildren()) {
					reader.moveDown();
					if (Constants.FORECAST_COND_TAG.equals(reader.getNodeName())) {
						forecast.setCondition(reader.getValue());
					} else if (Constants.FORECAST_IMG_ICON_TAG.equals(reader.getNodeName())) {
						forecast.setImgName(reader.getValue());
					} else if (Constants.FORECAST_HIGH_TAG.equals(reader.getNodeName())) {
						forecast.setHighTemperature(Integer.valueOf(reader.getValue()));
					} else if (Constants.FORECAST_LOW_TAG.equals(reader.getNodeName())) {
						forecast.setLowTemperature(Integer.valueOf(reader.getValue()));
					}
					reader.moveUp();
				}
			}
			reader.moveUp();
		}
		return forecast;
	}

}
