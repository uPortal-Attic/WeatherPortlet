/* Copyright 2008 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package org.jasig.portlet.weather;

import java.util.List;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WeatherObservationService {

	/*
	 * TODO: need to do proper lazy loading
	 * 
	 * TODO: add Hi/Low for day
	 * 
	 */
	private static Set<String> states =  new TreeSet<String>();
	private static Map<String,List<Station> > stations = new HashMap<String,List<Station>>();

	private static void parseStations(){
		String url = "http://www.weather.gov/data/current_obs/index.xml";
		
		// Parse using the StationInfoHandler. Parsing with DOM was really slow.

		SAXParserFactory factory = SAXParserFactory.newInstance();

		// Parse the input
		StationInfoHandler handler= new StationInfoHandler();
		SAXParser saxParser;
		try {

			saxParser = factory.newSAXParser();
			saxParser.parse( url, (DefaultHandler)handler );

			states = handler.getStates();
			stations = handler.getStations();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Collection<List<Station>> stationsValue = stations.values();
		for (List <Station> stationList: stationsValue){
			Collections.sort(stationList,new StationComparator());				
		}
	}

	public static Set<String> getStates(){
		if (states.size() == 0){
			parseStations();
		}
		return Collections.unmodifiableSet(states);		
	}

	/**
	 * Return a list of stations for the given two letter state abbreviation.
	 * @param state
	 * @return
	 */
	public static List<Station> getStations(String state){
		if (stations.size() == 0){
			parseStations();
		}
		return Collections.unmodifiableList(stations.get(state));
	}

	public static Observation getObservation(String url){
		DocumentBuilder db;
		Observation obs;

		/* 
		 * TODO: Add caching to prevent bombarding the service with requests.
		 * TODO: Avoid failing if there is a problem getting the weather. Retry.
		 */
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc;
			doc = db.parse(url);
			XPath x = XPathFactory.newInstance().newXPath();
			obs = new Observation();
			obs.location = x.evaluate("current_observation/location",doc);
			obs.stationId = x.evaluate("current_observation/station_id",doc);
			obs.weather = x.evaluate("current_observation/weather",doc);
			obs.iconUrlBase = x.evaluate("current_observation/icon_url_base",doc);
			obs.iconUrlName = x.evaluate("current_observation/icon_url_name",doc);
			obs.tempF = x.evaluate("current_observation/temp_f",doc);
			obs.windMph = x.evaluate("current_observation/wind_mph",doc);
			obs.windDir = x.evaluate("current_observation/wind_dir",doc);
			obs.heatIndexF = x.evaluate("current_observation/heat_index_f",doc);
			if (obs.heatIndexF.equals("NA")){
				obs.heatIndexF=null;
			}
			obs.windchillF = x.evaluate("current_observation/windchill_f",doc);
			if (obs.windchillF.equals("NA")){
				obs.windchillF=null;
			}
			obs.relativeHumidity = x.evaluate("current_observation/relative_humidity",doc);
		} catch (SAXException e) {
			throw new RuntimeException("url: "+url,e);
		} catch (IOException e) {
			throw new RuntimeException("url: "+url,e);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (XPathExpressionException e) {
			throw new RuntimeException("url: "+url,e);
		}
		return obs;
	}

}
