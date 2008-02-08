/* Copyright 2008 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package org.jasig.portlet.weather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StationInfoHandler extends DefaultHandler {

	private Set<String> states =  new TreeSet<String>();
	private Map<String,List<Station> > stations = new HashMap<String,List<Station>>();
	
	boolean inStation = false;
	 
	boolean inElement = false;
	
	StringBuffer chars = new StringBuffer();
	
	HashMap<String,String> values = new HashMap<String,String>();
	
	String [] elems = {"state","station_name","station_id","html_url","xml_url"};
	List <String>elemsA = Arrays.asList(elems);
	
	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		
		// save the character values of the elements we need
		if (elemsA.contains(name)){
			values.put(name, chars.toString());
			chars = new StringBuffer();
			inElement = false;
		}
		
		// Once we reach the end of the station element we save the whole station
		// object.
		if (name.equals("station")){
			inStation = false;

			String state = values.get("state");
			states.add(state);
			
			Station s = new Station();
			s.setName(values.get("station_name"));
			s.setStationId(values.get("station_id"));
			s.setXmlUrl(values.get("xml_url"));
			s.setHtmlUrl(values.get("html_url"));
			
			List<Station> stationList = stations.get(state);
			if (stationList == null){
				stationList = new ArrayList<Station>();
				stations.put(state, stationList);
			}
			stationList.add(s);			
		}
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		if (name.equals("station")){
			inStation = true;
		}
		if (elemsA.contains(name)){
			chars = new StringBuffer();
			inElement = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (inElement){
			chars.append(ch,start,length);
		}
	}

	public Set<String> getStates() {
		return states;
	}

	public Map<String, List<Station>> getStations() {
		return stations;
	}

}
