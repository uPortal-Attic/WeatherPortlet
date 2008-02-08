<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="org.jasig.portlet.weather.*"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Weather</title>
</head>
<body>

<portlet:renderURL var="renderURL" portletMode="view" windowState="maximized">
    <portlet:param name="action" value="state"/>
</portlet:renderURL>

<div style="float: right;">
<a href="${renderURL}">Customize</a>
</div>
<h2><c:out value="${model.obs.location}" /></h2>
<br />
Current conditions:
<br />

<table class="uportal-channel-text" cellpadding="5" cellspacing="5"
    width="100%">
    <tr>
     
        <td>
          <img src="<c:out value="${model.obs.iconUrlBase}${model.obs.iconUrlName}"/>" alt="" />
        <br />
         
        <b><c:out value="${model.obs.weather}" /></b>
        </td>
        <td>
        <b><c:out value="${model.obs.tempF}" />&deg; F</b>
        </td>
        <td><c:if test="${!(model.obs.heatIndexF==null)}">
        Heat Index: <c:out value="${model.obs.heatIndexF}" />
            <br />
        </c:if> <c:if test="${!(model.obs.windchillF==null)}">
        Wind Chill: <c:out value="${model.obs.windchillF}" />
            <br />
        </c:if> Humidity: <c:out value="${model.obs.relativeHumidity}" />%<br />
        Wind: <c:out value="${model.obs.windDir}" />&#160;<c:out
            value="${model.obs.windMph}" /> mph<br/>
            <a href="<c:out value="${model.station.htmlUrl}"/>">Details</a>
        </td>
    </tr>
</table>
<br />

<br />

</body>
</html>