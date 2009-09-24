<%-- Author: Dustin Schultz | Version $Id$ --%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<portlet:defineObjects/>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:if test="${renderRequest.preferences.map['useInlineCSSTags'][0]}">
	<link rel="stylesheet" href="<c:url value="/css/weather.css"/>" type="text/css" />
</c:if>
<div class="weatherportlet">
<c:choose>
	<c:when test="${empty weathers}">
		<p><spring:message code="view.location.notset"/></p>
	</c:when>
	<c:otherwise>
		<spring:message var="logo" code="logo.img.full.path"/>
		<spring:message var="provByLink" code="view.weather.providedBy.link"/>
		<c:if test="${fn:length(logo) > 0 && fn:length(provByLink) > 0}">
			<div class="weather-logo">
				<spring:message var="logoW" code="logo.img.width"/>
				<spring:message var="logoH" code="logo.img.height"/>
				<a href="${provByLink}" target="_blank"><img width="${logoW}" height="${logoH}" src="${context}/${logo}"/></a>
			</div>
		</c:if>
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
								<img width="${currW}" height="${currH}" src="${context}/${currImgPath}/${current.imgName}${currExt}" alt="${current.condition}" title="${current.condition}"/><br/>
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
									<img width="${forecastW}" height="${forecastH}" src="${context}/${forecastImgPath}/${forecast.imgName}${forecastExt}" alt="${forecast.condition}" title="${forecast.condition}"/><br/>
									${forecast.highTemperature}&#xB0; | ${forecast.lowTemperature}&#xB0;
								</div>
							</c:forEach>
						</div>
					</c:if>
				</div>
			</div>
		</c:forEach>
		<spring:message var="providedBy" code="view.weather.providedBy"/>
		<c:if test="${fn:length(providedBy) > 0}">
			<div class="provided-by">
				${providedBy}
			</div>
		</c:if>
	</c:otherwise>
</c:choose>
</div>