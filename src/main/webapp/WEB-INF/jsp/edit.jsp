<%-- Author: Dustin Schultz | Version $Id$ --%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div id="weatherportlet-<portlet:namespace/>">
	<portlet:actionURL var="formSearchAction">
		<portlet:param name="action" value="search"/>
	</portlet:actionURL>
	<form:form commandName="weatherEditCommand" action="${formSearchAction}" id="searchForm">
		<form:errors path="*" cssClass="error"/>
		<p><spring:message code="edit.enter.location"/>:</p>
		<form:input path="location" id="location"/><input type="submit" id="searchSubmit" name="searchSubmit" value="Search"/>
	</form:form>
	<c:if test="${not empty locations}">
		<portlet:actionURL var="formAddAction">
			<portlet:param name="action" value="add"/>
		</portlet:actionURL>
		<form:form commandName="weatherEditCommand" action="${formAddAction}" id="addForm">
			<p><spring:message code="edit.multiple.locations"/></p>
			<form:select path="locationCode">
				<form:option value=""><spring:message code="edit.select.location"/></form:option>
				<c:forEach var="location" items="${locations}">
					<form:option value="${location.locationCode}">${location.city}, ${location.stateOrCountry}</form:option>
				</c:forEach>
			</form:select>
			<p><spring:message code="edit.select.metric"/>:</p>
			<form:select path="metric" id="temperatureUnit">
				<form:option value="false"><spring:message code="edit.standard"/></form:option>
				<form:option value="true"><spring:message code="edit.metric"/></form:option>
			</form:select>
			<br/><br/>
			<input type="submit" id="addSubmit" name="addSubmit" value="Add Location"/><input type="submit" id="searchAgainSubmit" name="searchAgainSubmit" value="Search Again"/>
		</form:form>
	</c:if>
	<%-- 
	<form:form id="deleteForm">
		<c:forEach var="savedLocation" items="${savedLocationsMap}">
			<c:set></c:set>
			
		</c:forEach>
	</form:form>
	--%>
	<portlet:actionURL var="formDoneAction">
		<portlet:param name="action" value="done"/>
	</portlet:actionURL>
	<form:form action="${formDoneAction}" id="doneForm">
		<input type="submit" id="doneSubmit" name="doneSubmit" value="Done"/>
	</form:form>
</div>