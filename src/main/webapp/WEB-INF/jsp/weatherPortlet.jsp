<html xmlns="http://www.w3c.org/1999/xhtml" xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core" xml:lang="en" lang="en">
	<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>
	<head> </head>

	<body>

		<table>
			<c:forEach items="${model.weatherObjects}" var="obj">
				<c:set var="location" value="${obj.key}"/>
				<c:set var="weather" value="${obj.value}"/>
				<tr>
					<td colspan="4">
						<h2>
							<a href="${weather.link}" target="_blank">${weather.city}, <c:choose>
									<c:when test="${not empty weather.region}">${weather.region}</c:when>
									<c:otherwise>${weather.country}</c:otherwise>
								</c:choose>
							</a>
						</h2>
					</td>
				</tr>
				<tr>
					<td style="width: 10px; padding-bottom: 15px;">&nbsp;</td>
					<td id="weather-summary-${ location }" style="text-align: center; padding-bottom: 15px;">
						<img
							src="http://l.yimg.com/us.yimg.com/i/us/we/52/${weather.conditionCode}.gif"/>
						<p>${weather.conditionDescription}, ${weather.temp}&#176; ${weather.tempUnits}</p>
					</td>
					<c:forEach items="${weather.forecasts}" var="forecast">
						<td style="text-align: center; padding-left: 15px; padding-bottom: 15px;">
							<p> ${forecast.day}<br/>
								<img
									src="http://us.i1.yimg.com/us.yimg.com/i/us/nws/weather/gr/${forecast.code}s.png"
								/><br/> ${forecast.high} / ${forecast.low} </p>
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>

		<c:forEach items="${model.weatherObjects}" var="obj">
			<c:set var="location" value="${obj.key}"/>
			<c:set var="weather" value="${obj.value}"/>
		</c:forEach>

		<br />
		<p style="font-size: .9em"><a href="http://weather.yahoo.com" target="_blank">Yahoo! Weather, provided by The Weather Channel</a></p>

	</body>
</html>
