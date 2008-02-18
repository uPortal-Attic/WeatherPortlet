<%-- Author: Dustin Schultz | Version $Id$ --%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div id="weatherportlet-<portlet:namespace/>">
	<portlet:actionURL var="formAction"/>
	<form:form commandName="weatherEditCommand" action="${formAction}">
		<form:errors path="*" cssClass="error"/>
		<p>Please enter a city or zip code:</p>
		<form:input path="location" id="location"/>
		<c:if test="${not empty locations}">
			<p>Multiple locations found, please select one</p>
			<form:select path="code">
				<form:option value="">Select a location ...</form:option>
				<c:forEach var="location" items="${locations}">
					<form:option value="${location.locationCode}">${location.city}, ${location.stateOrCountry}</form:option>
				</c:forEach>
			</form:select>
		</c:if>
		<p>Please select a standard of measurement:</p>
		<form:select path="metric" id="temperatureUnit">
			<form:option value="false" label="Standard"></form:option>
			<form:option value="true" label="Metric"></form:option>
		</form:select>
		<br/><br/>
		<input type="submit" id="submit" name="submit" value="Save"></input><input type="submit" id="cancel" name="cancel" value="Cancel"></input>
	</form:form>
</div>