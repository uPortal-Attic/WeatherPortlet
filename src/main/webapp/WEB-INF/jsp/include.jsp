<%--

    Licensed to Apereo under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Apereo licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%-- Author: Dustin Schultz | Version $Id$ --%>
<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="rs" uri="http://www.jasig.org/resource-server" %>

<portlet:defineObjects/>
<c:set var="useInlineCSSTags" value="${renderRequest.preferences.map['useInlineCSSTags'][0]}"/>
<c:set var="includeJQuery" value="${renderRequest.preferences.map['includeJQuery'][0]}"/> <%-- Not currently used --%>
<c:set var="showEditLink" value="${renderRequest.preferences.map['showEditLink'][0]}"/>

<c:set var="n"><portlet:namespace/></c:set>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:if test="${useInlineCSSTags}">
    <link rel="stylesheet" href="<c:url value="/css/weather.min.css"/>" type="text/css" />
</c:if>
