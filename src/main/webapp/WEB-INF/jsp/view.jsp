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
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<div id="${n}jasigWeatherPortlet" class="jasigWeatherPortlet">
<c:choose>
	<c:when test="${empty weathers}">
		<p><spring:message code="view.location.notset"/></p>
        <div class="edit-link">
            <portlet:renderURL var="editUrl"  portletMode="EDIT" />
            <a href="${editUrl}"><spring:message code="view.edit-link"/></a>
        </div>
	</c:when>
	<c:otherwise>
		<c:forEach var="weather" items="${weathers}">
			<div class="weather-location">
				<div class="location">
					<c:choose>
						<c:when test="${not empty weather.moreInformationLink}">
							<a href="${weather.moreInformationLink}" target="_blank" title="${weather.moreInformationLink}">${weather.location.city}, ${weather.location.stateOrCountry}</a>
						</c:when>
						<c:otherwise>
							${weather.location.city}, ${weather.location.stateOrCountry}
						</c:otherwise>
					</c:choose>
				</div>
				<div class="conditions">
					<c:set var="current" value="${weather.currentWeather}"/>
					<div class="current">
						<c:choose>
							<c:when test="${not empty current.temperature && not empty current.condition}">
								<spring:message var="currW" code="current.condition.img.width"/>
								<spring:message var="currH" code="current.condition.img.height"/>
								<spring:message var="currImgPath" code="current.condition.img.path"/>
								<spring:message var="currExt" code="current.condition.img.extension"/>
								<spring:message code="view.currently"/><br/>
                                <c:choose>
                                    <c:when test="${ not empty current.imgUrl }">
                                        <img src="${ current.imgUrl }" alt="${current.condition}" title="${current.condition}"/><br/>
                                    </c:when>
                                    <c:otherwise>
                                        <img width="${currW}" height="${currH}" src="${context}/${currImgPath}/${current.imgName}${currExt}" alt="${current.condition}" title="${current.condition}"/><br/>
                                    </c:otherwise>
                                </c:choose>
								${current.temperature}&#xB0; ${weather.temperatureUnit}
							</c:when>
							<c:otherwise>
								<spring:message code="view.current.weather.unavailable"/>
							</c:otherwise>
						</c:choose>
						<br/>
						<c:choose>
							<c:when test="${not empty current.windDirection && not empty current.windSpeed}">
								<spring:message code="view.wind"/>: ${current.windDirection} <spring:message code="view.at"/> ${current.windSpeed} ${weather.windUnit}
							</c:when>
							<c:otherwise>
								<spring:message code="view.wind"/>: <spring:message code="view.unavailable"/>
							</c:otherwise>
						</c:choose>
						<br/>
						<c:choose>
							<c:when test="${not empty current.humidity}">
								<spring:message code="view.humidity"/>: ${current.humidity}&#37;
							</c:when>
							<c:otherwise>
								<spring:message code="view.humidity"/>: <spring:message code="view.unavailable"/>
							</c:otherwise>
						</c:choose>
						<br/>
						<c:choose>
							<c:when test="${not empty current.pressure}">
								<spring:message code="view.pressure"/>: ${current.pressure} ${weather.pressureUnit}
							</c:when>
							<c:otherwise>
								<spring:message code="view.pressure"/>: <spring:message code="view.unavailable"/>
							</c:otherwise>
						</c:choose>
					</div>
					<c:if test="${not empty weather.forecast}">
						<div class="fullforecast">
							<c:forEach var="forecast" items="${weather.forecast}">
								<div class="forecast">
									${forecast.day}<br/>
									<spring:message var="forecastW" code="forecast.condition.img.width"/>
									<spring:message var="forecastH" code="forecast.condition.img.height"/>
									<spring:message var="forecastImgPath" code="forecast.condition.img.path"/>
									<spring:message var="forecastExt" code="forecast.condition.img.extension"/>
                                    <c:choose>
                                        <c:when test="${ not empty forecast.imgUrl }">
                                            <img src="${ forecast.imgUrl }" alt="${forecast.condition}" title="${forecast.condition}"/><br/>
                                        </c:when>
                                        <c:otherwise>
                                            <img width="${forecastW}" height="${forecastH}" src="${context}/${forecastImgPath}/${forecast.imgName}${forecastExt}" alt="${forecast.condition}" title="${forecast.condition}"/><br/>
                                        </c:otherwise>
                                    </c:choose>
									${forecast.highTemperature}&#xB0; | ${forecast.lowTemperature}&#xB0;
								</div>
							</c:forEach>
						</div>
					</c:if>
				</div>
			</div>
		</c:forEach>
        <c:if test="${fn:length(errors) > 0}">
            <div class="error">
                <h4><spring:message code="view.errors.header"/></h4>
                <ul>
                    <c:forEach var="errorLocation" items="${errors}">
                        <li><spring:message htmlEscape="true" text="${errorLocation.name}" /></li>
                    </c:forEach>
                </ul>
                <portlet:renderURL var="refreshUrl" />
                <p><a href="${refreshUrl}"><spring:message code="view.errors.refresh"/></a></p>
            </div>
        </c:if>
        <div class="footer">
            <c:set var="serviceUrl"><a href="${ serviceUrl }">${ serviceName }</a></c:set>
    		<spring:message var="providedBy" code="view.weather.providedBy" arguments="${ serviceUrl }" htmlEscape="false"/>
    		<c:if test="${fn:length(providedBy) > 0}">
    			<div class="provided-by">
    				${providedBy}
    			</div>
    		</c:if>
            <c:if test="${showEditLink == true && isGuest == false}">
                <div class="edit-link">
                    <portlet:renderURL var="editUrl"  portletMode="EDIT" windowState="MAXIMIZED" />
                    <a href="${editUrl}"><spring:message code="view.edit-link"/></a>
                </div>
            </c:if>
        </div>
	</c:otherwise>
</c:choose>
</div>
