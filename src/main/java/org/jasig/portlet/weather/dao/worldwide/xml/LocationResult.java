package org.jasig.portlet.weather.dao.worldwide.xml;

import javax.xml.bind.annotation.XmlElement;

public class LocationResult {

    private String city;
    private String region;
    private String country;
    private double latitude;
    private double longitude;

    public String getCity() {
        return city;
    }

    @XmlElement(name = "areaName")
    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    @XmlElement(name = "region")
    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    @XmlElement(name = "country")
    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    @XmlElement(name = "latitude")
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @XmlElement(name = "longitude")
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
