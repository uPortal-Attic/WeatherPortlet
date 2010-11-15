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
import org.jasig.portlet.weather.dao.yahoo.IYahooLocationParsingService

class YahooLocationParsingServiceImpl implements IYahooLocationParsingService {

    List parseLocations(InputStream xml) {
        def places = new XmlSlurper().parse(xml)
        
        def list = new ArrayList()
        for (place in places.place) {
            def location = new Location()
            location.setLocationCode(place.woeid.toString())
            location.setCity(place.locality1.toString())
            location.setStateOrCountry(place.admin1.toString())
            location.setLatitude(place.centroid.latitude.toDouble())
            location.setLongitude(place.centroid.longitude.toDouble())
            list.add(location)
        }
        
        return list
    }

}