<html xmlns="http://www.w3c.org/1999/xhtml" xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:portlet="http://java.sun.com/portlet"
	xmlns:html="/WEB-INF/tags/html" xmlns:form="http://www.springframework.org/tags/form"
	xml:lang="en" lang="en">
	<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>
	<head>
		<script type="text/javascript">
			function addWeatherLocation(id) {
				var div = document.getElementById(id);
				var container = document.createElement('div');
				container.style.padding = "5px";
				var input = document.createElement('input');
				input.name = 'location';
				input.type = 'text';
				input.size = '8';
				container.appendChild(input);
				var remove = document.createElement('a');
				remove.href = 'javascript:;';
				remove.onclick = function(){removeWeatherLocation(this)};
				remove.appendChild(document.createTextNode(' '));
				var img = document.createElement('img');
				img.src = '<c:url value="/images/delete.png"/>';
				img.style.verticalAlign = 'middle';
				remove.appendChild(img);
				container.appendChild(remove);
				div.appendChild(container);
			}
			
			function removeWeatherLocation(link) {
				var div = link.parentNode;
				div.parentNode.removeChild(div);
			}
		</script>
	</head>
	<body>
		<portlet:actionURL var="url"/>
		<form method="post" action="${url}">

			<form:form commandName="prefs">
				<label class="portlet-form-field-label">Preferred locations:</label>
				<div id="weather-locations">
					<c:forEach items="${prefs.location}" var="location">
						<div style="padding: 5px;">
							<input type="text" name="location" value="${location}" size="8"/>
							<a href="javascript:;" onclick="removeWeatherLocation(this)">
								<img style="vertical-align: middle;" src="<c:url value="/images/delete.png"/>"/>
							</a>
						</div>
					</c:forEach>
				</div>
				<br/>
				<p>
					<a href="javascript:;" onclick="addWeatherLocation('weather-locations')">
						<img style="vertical-align: middle;" src="<c:url value="/images/add.png"/>"/>
						add a location</a>
				</p>
				<p><label class="portlet-form-field-label">Temperature unit:</label>
					<form:radiobutton path="tempUnit" value="f"/> F <form:radiobutton
						path="tempUnit" value="c"/> C </p>
				<p>
					<button type="submit" class="portlet-form-button">Save preferences</button>
				</p>
			</form:form>
		</form>
        <hr />
        <p>
        	<a href="<portlet:renderURL portletMode="view"/>"><img src="<c:url value="/images/arrow_left.png"/>" style="vertical-align: middle"> Return to main view</a>
        </p>
	</body>
</html>
