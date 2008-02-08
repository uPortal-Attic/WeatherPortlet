/**
 * NdfdXMLPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl;

public interface NdfdXMLPortType extends java.rmi.Remote {

    /**
     * Returns National Weather Service digital weather forecast data
     */
    public java.lang.String NDFDgen(java.math.BigDecimal latitude, java.math.BigDecimal longitude, gov.weather.www.forecasts.xml.DWMLgen.schema.DWML_xsd.ProductType product, java.util.Calendar startTime, java.util.Calendar endTime, gov.weather.www.forecasts.xml.DWMLgen.schema.DWML_xsd.WeatherParametersType weatherParameters) throws java.rmi.RemoteException;

    /**
     * Returns National Weather Service digital weather forecast data
     * summarized over either 24- or 12-hourly periods
     */
    public java.lang.String NDFDgenByDay(java.math.BigDecimal latitude, java.math.BigDecimal longitude, java.util.Date startDate, java.math.BigInteger numDays, gov.weather.www.forecasts.xml.DWMLgen.schema.DWML_xsd.FormatType format) throws java.rmi.RemoteException;
}
