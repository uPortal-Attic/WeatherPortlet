/**
 * ProductType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.weather.www.forecasts.xml.DWMLgen.schema.DWML_xsd;

@SuppressWarnings("unchecked")
public class ProductType implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6185300662762827733L;
	private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ProductType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "time-series";
    public static final java.lang.String _value2 = "glance";
    public static final ProductType value1 = new ProductType(_value1);
    public static final ProductType value2 = new ProductType(_value2);
    public java.lang.String getValue() { return _value_;}
    public static ProductType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ProductType enumeration = (ProductType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ProductType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProductType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.weather.gov/forecasts/xml/DWMLgen/schema/DWML.xsd", "productType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
