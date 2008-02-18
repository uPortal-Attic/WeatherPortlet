<%-- Author: Dustin Schultz | Version $Id$ --%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:choose>
	<c:when test="${empty weather}">
		<p>Currently there is no weather location set, please set a location using the edit link.</p>
	</c:when>
	<c:otherwise>
		<div id="weatherportlet">
			<c:if test="${not empty weather.logoPath}">
				<div style="float:right;">
					<img width="${weather.logoWidth}" height="${weather.logoHeight}" src="${context}/${weather.logoPath}"/>
				</div>
			</c:if>
			<div class="location">
				<c:choose>
					<c:when test="${not empty weather.moreInformationLink}">
						<a href="${weather.moreInformationLink} target="_blank" title="${weather.moreInformationLink}">${weather.location.city}, ${weather.location.stateOrCountry}</a>
					</c:when>
					<c:otherwise>
						${weather.location.city}, ${weather.location.stateOrCountry}
					</c:otherwise>
				</c:choose>
			</div>
			<c:set var="current" value="${weather.currentWeather}"/>
			<div class="current">
				<c:choose>
					<c:when test="${not empty current.temperature && not empty current.condition}">
						Currently<br/>
						<img width="${current.conditionImgWidth}" height="${current.conditionImgHeight}" src="${context}/${current.conditionImgPath}" alt="${current.condition}" title="${current.condition}"/><br/>
						${current.temperature}&#xB0; ${weather.temperatureUnit}
					</c:when>
					<c:otherwise>
						Current temperature and condition unavailable
					</c:otherwise>
				</c:choose>
				<br/>
				<c:choose>
					<c:when test="${not empty current.windDirection && not empty current.windSpeed}">
						Wind: ${current.windDirection} at ${current.windSpeed} ${weather.windUnit}
					</c:when>
					<c:otherwise>
						Wind: Unavailable
					</c:otherwise>
				</c:choose>
				<br/>
				<c:choose>
					<c:when test="${not empty current.humidity}">
						Humidity: ${current.humidity}&#37;
					</c:when>
					<c:otherwise>
						Humidity: Unavailable
					</c:otherwise>
				</c:choose>
				<br/>
				<c:choose>
					<c:when test="${not empty current.pressure}">
						Pressure: ${current.pressure} ${weather.pressureUnit}
					</c:when>
					<c:otherwise>
						Pressure: Unavailable
					</c:otherwise>
				</c:choose>
			</div>
			<c:if test="${not empty weather.forecast}">
				<c:forEach var="forecast" items="${weather.forecast}">
					<div class="forecast">
						${forecast.day}<br/>
						<img width="${forecast.conditionImgWidth}" height="${forecast.conditionImgHeight}" src="${context}/${forecast.conditionImgPath}" alt="${forecast.condition}" title="${forecast.condition}"/><br/>
						${forecast.highTemperature}&#xB0; | ${forecast.lowTemperature}&#xB0;
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${not empty weather.providedBy}">
				<div style="clear:both;font-size:70%;text-align:left;">
					${weather.providedBy}
				</div>
			</c:if>
		</div>
	</c:otherwise>
</c:choose>