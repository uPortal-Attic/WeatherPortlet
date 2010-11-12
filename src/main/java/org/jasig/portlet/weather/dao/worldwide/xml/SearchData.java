package org.jasig.portlet.weather.dao.worldwide.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="search_api")
public class SearchData {
    
    private List<LocationResult> results;

    public List<LocationResult> getResults() {
        return results;
    }

    @XmlElement(name="result")
    public void setResults(List<LocationResult> results) {
        this.results = results;
    }

}
