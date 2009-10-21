<%-- Author: Dustin Schultz | Version $Id$ --%>
<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="rs" uri="http://www.jasig.org/resource-server" %>

<portlet:defineObjects/>
<c:set var="useInlineCSSTags" value="${renderRequest.preferences.map['useInlineCSSTags'][0]}"/>
<c:set var="includeJQuery" value="${renderRequest.preferences.map['includeJQuery'][0]}"/>

<c:set var="n"><portlet:namespace/></c:set>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:if test="${useInlineCSSTags}">
    <link rel="stylesheet" href="<c:url value="/css/weather.css"/>" type="text/css" />
</c:if>
