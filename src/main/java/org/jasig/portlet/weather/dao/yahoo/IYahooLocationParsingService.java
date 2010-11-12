package org.jasig.portlet.weather.dao.yahoo;

import java.io.InputStream;
import java.util.List;

import org.jasig.portlet.weather.domain.Location;

public interface IYahooLocationParsingService {
    
    public List<Location> parseLocations(InputStream xml);

}
