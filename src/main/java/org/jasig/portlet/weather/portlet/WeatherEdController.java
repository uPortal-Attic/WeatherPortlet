package org.jasig.portlet.weather.portlet;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.apache.log4j.Logger;
import org.jasig.portlet.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.util.PortletUtils;

@Controller
@RequestMapping("EDIT")
public class WeatherEdController {
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Autowired
	private IWeatherService weatherService;

	@RequestMapping //default action=search
	public String showSearchLocationForm(Model model) {
		if (!model.containsAttribute("weatherFormModel")) {
			model.addAttribute("weatherFormModel", new WeatherFormModel());
		}
		return "editWeather";
	}
	
	@ModelAttribute("savedLocations")
	public Map<String, String[]> getSavedLocations(PortletRequest request) {
		PortletPreferences preferences = request.getPreferences();
		return weatherService.getSavedLocationsMap(preferences);
	}
	
	@RequestMapping(params = "action=search")
	public void searchLocation( 
			Model model, 
			ActionResponse response,
			@ModelAttribute("weatherFormModel") WeatherFormModel weatherFormModel) {
		model.addAttribute("locationResults", weatherService
				.find(weatherFormModel.getLocation()));
		response.setRenderParameter("action", "add");
	}
	
	@RequestMapping(params = "action=add")
	public String showAddLocationForm() {
		return "editWeather";
	}
	
	@RequestMapping(params = {"action=add", "cancelSearchSubmit"})
	public void cancelAddLocation(ActionResponse response) {
		PortletUtils.clearAllRenderParameters(response);
	}
	
	@RequestMapping(params = {"action=add", "addSubmit"})
	public void addLocation(
			ActionRequest request, 
			ActionResponse response,
			@ModelAttribute("weatherFormModel") WeatherFormModel formModel) {
		//make sure they chose a location
		if (StringUtils.hasText(formModel.getLocationCode())) {
			PortletPreferences preferences = request.getPreferences();
			weatherService.addWeatherLocation(preferences, formModel);
			response.setRenderParameter("action", "search");
		}
	}
	
	@RequestMapping(params = "action=done")
	public String done() {
		return "viewWeather";
	}
	
	public static class WeatherFormModel {
		
		private String location = null; //this represents the text box
		private String locationCode = null; //this is internal and passed to getWeather
		private String metric = null; //this represents the drop down

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getLocationCode() {
			return locationCode;
		}

		public void setLocationCode(String locationCode) {
			this.locationCode = locationCode;
		}

		public String getMetric() {
			return metric;
		}

		public void setMetric(String metric) {
			this.metric = metric;
		}
	}
}
