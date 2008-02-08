
package org.jasig.portlet.weather;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jasig.portlet.weather.Station;
import org.jasig.portlet.weather.StationInfoHandler;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

import junit.framework.TestCase;

public class ParserTest extends TestCase{
	
	public void parserTest(){
		
		String url = "http://www.weather.gov/data/current_obs/index.xml";
		
        SAXParserFactory factory = SAXParserFactory.newInstance();
        
            // Parse the input
        	StationInfoHandler handler= new StationInfoHandler();
            SAXParser saxParser;
			try {
				
				saxParser = factory.newSAXParser();
	            saxParser.parse( url, (DefaultHandler)handler );
	            
		        Set<String> states = handler.getStates();
		        
		        // spot-check one station
		        Map<String, List<Station>> stations = handler.getStations();
		        List<Station> txStations = stations.get("NC");
		        boolean foundStation = false;
		        for (Station station: txStations){
		        	if (station.getStationId().equals("KAVL")){
		        		foundStation = true;
		        	}
		        }
		        assertTrue(states.size() > 50);
		        assertTrue(states.size() < 60);
		        assertTrue(foundStation);
	            
			} catch (ParserConfigurationException e) {
				throw new RuntimeException(e);
			} catch (SAXException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
	}
}
