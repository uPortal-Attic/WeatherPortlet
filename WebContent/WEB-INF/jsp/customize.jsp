<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="spring" uri="/spring" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Select Weather Station</title>
</head>
<body>

<c:choose>
    <c:when test="${empty page}">
        <c:set var="page" value="0"/>
    </c:when>
    <c:otherwise>
        <c:set var="page" value="${page}"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${page == 1}">
        <c:set var="nextPage" value="${null}"/>
    </c:when>
    <c:otherwise>
        <c:set var="nextPage" value="${page + 1}"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${page == 0}">
        <c:set var="prevPage" value="${null}"/>
    </c:when>
    <c:otherwise>
        <c:set var="prevPage" value="${page - 1}"/>
    </c:otherwise>
</c:choose>


<portlet:actionURL var="formAction" portletMode="view">
    <portlet:param name="action" value="state"/>
</portlet:actionURL>

<form:form commandName="customize" method="post" action="${formAction}">
    <form:errors path="*" cssStyle="color:red"/>

	<c:choose>
		<c:when test="${page == 0}">
	        <spring:bind path="customize.state">
	       Select the state: 
	      <select name="${status.expression}">
	                <c:forEach var="state" items="${listStates}">
	                    <option value="${state}"
	                        <c:if test="${state == status.value}"> selected="selected"</c:if>>${state}
	                    </option>
	                </c:forEach>
	            </select>
	        </spring:bind>
		</c:when>
		<c:when test="${page == 1}">
		    <spring:bind path="customize.stationId">
		      Select the station: 
		      <select name="${status.expression}">
		        <c:forEach var="station" items="${listStations}">
		            <option value="${station.stationId}" 
		                <c:if test="${station.stationId == status.value}"> selected="selected"</c:if>
		            >${station.name}
		            </option>
		        </c:forEach>
		      </select>
		    <br/>   
		    </spring:bind>
		</c:when>
	</c:choose>

		<br />
                <c:choose>
                    <c:when test="${empty prevPage}">
                        <input type="submit" value="Previous" disabled="disabled"/>
                    </c:when>
                    <c:otherwise>
                        <input type="submit" name="_target<c:out value="${prevPage}"/>" value="Previous"/>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${empty nextPage}">
                        <input type="submit" value="Next" disabled="disabled"/>
                    </c:when>
                    <c:otherwise>
                        <input type="submit" name="_target<c:out value="${nextPage}"/>" value="Next"/>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${empty nextPage}">
                        <input type="submit" name="_finish" value="Finish"/>
                    </c:when>
                    <c:otherwise>
                        <input type="submit" value="Finish" disabled="disabled"/>
                    </c:otherwise>
                </c:choose>
                <input type="submit" name="_cancel" value="Cancel"/>
</form:form>
<br/>
<!-- 
Switch to: 
<a href="<portlet:renderURL portletMode="view"/>">
    View Mode
</a>
 -->
</body>
</html>