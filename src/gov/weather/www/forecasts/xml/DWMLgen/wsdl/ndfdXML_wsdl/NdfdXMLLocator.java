/**
 * NdfdXMLLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl;

@SuppressWarnings("unchecked")
public class NdfdXMLLocator extends org.apache.axis.client.Service implements gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXML {

/**
	 * 
	 */
	private static final long serialVersionUID = -2845018937938418511L;

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

    public NdfdXMLLocator() {
    }


    public NdfdXMLLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public NdfdXMLLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ndfdXMLPort
    private java.lang.String ndfdXMLPort_address = "http://www.weather.gov/forecasts/xml/SOAP_server/ndfdXMLserver.php";

    public java.lang.String getndfdXMLPortAddress() {
        return ndfdXMLPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ndfdXMLPortWSDDServiceName = "ndfdXMLPort";

    public java.lang.String getndfdXMLPortWSDDServiceName() {
        return ndfdXMLPortWSDDServiceName;
    }

    public void setndfdXMLPortWSDDServiceName(java.lang.String name) {
        ndfdXMLPortWSDDServiceName = name;
    }

    public gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortType getndfdXMLPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ndfdXMLPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getndfdXMLPort(endpoint);
    }

    public gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortType getndfdXMLPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLBindingStub _stub = new gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLBindingStub(portAddress, this);
            _stub.setPortName(getndfdXMLPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setndfdXMLPortEndpointAddress(java.lang.String address) {
        ndfdXMLPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLBindingStub _stub = new gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLBindingStub(new java.net.URL(ndfdXMLPort_address), this);
                _stub.setPortName(getndfdXMLPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ndfdXMLPort".equals(inputPortName)) {
            return getndfdXMLPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.weather.gov/forecasts/xml/DWMLgen/wsdl/ndfdXML.wsdl", "ndfdXML");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.weather.gov/forecasts/xml/DWMLgen/wsdl/ndfdXML.wsdl", "ndfdXMLPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ndfdXMLPort".equals(portName)) {
            setndfdXMLPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
