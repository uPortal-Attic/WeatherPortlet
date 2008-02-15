/*
 * Created on Feb 15, 2008
 *
 * Copyright(c) Yale University, Feb 15, 2008.  All rights reserved.
 * (See licensing and redistribution disclosures at end of this file.)
 * 
 */
package edu.yale.its.tp.portlets.YahooServices.weather;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springmodules.cache.annotations.Cacheable;
import org.w3c.dom.Element;

import edu.yale.its.tp.portlets.YahooServices.weather.xml.Astronomy;
import edu.yale.its.tp.portlets.YahooServices.weather.xml.Atmosphere;
import edu.yale.its.tp.portlets.YahooServices.weather.xml.Condition;
import edu.yale.its.tp.portlets.YahooServices.weather.xml.Forecast;
import edu.yale.its.tp.portlets.YahooServices.weather.xml.Location;
import edu.yale.its.tp.portlets.YahooServices.weather.xml.Rss;
import edu.yale.its.tp.portlets.YahooServices.weather.xml.Units;
import edu.yale.its.tp.portlets.YahooServices.weather.xml.Wind;
import edu.yale.its.tp.portlets.YahooServices.weather.xml.Rss.Channel;
import edu.yale.its.tp.portlets.YahooServices.weather.xml.Rss.Channel.Item;

public class YahooWeatherService {

	private static Log log = LogFactory.getLog(ViewWeatherController.class);

	@Cacheable(modelId = "weatherCacheModel")
	public YahooWeatherObject retrieveWeatherObject(String location,
			String tempUnit) {

		Element e = null;
		GetMethod get = null;

		try {

			log
					.debug("Retrieving weather information for location "
							+ location);

			// get the course reserves from the library
			HttpClient client = new HttpClient();
			get = new GetMethod("http://weather.yahooapis.com/forecastrss?p="
					+ location + "&u=" + tempUnit);
			int rc = client.executeMethod(get);
			if (rc != HttpStatus.SC_OK) {
				log.error("HttpStatus:" + rc);
			}

			InputStream in = get.getResponseBodyAsStream();

			JAXBContext jaxbContext = JAXBContext
					.newInstance("edu.yale.its.tp.portlets.YahooServices.weather.xml");

			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Rss weatherElement = (Rss) unmarshaller
					.unmarshal(in);
			return constructWeatherObject(weatherElement, location);


		} catch (HttpException e1) {
			e1.printStackTrace();
		} catch (JAXBException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (get != null)
				get.releaseConnection();
		}

		return null;

	}

	public YahooWeatherObject constructWeatherObject(Rss rss, String location) {
		YahooWeatherObject obj = new YahooWeatherObject();
		Channel channel = rss.getChannel();
		Astronomy astro = channel.getAstronomy();
		if (astro != null) {
			obj.setSunriseTime(astro.getSunrise());
			obj.setSunsetTime(astro.getSunset());
		}
		Atmosphere atmos = channel.getAtmosphere();
		if (atmos != null) {
			obj.setHumidity(atmos.getHumidity());
			obj.setPressure(atmos.getPressure());
			obj.setPressureDirection(atmos.getRising());
			obj.setVisibility(atmos.getVisibility());
		}
		Location loc = channel.getLocation();
		if (loc != null){
			obj.setCity(loc.getCity());
			obj.setRegion(loc.getRegion());
			obj.setCountry(loc.getCountry());
		}
		Units units = channel.getUnits();
		if (units != null) {
			obj.setDistUnits(units.getDistance());
			obj.setPressureUnits(units.getPressure());
			obj.setSpeedUnits(units.getSpeed());
			obj.setTempUnits(units.getTemperature());
		}
		Wind wind = channel.getWind();
		if (wind != null) {
			obj.setWindChill(wind.getChill());
			obj.setWindDirection(wind.getDirection());
			obj.setWindSpeed(wind.getSpeed());
		}
		Item item = channel.getItem();
		if (item != null) {
			Condition cond = item.getCondition();
			if (cond != null) {
				obj.setConditionCode(cond.getCode());
				obj.setTemp(cond.getTemp());
				obj.setConditionDescription(cond.getText());
			}
			List<Forecast> forecasts = item.getForecast();
			List<YahooWeatherForecast> newforecasts = new ArrayList<YahooWeatherForecast>();
			for (Forecast forecast : forecasts) {
				YahooWeatherForecast fore = new YahooWeatherForecast();
				fore.setCode(forecast.getCode());
				fore.setDate(forecast.getDate());
				fore.setDay(forecast.getDay());
				fore.setHigh(forecast.getHigh());
				fore.setLow(forecast.getLow());
				fore.setDescription(forecast.getText());
				newforecasts.add(fore);
			}
			obj.setForecasts(newforecasts);
			obj.setLatitude(item.getLat());
			obj.setLongitude(item.getLong());
		}
		obj.setLocation(location);
		obj.setLink(channel.getLink());
		return obj;
	}

}

/*
 * YahooWeatherService.java
 * 
 * Copyright (c) Feb 15, 2008 Yale University. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE, ARE EXPRESSLY DISCLAIMED. IN NO EVENT SHALL
 * YALE UNIVERSITY OR ITS EMPLOYEES BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED, THE COSTS OF PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED IN ADVANCE OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Redistribution and use of this software in source or binary forms, with or
 * without modification, are permitted, provided that the following conditions
 * are met.
 * 
 * 1. Any redistribution must include the above copyright notice and disclaimer
 * and this list of conditions in any related documentation and, if feasible, in
 * the redistributed software.
 * 
 * 2. Any redistribution must include the acknowledgment, "This product includes
 * software developed by Yale University," in any related documentation and, if
 * feasible, in the redistributed software.
 * 
 * 3. The names "Yale" and "Yale University" must not be used to endorse or
 * promote products derived from this software.
 */