/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.InputStream
import groovy.xml.StreamingMarkupBuilder
import org.jasig.portlet.weather.domain.*
import org.jasig.portlet.weather.dao.yahoo.IYahooWeatherParsingService

class YahooWeatherParsingServiceImpl implements IYahooWeatherParsingService {

    Weather parseWeather(InputStream xml) {
        def rss = new XmlSlurper().parse(xml)
        
        def weather = new Weather()
        weather.setPressureUnit(rss.channel.units.@pressure.toString())
        weather.setTemperatureUnit(rss.channel.units.@temperature.toString())
        weather.setWindUnit(rss.channel.units.@speed.toString())
        weather.setMoreInformationLink(rss.channel.link.toString())
        
        def current = new Current()
        current.setCondition(rss.channel.item.condition.@text.toString())
        current.setTemperature(rss.channel.item.condition.@temp.toInteger())
        current.setWindSpeed(rss.channel.wind.@speed.toDouble())
        current.setWindDirection(rss.channel.wind.@direction.toString())
        current.setHumidity(rss.channel.atmosphere.@humidity.toDouble())
        current.setPressure(rss.channel.atmosphere.@pressure.toDouble())
        current.setImgUrl("http://l.yimg.com/a/i/us/we/52/" + rss.channel.item.condition.@code.toString() + ".gif")
        weather.setCurrentWeather(current)
        
        def location = new Location()
        location.setCity(rss.channel.location.@city.toString())
        if (!rss.channel.location.@region.toString().equals("")) {
            location.setStateOrCountry(rss.channel.location.@region.toString())
        } else {
            location.setStateOrCountry(rss.channel.location.@country.toString())
        }
        location.setLatitude(rss.channel.item.lat.toDouble())
        location.setLongitude(rss.channel.item.long.toDouble())
        location.setLongitude()
        weather.setLocation(location)
        
        def list = new ArrayList()
        for (f in rss.channel.item.forecast) {
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
