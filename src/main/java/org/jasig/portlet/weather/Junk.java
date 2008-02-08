/* Copyright 2008 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package org.jasig.portlet.weather;

import gov.weather.www.forecasts.xml.DWMLgen.schema.DWML_xsd.ProductType;
import gov.weather.www.forecasts.xml.DWMLgen.schema.DWML_xsd.WeatherParametersType;
import gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortType;
import gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortTypeProxy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

/**
 * This class contains experimental code that could be used
 * to lookup locations by zip or by location name.
 * 
 * @author jjohnson
 *
 */
public class Junk {
	public static void main (String [] args) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException{
		
		Point myPoint = findPointByPlaceName("Lubbock");
		System.out.println(myPoint);
		myPoint = findPointByZip("79416");
		System.out.println(myPoint);
		printForcastXML(myPoint);
		
//		//String state = "TX";
//		String url = "http://www.weather.gov/data/current_obs/KLBB.xml";
//		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		Document doc = db.parse(url);
//		XPath x = XPathFactory.newInstance().newXPath();
//		Observation obs = new Observation();
//		obs.stationId = x.evaluate("current_observation/station_id",doc);
//		obs.weather = x.evaluate("current_observation/weather",doc);
//		obs.iconUrlBase = x.evaluate("current_observation/icon_url_base",doc);
//		obs.iconUrlName = x.evaluate("current_observation/icon_url_name",doc);
//		obs.tempF = x.evaluate("current_observation/temp_f",doc);
//		obs.windMph = x.evaluate("current_observation/wind_mph",doc);
//		obs.windDir = x.evaluate("current_observation/wind_dir",doc);
//		obs.heatIndexF = x.evaluate("current_observation/heat_index_f",doc);
//		obs.windchillF = x.evaluate("current_observation/windchill_f",doc);
//		obs.relativeHumidity = x.evaluate("current_observation/relative_humidity",doc);
//		
//		System.out.println("station_id " +obs.stationId);
//		System.out.println("weather "+obs.weather);
//		System.out.println("icon_url_base "+obs.iconUrlBase);
//		System.out.println("icon_url_name "+obs.iconUrlName);
//		System.out.println("temp_f "+obs.tempF);
//		System.out.println("wind_mph "+obs.windMph);
//		System.out.println("wind_dir "+obs.windDir);
//		System.out.println("heat_index_f "+obs.heatIndexF);
//		System.out.println("windchill_f "+obs.windchillF);
//		System.out.println("relative_humidity "+obs.relativeHumidity);
	}

	private static void printForcastXML(Point myPoint) throws RemoteException {
		NdfdXMLPortType n = new  NdfdXMLPortTypeProxy();
		WeatherParametersType wp = new WeatherParametersType();
		wp.setTemp(true);
		wp.setMaxt(true);
		wp.setMint(true);
		Calendar time = Calendar.getInstance();
		Calendar time2 = Calendar.getInstance();
		time.setTime(new Date());
		time2.setTime(new Date());
		time2.add(Calendar.HOUR, 48);
//		ProductType pt = ProductType.fromString("time-series");
		ProductType pt = ProductType.fromString("glance");
//		String s = n.NDFDgen(new BigDecimal("33.59"), new BigDecimal("-101.89"),
		String s = n.NDFDgen(myPoint.latitude, myPoint.longitude,
				pt, 
				null, 
				null, 
				wp );
		System.out.println(s);
	}

	private static Point findPointByPlaceName(String name)
			throws FileNotFoundException, IOException {
		Point myPoint = new Point();

		BufferedReader is = new BufferedReader(new InputStreamReader(
				new FileInputStream("places2k.txt")));
		String line;
		
		while ((line = is.readLine()) != null){
			int fipsCode = Integer.parseInt(line.substring(4,9));
			String city = line.substring(9,73).trim();
			BigDecimal latitude = new BigDecimal(line.substring(143,153).trim());
			BigDecimal longitude = new BigDecimal(line.substring(153,164).trim());
			if (city.startsWith(name)){
				System.out.println(fipsCode +" " +city+" "+latitude+" "+longitude);
				myPoint.latitude = latitude;
				myPoint.longitude = longitude;
			}
		}
		return myPoint;
	}
	

	private static Point findPointByZip(String zip)
			throws FileNotFoundException, IOException {
		Point myPoint = new Point();

		BufferedReader is = new BufferedReader(new InputStreamReader(
				new FileInputStream("zcta5.txt")));
		String line;
		
		while ((line = is.readLine()) != null){
			String zipCode = line.substring(2,7).trim();
			BigDecimal latitude = new BigDecimal(line.substring(136,146).trim());
			BigDecimal longitude = new BigDecimal(line.substring(146,157).trim());
			if (zip.equals(zipCode)){
				System.out.println(zipCode +" "+latitude+" "+longitude);
				myPoint.latitude = latitude;
				myPoint.longitude = longitude;
			}
		}
		return myPoint;
	}
}
