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