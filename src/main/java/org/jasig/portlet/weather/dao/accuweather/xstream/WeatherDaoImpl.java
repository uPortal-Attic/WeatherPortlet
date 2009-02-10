/* Copyright 2008 The JA-SIG Collaborative. All rights reserved.
 *  See license distributed with this file and
 *  available online at http://www.uportal.org/license.html
 */

package org.jasig.portlet.weather.dao.accuweather.xstream;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.Collection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.jasig.portlet.weather.dao.IWeatherDao;
import org.jasig.portlet.weather.dao.accuweather.constants.Constants;
import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.domain.Weather;

import com.thoughtworks.xstream.XStream;

/**
 * AccuWeather.com weather data implementation using Xstream to parse XML.
 * 
 * @author Dustin Schultz
 * @version $Id$
 */
public class WeatherDaoImpl implements IWeatherDao {
	
	private static final Logger logger = Logger.getLogger(WeatherDaoImpl.class);
	private final HttpClient httpClient = new HttpClient();

	//Default timeout of 5 seconds
	private int connectionTimeout = 5000;
	
	//Default timeout of 5 seconds
	private int readTimeout = 5000;
	
	//Default retry of 5 times
	private int timesToRetry = 5;
	
	public WeatherDaoImpl() {
		init();
	}
	
	public void init() {
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(readTimeout);
		
		HttpMethodRetryHandler retryhandler = new HttpMethodRetryHandler() {
		    public boolean retryMethod(
		        final HttpMethod method, 
		        final IOException exception, 
		        int executionCount) {
		        if (executionCount >= timesToRetry) {
		            // Do not retry if over max retry count
		            return false;
		        }
		        if (exception instanceof NoHttpResponseException) {
		            // Retry if the server dropped connection on us
		            return true;
		        }
		        if (exception instanceof SocketException) {
		            // Retry if the server reset connection on us
		            return true;
		        }
		        if (exception instanceof SocketTimeoutException) {
		        	// Retry if the read timed out
		        	return true;
		        }
		        if (!method.isRequestSent()) {
		            // Retry if the request has not been sent fully or
		            // if it's OK to retry methods that have been sent
		            return true;
		        }
		        // otherwise do not retry
		        return false;
		    }
		};
		
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, retryhandler);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Location> find(String location) {
		String accuweatherUrl = null;
		try {
			accuweatherUrl = Constants.BASE_FIND_URL
					+ URLEncoder.encode(location, Constants.URL_ENCODING);
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
			throw new RuntimeException("Unable to encode url with "
					+ Constants.URL_ENCODING + " encoding");
		}
		HttpMethod getMethod = new GetMethod(accuweatherUrl);
		InputStream inputStream = null;
		XStream xstream = new XStream();
		xstream.alias("adc_database", Collection.class);
		xstream.registerConverter(new FinderConverter());
		Collection<Location> locations = null;
		try {
			// Execute the method.
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + getMethod.getStatusLine());
				throw new RuntimeException("Unable to retrieve locations from feed, invalid status code");
			}
			// Read the response body
			inputStream = getMethod.getResponseBodyAsStream();
			if (logger.isDebugEnabled()) {
				logger.debug("Retrieving location xml for " + location + " using Xstream");
			}
			locations = (Collection<Location>) xstream.fromXML(inputStream);
		} catch (HttpException e) {
			logger.error("Fatal protocol violation", e);
			throw new RuntimeException("Unable to retrieve locations from feed, http protocol exception");
		} catch (IOException e) {
			logger.error("Fatal transport error", e);
			throw new RuntimeException("Unable to retrieve locations from feed, IO exception");
		} finally {
			//try to close the inputstream
			try {
				if (inputStream != null){
					inputStream.close();
				}
			} catch (IOException e) {
				logger.warn("Unable to close input stream while retrieving locations");
			}
			//release the connection
			getMethod.releaseConnection();
		}
		return (locations != null && locations.size() > 0) ? locations : null;
	}

	public Weather getWeather(String locationCode, Boolean metric) {
		String accuweatherUrl = null;
		try {
			accuweatherUrl = Constants.BASE_GET_URL + URLEncoder.encode(locationCode, Constants.URL_ENCODING)
					+ "&metric=" + ((metric) ? "1" : "0");
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
			throw new RuntimeException("Unable to encode url with "
					+ Constants.URL_ENCODING + " encoding");
		}
		HttpMethod getMethod = new GetMethod(accuweatherUrl);
		InputStream inputStream = null;
		XStream xstream = new XStream();
		xstream.registerConverter(new WeatherConverter(locationCode));
		xstream.alias("adc_database", Weather.class);
		Weather weather = null;
		try {
			// Execute the method.
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + getMethod.getStatusLine());
				throw new RuntimeException("Unable to retrieve weather from feed, invalid status code");
			}
			// Read the response body
			inputStream = getMethod.getResponseBodyAsStream();
			if (logger.isDebugEnabled()) {
				logger.debug("Retrieving weather xml using Xstream for location " + locationCode + " with metric " + metric);
			}
			weather = (Weather) xstream.fromXML(inputStream);
		} catch (HttpException e) {
			logger.error("Fatal protocol violation", e);
			throw new RuntimeException("Unable to retrieve weather from feed, http protocol exception");
		} catch (IOException e) {
			logger.error("Fatal transport error", e);
			throw new RuntimeException("Unable to retrieve weather from feed, IO exception");
		} finally {
			//try to close the inputstream
			try {
				if (inputStream != null){
					inputStream.close();
				}
			} catch (IOException e) {
				logger.warn("Unable to close input stream while retrieving weather");
			}
			//release the connection
			getMethod.releaseConnection();
		}
		return weather;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public void setTimesToRetry(int timesToRetry) {
		this.timesToRetry = timesToRetry;
	}
	
}
