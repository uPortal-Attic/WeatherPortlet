package org.jasig.portlet.weather.portlet;

import java.util.Collection;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.SimpleFormController;

public class WeatherSearchController extends SimpleFormController {

	private IWeatherService weatherService = null; // Spring managed
	
	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmitRender(RenderRequest request,
			RenderResponse response, Object command, BindException errors)
			throws Exception {
		WeatherEditCommand formData = (WeatherEditCommand)command;
		Collection<Location> locations = weatherService.find(formData.getLocation());
		Map map = errors.getModel();
		map.put("locations", locations);
		return new ModelAndView("edit", map);
	}
	
	@Autowired
	public void setWeatherService(IWeatherService weatherService) {
		this.weatherService = weatherService;
	}
}
