<%-- Author: Dustin Schultz | Version $Id$ --%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<div class="weatherportlet">
	<portlet:actionURL var="formSearchAction">
		<portlet:param name="action" value="search"/>
	</portlet:actionURL>
	<form:form commandName="weatherEditCommand" action="${formSearchAction}" id="searchForm">
		<form:errors path="*" cssClass="error"/>
		<p><spring:message code="edit.enter.location"/>:</p>
		<spring:message var="search" code="edit.search.button"/>
		<form:input path="location" id="location"/><input type="submit" id="searchSubmit" name="searchSubmit" value="${search}"/>
	</form:form>
	<c:if test="${not empty locationResults}">
		<div class="search-results">
			<portlet:actionURL var="formAddAction">
				<portlet:param name="action" value="add"/>
			</portlet:actionURL>
			<form:form commandName="weatherEditCommand" action="${formAddAction}" id="addForm">
				<p><spring:message code="edit.multiple.locations"/></p>
				<form:select path="locationCode">
					<form:option value=""><spring:message code="edit.select.location.option"/></form:option>
					<c:forEach var="location" items="${locationResults}">
						<form:option value="${location.city}, ${location.stateOrCountry}+${location.locationCode}">${location.city}, ${location.stateOrCountry}</form:option>
					</c:forEach>
				</form:select>
				<p><spring:message code="edit.select.metric"/>:</p>
				<form:select path="metric" id="temperatureUnit">
					<form:option value="false"><spring:message code="edit.standard.option"/></form:option>
					<form:option value="true"><spring:message code="edit.metric.option"/></form:option>
				</form:select>
				<br/><br/>
				<spring:message var="addLocation" code="edit.add.location.button"/>
				<spring:message var="cancelSearch" code="edit.cancel.search.button"/>
				<input type="submit" id="addSubmit" name="addSubmit" value="${addLocation}"/><input type="submit" id="cancelSearchSubmit" name="cancelSearchSubmit" value="${cancelSearch}"/>
			</form:form>
		</div>
	</c:if>
	<c:if test="${not empty savedLocations}">
		<div class="locations">
			<p><spring:message code="edit.saved.locations.title"/></p>
			<form:form id="deleteForm">
				<table>
				<tr>
					<th>Location</th>
					<th>Measurement</th>
					<th>Delete</th>
				</tr>
					<spring:message var="delImgSrc" code="del.img.full.path"/>
					<spring:message var="delImgWidth" code="del.img.width"/>
					<spring:message var="delImgHeight" code="del.img.height"/>
					<c:forEach var="savedLocation" items="${savedLocations}" varStatus="status">
						<c:set var="locCode" value="${savedLocation.key}"/>
						<portlet:actionURL var="formDeleteAction">
							<portlet:param name="action" value="delete"/>
							<portlet:param name="key" value="${locCode}"/>
						</portlet:actionURL>
						<c:set var="shadeClass" value="${(status.count % 2 == 0) ? 'r2' : 'r1'}"/>
						<tr class="${shadeClass}">
							<c:forEach var="locationAttr" items="${savedLocation.value}">
								<td>${locationAttr}</td>
							</c:forEach>
							<td class="delete"><a href="${formDeleteAction}"><img width="${delImgWidth}" height="${delImgHeight}" src="${context}/${delImgSrc}"/></a></td>
						</tr>
					</c:forEach>
				</table>
			</form:form>
		</div>
	</c:if>
	<portlet:actionURL var="formDoneAction">
		<portlet:param name="action" value="done"/>
	</portlet:actionURL>
	<form:form action="${formDoneAction}" id="doneForm">
		<spring:message var="done" code="edit.done.button"/>
		<input type="submit" id="doneSubmit" name="doneSubmit" value="${done}"/>
	</form:form>
</div>