package gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl;

public class NdfdXMLPortTypeProxy implements gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortType {
  private String _endpoint = null;
  private gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortType ndfdXMLPortType = null;
  
  public NdfdXMLPortTypeProxy() {
    _initNdfdXMLPortTypeProxy();
  }
  
  public NdfdXMLPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initNdfdXMLPortTypeProxy();
  }
  
  private void _initNdfdXMLPortTypeProxy() {
    try {
      ndfdXMLPortType = (new gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLLocator()).getndfdXMLPort();
      if (ndfdXMLPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)ndfdXMLPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)ndfdXMLPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (ndfdXMLPortType != null)
      ((javax.xml.rpc.Stub)ndfdXMLPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortType getNdfdXMLPortType() {
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType;
  }
  
  public java.lang.String NDFDgen(java.math.BigDecimal latitude, java.math.BigDecimal longitude, gov.weather.www.forecasts.xml.DWMLgen.schema.DWML_xsd.ProductType product, java.util.Calendar startTime, java.util.Calendar endTime, gov.weather.www.forecasts.xml.DWMLgen.schema.DWML_xsd.WeatherParametersType weatherParameters) throws java.rmi.RemoteException{
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType.NDFDgen(latitude, longitude, product, startTime, endTime, weatherParameters);
  }
  
  public java.lang.String NDFDgenByDay(java.math.BigDecimal latitude, java.math.BigDecimal longitude, java.util.Date startDate, java.math.BigInteger numDays, gov.weather.www.forecasts.xml.DWMLgen.schema.DWML_xsd.FormatType format) throws java.rmi.RemoteException{
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType.NDFDgenByDay(latitude, longitude, startDate, numDays, format);
  }
  
  
}