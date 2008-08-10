package org.jasig.portlet.weather.portlet;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

import org.jasig.portlet.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("VIEW")
public class WeatherViewController {
	
	@Autowired
	private IWeatherService weatherService;
	
	@RequestMapping
	public String displayAllWeather(RenderRequest request, Model model) {
		PortletPreferences preferences = request.getPreferences();
		model.addAttribute("weathers", weatherService.getAllWeather(preferences));
		return "viewWeather";
	}
}
