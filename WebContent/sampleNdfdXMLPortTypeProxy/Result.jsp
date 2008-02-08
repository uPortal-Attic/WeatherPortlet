<%@page contentType="text/html;charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="sampleNdfdXMLPortTypeProxyid" scope="session" class="gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortTypeProxy" />
<%
if (request.getParameter("endpoint") != null && request.getParameter("endpoint").length() > 0)
sampleNdfdXMLPortTypeProxyid.setEndpoint(request.getParameter("endpoint"));
%>

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

if(methodID != -1) methodID = Integer.parseInt(method);
boolean gotMethod = false;

try {
switch (methodID){ 
case 2:
        gotMethod = true;
        java.lang.String getEndpoint2mtemp = sampleNdfdXMLPortTypeProxyid.getEndpoint();
if(getEndpoint2mtemp == null){
%>
<%=getEndpoint2mtemp %>
<%
}else{
        String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getEndpoint2mtemp));
        %>
        <%= tempResultreturnp3 %>
        <%
}
break;
case 5:
        gotMethod = true;
        String endpoint_0id=  request.getParameter("endpoint8");
        java.lang.String endpoint_0idTemp  = endpoint_0id;
        sampleNdfdXMLPortTypeProxyid.setEndpoint(endpoint_0idTemp);
break;
case 10:
        gotMethod = true;
        gov.weather.www.forecasts.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortType getNdfdXMLPortType10mtemp = sampleNdfdXMLPortTypeProxyid.getNdfdXMLPortType();
if(getNdfdXMLPortType10mtemp == null){
%>
<%=getNdfdXMLPortType10mtemp %>
<%
}else{
        if(getNdfdXMLPortType10mtemp!= null){
        String tempreturnp11 = getNdfdXMLPortType10mtemp.toString();
        %>
        <%=tempreturnp11%>
        <%
        }}
