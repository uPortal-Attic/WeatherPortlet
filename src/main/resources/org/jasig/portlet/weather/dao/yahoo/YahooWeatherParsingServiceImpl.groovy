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

import org.jasig.portlet.weather.dao.yahoo.IYahooWeatherParsingService
import org.jasig.portlet.weather.domain.Current
import org.jasig.portlet.weather.domain.Forecast
import org.jasig.portlet.weather.domain.Location
import org.jasig.portlet.weather.domain.Weather
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

class YahooWeatherParsingServiceImpl implements IYahooWeatherParsingService {

    @Autowired
    ApplicationContext context

    Weather parseWeather(InputStream xml) {
        def rss = new XmlSlurper().parse(xml)
        
        def weather = new Weather()
        weather.setPressureUnit(rss.results.channel.units.@pressure.toString())
        weather.setTemperatureUnit(rss.results.channel.units.@temperature.toString())
        weather.setWindUnit(rss.results.channel.units.@speed.toString())
        weather.setMoreInformationLink(rss.results.channel.link.toString())
        
        def current = new Current()
        current.setCondition(rss.results.channel.item.condition.@text.toString())
        current.setTemperature(rss.results.channel.item.condition.@temp.toInteger())
        current.setWindSpeed(rss.results.channel.wind.@speed.toDouble())
        current.setWindDirection(rss.results.channel.wind.@direction.toString())
        current.setHumidity(rss.results.channel.atmosphere.@humidity.toDouble())
        current.setPressure(rss.results.channel.atmosphere.@pressure.toDouble())
        if (current.getPressure() > 33) {
            weather.setPressureUnit(context.getMessage("units.pressure.millibar", null, Locale.getDefault()))
        } else {
            weather.setPressureUnit(context.getMessage("units.pressure.inches", null, Locale.getDefault()))
        }
        current.setImgUrl("https://s.yimg.com/zz/combo?/a/i/us/we/52/" + rss.results.channel.item.condition.@code.toString() + ".gif")
        weather.setCurrentWeather(current)
        
        def location = new Location()
        location.setCity(rss.results.channel.location.@city.toString())
        if (!rss.results.channel.location.@region.toString().equals("")) {
            location.setStateOrCountry(rss.results.channel.location.@region.toString())
        } else {
            location.setStateOrCountry(rss.results.channel.location.@country.toString())
        }
        location.setLatitude(rss.results.channel.item.lat.toDouble())
        location.setLongitude(rss.results.channel.item.long.toDouble())
        location.setLongitude()
        weather.setLocation(location)
        
        def list = new ArrayList()
        for (f in rss.results.channel.item.forecast) {
            def forecast = new Forecast()
            forecast.setDay(f.@day.toString())
            forecast.setHighTemperature(f.@high.toInteger())
            forecast.setLowTemperature(f.@low.toInteger())
            forecast.setCondition(f.@text.toString())
            forecast.setImgUrl("http://l.yimg.com/a/i/us/we/52/" + f.@code.toString() + ".gif")
            list.add(forecast)
        }
        weather.setForecast(list)
        
        return weather
    }

}
