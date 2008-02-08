/**
 * NdfdXML.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl;

public interface NdfdXML extends javax.xml.rpc.Service {

/**
 * The service has two exposed functions, NDFDgen and NDFDgenByDay.
 * For the NDFDgen function, the client needs to provide a latitude and
 * longitude pair and the product type.  The client also needs to provide
 * the start and end time of the period that it wants data for.  For
 * the time-series product, the client needs to provide an array of boolean
 * values corresponding to which weather values should appear in the
 * time series product.  For the NDFDgenByDay function, the client needs
 * to provide a latitude and longitude pair, the date it wants to start
 * retrieving data for and the number of days worth of data.  The client
 * also needs to provide the format that is desired.
 */
    public java.lang.String getndfdXMLPortAddress();

    public gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortType getndfdXMLPort() throws javax.xml.rpc.ServiceException;

    public gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortType getndfdXMLPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