break;
case 13:
        gotMethod = true;
        String latitude_1id=  request.getParameter("latitude16");
        java.math.BigDecimal latitude_1idTemp  = new java.math.BigDecimal(latitude_1id);
        String longitude_2id=  request.getParameter("longitude18");
        java.math.BigDecimal longitude_2idTemp  = new java.math.BigDecimal(longitude_2id);
        gov.weather.www.forecasts.xml.DWMLgen.schema.DWML_xsd.ProductType gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1ProductType_3id = null;
        String startTime_4id=  request.getParameter("startTime22");
        java.text.DateFormat dateFormatstartTime22 = java.text.DateFormat.getDateInstance();
        java.util.Date dateTempstartTime22  = dateFormatstartTime22.parse(startTime_4id);
        java.util.GregorianCalendar startTime_4idTemp = new java.util.GregorianCalendar();
        startTime_4idTemp.setTime(dateTempstartTime22);
        String endTime_5id=  request.getParameter("endTime24");
        java.text.DateFormat dateFormatendTime24 = java.text.DateFormat.getDateInstance();
        java.util.Date dateTempendTime24  = dateFormatendTime24.parse(endTime_5id);
        java.util.GregorianCalendar endTime_5idTemp = new java.util.GregorianCalendar();
        endTime_5idTemp.setTime(dateTempendTime24);
        String appt_7id=  request.getParameter("appt28");
        boolean appt_7idTemp  = Boolean.valueOf(appt_7id).booleanValue();
        String pxtstmwinds_8id=  request.getParameter("pxtstmwinds30");
        boolean pxtstmwinds_8idTemp  = Boolean.valueOf(pxtstmwinds_8id).booleanValue();
        String wgust_9id=  request.getParameter("wgust32");
        boolean wgust_9idTemp  = Boolean.valueOf(wgust_9id).booleanValue();
        String dew_10id=  request.getParameter("dew34");
        boolean dew_10idTemp  = Boolean.valueOf(dew_10id).booleanValue();
        String pop12_11id=  request.getParameter("pop1236");
        boolean pop12_11idTemp  = Boolean.valueOf(pop12_11id).booleanValue();
        String ptotsvrtstm_12id=  request.getParameter("ptotsvrtstm38");
        boolean ptotsvrtstm_12idTemp  = Boolean.valueOf(ptotsvrtstm_12id).booleanValue();
        String snow_13id=  request.getParameter("snow40");
        boolean snow_13idTemp  = Boolean.valueOf(snow_13id).booleanValue();
        String maxt_14id=  request.getParameter("maxt42");
        boolean maxt_14idTemp  = Boolean.valueOf(maxt_14id).booleanValue();
        String mint_15id=  request.getParameter("mint44");
        boolean mint_15idTemp  = Boolean.valueOf(mint_15id).booleanValue();
        String waveh_16id=  request.getParameter("waveh46");
        boolean waveh_16idTemp  = Boolean.valueOf(waveh_16id).booleanValue();
        String incw50_17id=  request.getParameter("incw5048");
        boolean incw50_17idTemp  = Boolean.valueOf(incw50_17id).booleanValue();
        String wspd_18id=  request.getParameter("wspd50");
        boolean wspd_18idTemp  = Boolean.valueOf(wspd_18id).booleanValue();
        String cumw50_19id=  request.getParameter("cumw5052");
        boolean cumw50_19idTemp  = Boolean.valueOf(cumw50_19id).booleanValue();
        String wdir_20id=  request.getParameter("wdir54");
        boolean wdir_20idTemp  = Boolean.valueOf(wdir_20id).booleanValue();
        String pxhail_21id=  request.getParameter("pxhail56");
        boolean pxhail_21idTemp  = Boolean.valueOf(pxhail_21id).booleanValue();
        String pxtotsvrtstm_22id=  request.getParameter("pxtotsvrtstm58");
        boolean pxtotsvrtstm_22idTemp  = Boolean.valueOf(pxtotsvrtstm_22id).booleanValue();
        String ptstmwinds_23id=  request.getParameter("ptstmwinds60");
        boolean ptstmwinds_23idTemp  = Boolean.valueOf(ptstmwinds_23id).booleanValue();
        String sky_24id=  request.getParameter("sky62");
        boolean sky_24idTemp  = Boolean.valueOf(sky_24id).booleanValue();
        String qpf_25id=  request.getParameter("qpf64");
        boolean qpf_25idTemp  = Boolean.valueOf(qpf_25id).booleanValue();
        String pxtornado_26id=  request.getParameter("pxtornado66");
        boolean pxtornado_26idTemp  = Boolean.valueOf(pxtornado_26id).booleanValue();
        String incw34_27id=  request.getParameter("incw3468");
        boolean incw34_27idTemp  = Boolean.valueOf(incw34_27id).booleanValue();
        String incw64_28id=  request.getParameter("incw6470");
        boolean incw64_28idTemp  = Boolean.valueOf(incw64_28id).booleanValue();
        String cumw34_29id=  request.getParameter("cumw3472");
        boolean cumw34_29idTemp  = Boolean.valueOf(cumw34_29id).booleanValue();
        String rh_30id=  request.getParameter("rh74");
        boolean rh_30idTemp  = Boolean.valueOf(rh_30id).booleanValue();
        String ptornado_31id=  request.getParameter("ptornado76");
        boolean ptornado_31idTemp  = Boolean.valueOf(ptornado_31id).booleanValue();
        String cumw64_32id=  request.getParameter("cumw6478");
        boolean cumw64_32idTemp  = Boolean.valueOf(cumw64_32id).booleanValue();
        String phail_33id=  request.getParameter("phail80");
        boolean phail_33idTemp  = Boolean.valueOf(phail_33id).booleanValue();
        String temp_34id=  request.getParameter("temp82");
        boolean temp_34idTemp  = Boolean.valueOf(temp_34id).booleanValue();
        String conhazo_35id=  request.getParameter("conhazo84");
        boolean conhazo_35idTemp  = Boolean.valueOf(conhazo_35id).booleanValue();
        String icons_36id=  request.getParameter("icons86");
        boolean icons_36idTemp  = Boolean.valueOf(icons_36id).booleanValue();
        String wx_37id=  request.getParameter("wx88");
        boolean wx_37idTemp  = Boolean.valueOf(wx_37id).booleanValue();
        %>
        <jsp:useBean id="gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id" scope="session" class="gov.weather.www.forecasts.xml.DWMLgen.schema.DWML_xsd.WeatherParametersType" />
        <%
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setAppt(appt_7idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setPxtstmwinds(pxtstmwinds_8idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setWgust(wgust_9idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setDew(dew_10idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setPop12(pop12_11idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setPtotsvrtstm(ptotsvrtstm_12idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setSnow(snow_13idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setMaxt(maxt_14idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setMint(mint_15idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setWaveh(waveh_16idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setIncw50(incw50_17idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setWspd(wspd_18idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setCumw50(cumw50_19idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setWdir(wdir_20idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setPxhail(pxhail_21idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setPxtotsvrtstm(pxtotsvrtstm_22idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setPtstmwinds(ptstmwinds_23idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setSky(sky_24idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setQpf(qpf_25idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setPxtornado(pxtornado_26idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setIncw34(incw34_27idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setIncw64(incw64_28idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setCumw34(cumw34_29idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setRh(rh_30idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setPtornado(ptornado_31idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setCumw64(cumw64_32idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setPhail(phail_33idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setTemp(temp_34idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setConhazo(conhazo_35idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setIcons(icons_36idTemp);
        gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id.setWx(wx_37idTemp);
        java.lang.String NDFDgen13mtemp = sampleNdfdXMLPortTypeProxyid.NDFDgen(latitude_1idTemp,longitude_2idTemp,gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1ProductType_3id,startTime_4idTemp,endTime_5idTemp,gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1WeatherParametersType_6id);
if(NDFDgen13mtemp == null){
%>
<%=NDFDgen13mtemp %>
<%
}else{
        String tempResultreturnp14 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(NDFDgen13mtemp));
        %>
        <%= tempResultreturnp14 %>
        <%
}
break;
case 90:
        gotMethod = true;
        String latitude_38id=  request.getParameter("latitude93");
        java.math.BigDecimal latitude_38idTemp  = new java.math.BigDecimal(latitude_38id);
        String longitude_39id=  request.getParameter("longitude95");
        java.math.BigDecimal longitude_39idTemp  = new java.math.BigDecimal(longitude_39id);
        String startDate_40id=  request.getParameter("startDate97");
        java.text.DateFormat dateFormatstartDate97 = java.text.DateFormat.getDateInstance();
        java.util.Date startDate_40idTemp= dateFormatstartDate97.parse(startDate_40id);
        String numDays_41id=  request.getParameter("numDays99");
        java.math.BigInteger numDays_41idTemp  = new java.math.BigInteger(numDays_41id);
        gov.weather.www.forecasts.xml.DWMLgen.schema.DWML_xsd.FormatType gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1FormatType_42id = null;
        java.lang.String NDFDgenByDay90mtemp = sampleNdfdXMLPortTypeProxyid.NDFDgenByDay(latitude_38idTemp,longitude_39idTemp,startDate_40idTemp,numDays_41idTemp,gov1weather1www1forecasts1xml1DWMLgen1schema1DWML_xsd1FormatType_42id);
if(NDFDgenByDay90mtemp == null){
%>
<%=NDFDgenByDay90mtemp %>
<%
}else{
        String tempResultreturnp91 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(NDFDgenByDay90mtemp));
        %>
        <%= tempResultreturnp91 %>
        <%
}
break;
}
} catch (Exception e) { 
%>
exception: <%= e %>
<%
return;
}
if(!gotMethod){
%>
result: N/A
<%
}
%>
</BODY>
</HTML>