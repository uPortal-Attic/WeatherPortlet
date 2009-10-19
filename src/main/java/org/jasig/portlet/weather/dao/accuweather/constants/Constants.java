/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather.constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Constants interface to hold all constant information such as tag names,
 * attribute names, or image sizes/paths.
 * 
 * @author schultzd
 * @version $Id$
 */

public interface Constants {

	// url constants
	public static final String BASE_GET_URL = "http://uport.accu-weather.com/widget/uport/weather-data.asp?location=";
	public static final String BASE_FIND_URL = "http://uport.accu-weather.com/widget/uport/city-find.asp?location=";
	public static final String URL_ENCODING = "UTF-8";
	
	//formatting constants
	@SuppressWarnings("serial")
	public static final Collection<String> dateFormatterPatterns = Collections.unmodifiableList(new ArrayList<String>() {
		{
			add(new String("h:mm a")); //12 hour
			add(new String("kk:mm")); //24 hour
		}
	});
	
	// xml root tags
	public static final String UNITS_TAG = "units";
	public static final String LOCAL_TAG = "local";
	public static final String CURRENT_TAG = "currentconditions";
	public static final String PLANETS_TAG = "planets";
	public static final String FORECAST_TAG = "forecast";

	// xml location tags
	public static final String CITY_TAG = "city";
	public static final String STATE_TAG = "state";
	public static final String LAT_TAG = "lat";
	public static final String LON_TAG = "lon";

	// xml units of measurement tags
	public static final String TEMP_TAG = "temp";
	public static final String SPEED_TAG = "speed";
	public static final String PRES_TAG = "pres";

	// xml current conditions tags/attributes
	public static final String URL_TAG = "url";
	public static final String OBS_TIME_TAG = "observationtime";
	public static final String CURR_PRESSURE_TAG = "pressure";
	public static final String CURR_TEMP_TAG = "temperature";
	public static final String CURR_HUMIDITY_TAG = "humidity";
	public static final String CURR_COND_TAG = "weathertext";
	public static final String CURR_ICON_TAG = "weathericon";
	public static final String CURR_WIND_SPEED_TAG = "windspeed";
	public static final String CURR_WIND_DIR_TAG = "winddirection";

	// constant unknown values
	public static final String PRESSURE_UNKNOWN = "-999";

	// xml planet tags/attributes
	public static final String SUN_TAG = "sun";
	public static final String SUNSET_ATTR = "set";

	// xml forecast tags
	public static final String FORECAST_DAY_TAG = "day";
	public static final String FORECAST_DAYCODE_TAG = "daycode";
	public static final String FORECAST_COND_TAG = "txtshort";
	public static final String FORECAST_IMG_ICON_TAG = "weathericon";
	public static final String FORECAST_HIGH_TAG = "hightemperature";
	public static final String FORECAST_LOW_TAG = "lowtemperature";
	public static final String DAYTIME_TAG = "daytime";
	public static final String NIGHTTIME_TAG = "nighttime";

	// xml location find tags/attributes
	public static final String CITYLIST_TAG = "citylist";
	public static final String CITY_ATTR = "city";
	public static final String STATE_ATTR = "state";
	public static final String LOCATION_ATTR = "location";

}
