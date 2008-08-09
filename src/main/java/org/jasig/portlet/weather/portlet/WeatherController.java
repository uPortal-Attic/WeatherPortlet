package org.jasig.portlet.weather.portlet;

import java.util.Collection;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

import org.jasig.portlet.weather.domain.Weather;
import org.jasig.portlet.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("VIEW")
public class WeatherController {
	
	@Autowired
	private IWeatherService weatherService;
	
	private static final String VIEW_NAME = "viewWeather";
	
	@ModelAttribute("weathers")
	public Collection<Weather> getAllWeathers(RenderRequest request) {
		PortletPreferences preferences = request.getPreferences();
		return weatherService.getAllWeather(preferences);
	}
	
	@RequestMapping
	public String getView() {
		return VIEW_NAME;
	}
}
