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

import org.apache.log4j.Level;
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
				for (String pattern : Constants.dateFormatterPatterns) {
					//if we already successfully converted the sunsetTime, don't try again
					if (sunsetTime != null) { continue; }
					DateFormat formatter = new SimpleDateFormat(pattern);
					try {
						sunsetTime = formatter.parse(sunTime.trim());
					} catch (ParseException pe) {
						if (logger.isEnabledFor(Level.WARN)) {
							logger.warn("Unable to parse sunset time " + sunTime);
						}
					}
				}
				reader.moveUp();
			} else if (Constants.FORECAST_TAG.equals(reader.getNodeName())) {
				// Default daytime in case we don't know the sunset time
				String timeOfDay = Constants.DAYTIME_TAG;
				if (sunsetTime != null && observationTime != null) {
					timeOfDay = (sunsetTime.before(observationTime) ? Constants.NIGHTTIME_TAG : Constants.DAYTIME_TAG);
				} else {
					if (logger.isEnabledFor(Level.WARN)) {
						logger.warn("Unable to determine time of day for forecast rendering, defaulting to day time");
					}
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
