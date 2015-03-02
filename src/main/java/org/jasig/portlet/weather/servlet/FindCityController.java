/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.portlet.weather.servlet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.portlet.weather.InvalidConfigurationException;
import org.jasig.portlet.weather.domain.Location;
import org.jasig.portlet.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FindCityController {
    private static final String ERR_GENERAL_KEY = "exception.generalError.title";

    private IWeatherService weatherService;

    @Autowired
    public void setWeatherService(IWeatherService weatherService) {
        this.weatherService = weatherService;
    }

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @RequestMapping("/findCity")
	public ModelAndView findCity(
	        @RequestParam("location") String location,
	        HttpServletRequest request, HttpServletResponse response) {

        final Map<String, Object> model = new LinkedHashMap<String, Object>();
        final Collection<Location> locations = new ArrayList<Location>();

        try {
        	locations.addAll(this.weatherService.find(location));
        } catch (InvalidConfigurationException invalidConfigurationException) {
            model.put("error",invalidConfigurationException.getMessage());
        } catch (DataRetrievalFailureException dataRetrievalFailed) {
            model.put("error",messageSource.getMessage(ERR_GENERAL_KEY,null,"An unexpected error occurred.",Locale.getDefault()));
        }

        model.put("locations", locations);
        return new ModelAndView("jsonView", model);
	}
}
