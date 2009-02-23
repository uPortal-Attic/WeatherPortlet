/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather.xstream;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jasig.portlet.weather.domain.Current;
import org.jasig.portlet.weather.domain.Forecast;
import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;
import org.jasig.portlet.weather.dao.accuweather.constants.Constants;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Main converter class for building the weather object. This class calls other
 * converter classes ( {@link UnitsConverter}, {@link CurrentConverter},
 * {@link LocationConverter}, {@link ForecastsConverter},
 * {@link ForecastConverter} ) through the convertAnother method.
 * 
 * @see {@link UnmarshallingContext#convertAnother(Object, Class)}.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherConverter implements Converter {
	
	private static final Logger logger = Logger.getLogger(WeatherConverter.class);

	private Date observationTime = null;
	private Date sunsetTime = null;
	private DateFormat dateFormatter = new SimpleDateFormat("h:mm a");
	private String locationCode = null;

	public WeatherConverter(String locationCode) {
		this.locationCode = locationCode;
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
		return clazz.equals(Weather.class);
	}

	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) {
		// Don't need marshalling
	}

	@SuppressWarnings("unchecked")
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Weather weather = new Weather();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if (Constants.UNITS_TAG.equals(reader.getNodeName())) {
				//put the weather object in context so it can be retrieved in UnitsConverter
				context.put("weather", weather);
				weather = (Weather)context.convertAnother(weather, Weather.class, new UnitsConverter());
			} else if (Constants.LOCAL_TAG.equals(reader.getNodeName())) {
				//call the Location converter to get our location object
				Location location = (Location) context.convertAnother(weather,Location.class, new LocationConverter(locationCode));
				weather.setLocation(location);
			} else if (Constants.CURRENT_TAG.equals(reader.getNodeName())) {
				Current current = (Current)context.convertAnother(weather, Current.class, new CurrentConverter());
				weather.setCurrentWeather(current);
				//get items out of context that were set by CurrentConverter
				observationTime = (Date)context.get("observationTime");
				weather.setMoreInformationLink((String)context.get("moreInformationLink"));
			} else if (Constants.PLANETS_TAG.equals(reader.getNodeName())) {
				reader.moveDown();
				String sunTime = reader.getAttribute(Constants.SUNSET_ATTR);
				try {
					sunsetTime = dateFormatter.parse(sunTime.trim());
				} catch (ParseException pe) {
					sunsetTime = null;
					logger.warn("Unable to parse sunset time",pe);
				}
				reader.moveUp();
			} else if (Constants.FORECAST_TAG.equals(reader.getNodeName())) {
				// Assume daytime if we don't know the sunset time
				String timeOfDay = Constants.DAYTIME_TAG;
				if (sunsetTime != null && sunsetTime.before(observationTime)){
					timeOfDay = Constants.NIGHTTIME_TAG;
				}
				Collection<Forecast> forecasts = (Collection<Forecast>)context.convertAnother(
						weather, Forecast.class, new ForecastsConverter(timeOfDay));
				weather.setForecast(forecasts);
			}
			reader.moveUp();
		}
		return weather;
	}

}
