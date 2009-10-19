package org.jasig.portlet.weather.servlet;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FindCityController {
    private IWeatherService weatherService;

    @Autowired
    public void setWeatherService(IWeatherService weatherService) {
        this.weatherService = weatherService;
    }
    
    @RequestMapping("/findCity")
	public ModelAndView findCity(
	        @RequestParam("location") String location,
	        HttpServletRequest request, HttpServletResponse response) {
        
        final Collection<Location> locations = this.weatherService.find(location);
        
        final Map<Object, Object> model = new LinkedHashMap<Object, Object>();
        model.put("locations", locations);
        return new ModelAndView("jsonView", model);
	}
}
