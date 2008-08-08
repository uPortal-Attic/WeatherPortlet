<%-- Author: Dustin Schultz | Version $Id$ --%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:if test="${useInlineCSSTags}">
	<link rel="stylesheet" href="<c:url value="/css/weather.css"/>" type="text/css" />
	<link rel="stylesheet" href="<c:url value="/css/jquery.autocomplete.css"/>" type="text/css" />
</c:if>
<c:set var="namespace"><portlet:namespace/></c:set>
<div class="weatherportlet">
    <c:if test="${jQueryEnabled}">
        <script type="text/javascript" src="${context}/js/cities.js"></script>
        <script type="text/javascript" src="${context}/js/jquery.autocomplete.js"></script>
        <c:if test="${useInlineCSSTags}">
	        <style type="text/css">
	            @import url("${context}/css/jquery.autocomplete.css");
	        </style>
        </c:if>
        <script>
            $(document).ready(function(){
                $("#${namespace}location").autocompleteArray(cities,
                    {
                        delay:10,
                        minChars:1,
                        matchSubset:1,
                        autoFill:true,
                        maxItemsToShow:10
                    }
                );
                $("#${namespace}location").click(function() {
                    $("#${namespace}location")[0].autocompleter.findValue();
                    return false;
                });
                
                $("#${namespace}searchSubmit").click(function() {
                    var locVal = $("#${namespace}location").val();
                    $.ajax( {
                        type: "GET",
                        url: "${context}/FindCity",
                        data: "location=" + locVal,
                        dataType: "xml",
                        success: function(xml) {
                            var html = "";
                            $("location", xml).each(function() {
                                html += "\t<option value = \"" + $(this).attr("city") + ", " + $(this).attr("state") + "+" + $(this).attr("location") + "\">" + $(this).attr("city") + ", " + $(this).attr("state") + "</option>\r\n";
                            });
                            $("#${namespace}locationCode").html(html);
                            $("#${namespace}locationCode").find("option")[0].selected = true;
                        }
                    });
                    $(".search-results").show();
                    return false;
                });
                
<!--                $("#${namespace}addSubmit").click(function() {-->
<!--                    var locVals = $("#${namespace}locationCode").val().split("+");-->
<!--                    var stndVals = $("#${namespace}temperatureUnit").val();-->
<!--                    var html = "<tr>\r\n";-->
<!--                    html += "\t<td>" + locVals[0]; + "</td>\r\n";-->
<!--                    html += "\t<td>" + stndVals; + "</td>\r\n";-->
<!--                    html += "\t<td>delete</td>\r\n";-->
<!--                    html += "</tr>\r\n";-->
<!--                    $(".locations").find("table").append(html);-->
<!--                    $(".search-results").hide();-->
<!--                    $(".locations").show();-->
<!--                    $.post(-->
<!--                       $("#${namespace}addForm").attr("action"),-->
<!--                       { locationCode: $("#${namespace}locationCode").val(), metric: $("#${namespace}temperatureUnit").val() }-->
<!--                    );-->
<!--                    return false;-->
<!--                });-->
            });
        </script>
    </c:if>
    <portlet:actionURL var="formSearchAction">
        <portlet:param name="action" value="search"/>
    </portlet:actionURL>
    <form:form commandName="weatherEditCommand" action="${formSearchAction}" id="${namespace}weatherSearchForm">
        <form:errors path="*" cssClass="error"/>
        <p><spring:message code="edit.enter.location"/>:</p>
        <spring:message var="search" code="edit.search.button"/>
        <form:input path="location" id="${namespace}location"/><input type="submit" id="${namespace}searchSubmit" name="searchSubmit" value="${search}"/>
    </form:form>
    <div class="search-results" style="display: none;">
        <portlet:actionURL var="formAddAction">
            <portlet:param name="action" value="add"/>
        </portlet:actionURL>
        <form:form commandName="weatherEditCommand" action="${formAddAction}" id="${namespace}addForm">
            <p><spring:message code="edit.multiple.locations"/></p>
            <form:select path="locationCode" id="${namespace}locationCode">
                <c:forEach var="location" items="${locationResults}">
                    <form:option value="${location.city}, ${location.stateOrCountry}+${location.locationCode}">${location.city}, ${location.stateOrCountry}</form:option>
                </c:forEach>
            </form:select>
            <p><spring:message code="edit.select.metric"/>:</p>
            <form:select path="metric" id="${namespace}temperatureUnit">
                <form:option value="false"><spring:message code="edit.standard.option"/></form:option>
                <form:option value="true"><spring:message code="edit.metric.option"/></form:option>
            </form:select>
            <br/><br/>
            <spring:message var="addLocation" code="edit.add.location.button"/>
            <spring:message var="cancelSearch" code="edit.cancel.search.button"/>
            <input type="submit" id="${namespace}addSubmit" name="addSubmit" value="${addLocation}"/><input type="submit" id="${namespace}cancelSearchSubmit" name="cancelSearchSubmit" value="${cancelSearch}"/>
        </form:form>
    </div>
    <div class="locations">
        <p><spring:message code="edit.saved.locations.title"/></p>
        <form:form id="${namespace}deleteForm">
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
    <portlet:actionURL var="formDoneAction">
        <portlet:param name="action" value="done"/>
    </portlet:actionURL>
    <form:form action="${formDoneAction}" id="${namespace}doneForm">
        <spring:message var="done" code="edit.done.button"/>
        <input type="submit" id="${namespace}doneSubmit" name="doneSubmit" value="${done}"/>
    </form:form>
</div>