package org.jasig.portlet.weather.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jasig.portlet.weather.dao.accuweather.constants.Constants;

public class FindCity extends HttpServlet {

	private static final long serialVersionUID = -2776908287160550962L;
	private static final Logger logger = Logger.getLogger(FindCity.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/xml");
		String accuweatherUrl = null;
		String location = request.getParameter("location");
		try {
			accuweatherUrl = Constants.BASE_FIND_URL + URLEncoder.encode(location, Constants.URL_ENCODING);
		} catch (UnsupportedEncodingException uee) {
			//400 = bad request
			response.sendError(400);
			uee.printStackTrace();
		}
		URL urlObj = null;
		HttpURLConnection connection = null;
		try {
			urlObj = new URL(accuweatherUrl);
			connection = (HttpURLConnection)urlObj.openConnection();
			logger.debug("Retrieving location xml for " + location + " using AJAX");
			String inputLine = null;
			PrintWriter pw = response.getWriter();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((inputLine = br.readLine()) != null) {
				pw.println(inputLine);
			}
			br.close();
		} catch (MalformedURLException mue) {
			//400 = bad request
			response.sendError(400);
			mue.printStackTrace();
		} catch (IOException ioe) {
			//503 = Service Unavailable
			response.sendError(503);
			ioe.printStackTrace();
		}
	}

}
