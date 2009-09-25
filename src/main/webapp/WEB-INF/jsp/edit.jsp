<%-- Author: Dustin Schultz | Version $Id$ --%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<portlet:defineObjects/>
<c:set var="useInlineCSSTags" value="${renderRequest.preferences.map['useInlineCSSTags'][0]}"/>
<c:set var="includeJQuery" value="${renderRequest.preferences.map['includeJQuery'][0]}"/>

<c:set var="n"><portlet:namespace/></c:set>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:if test="${useInlineCSSTags}">
	<link rel="stylesheet" href="<c:url value="/css/weather.css"/>" type="text/css" />
</c:if>
<spring:message var="delImgSrc" code="del.img.full.path"/>
<spring:message var="delImgWidth" code="del.img.width"/>
<spring:message var="delImgHeight" code="del.img.height"/>

<portlet:actionURL var="deleteLocationUrl">
    <portlet:param name="action" value="delete"/>
</portlet:actionURL>


<div class="weatherportlet">
        <c:if test="${includeJQuery}">
            <script type="text/javascript" src="<rs:resourceURL value="/rs/jquery/1.3.2/jquery-1.3.2.min.js"/>"></script>
        </c:if>
        <c:if test="${useInlineCSSTags}">
	        <style type="text/css">
	            @import url("${context}/css/sexy-combo.css");
	        </style>
        </c:if>
        <script>
	        var ${n} = ${n} || {};
	        ${n}.jQuery = ${ includeJQuery ? 'jQuery.noConflict(true)' : 'jQuery' };
	        ${n}.jQuery(function(){
	            var $ = ${n}.jQuery;

	            var deleteLocation = function(link) {
		            var key = $(link).attr("key");
		            $.post('<portlet:actionURL><portlet:param name="action" value="delete"/></portlet:actionURL>', 
				        { key: key}, 
				        function(json){
					        $(link).parent().parent().remove();
				        }, 
				        "json"
				    );
	            };

	            var resetSearchForm = function() {
                    $('.search-message').css('display', 'none');
                    $('#${n}locationSearchText').val('');
                    return false;
		        };

	            var closeSearchForm = function() {
                    resetSearchForm();
                    $('.location-search').css('display', 'none');
                    $('.search-results').css('display', 'none');
                    $('.search-message').css('display', 'none').text('<spring:message code="edit.search.loading"/>');
                    $('.locations').css('display', 'block');
                    return false;
		        };
	            
	            $(document).ready(function(){
	                $('.delete-location-link').click(function(){ deleteLocation(this) });
	                
	                $('.add-location-link').click(function(){
                        $('.locations').css('display', 'none');
		                $('.location-search').css('display', 'block');
		            });

		            $('.weather-search-form-cancel').click(closeSearchForm);

	                $('#${n}locationSearchForm').submit(function(){
		                $('.search-message').css('display', 'block');
                        var search = $('#${n}locationSearchText').val();
                        $.ajax( {
                            type: "GET",
                            url: "${context}/FindCity",
                            data: { location : search },
                            dataType: "xml",
                            success: function(xml) {
                                locations = $(xml).find('location');
                                if (locations.size() == 0) {
                                    $('.search-message').text('<spring:message code="edit.noresults.location"/>');
                                } else {
	                                var select = $('#${n}locationSelect').html("").get(0);
	                                locations.each(function(i, location){
	                                    location = $(location);
	                                    select.options[i] = new Option(location.attr('city') + ', ' + location.attr('state'), location.attr('location'));
	                                });
	                                $('.location-search').css('display', 'none');
	                                $('.search-results').css('display', 'block');
                                }
                            }
                        });
		                return false;
		            });
		            $('#${n}addLocationForm').submit(function(){
			            var form = this;
			            var data = {
					        location: $(form.location).find(":selected").text(),
					        locationCode: form.location.value,
					        metric: form.metric.value
					    };
	                    $.post('<portlet:actionURL><portlet:param name="action" value="add"/></portlet:actionURL>', 
	                        data, 
	                        function(json){
	                            if (json.status != 'success') {
		                            closeSearchForm();
		                            return false;
	                            }
	                        
	                            var row = $(document.createElement('tr'));
	                            row.append(
	    	                        $(document.createElement('td')).html(data.location)
	    	                    ).append(
                                    $(document.createElement('td')).html(data.metric == 'true' ? 'Metric' : 'Standard')
                                );

	                            var link = $(document.createElement('a'))
	                               .attr('href', 'javascript:;')
	                               .attr('key', data.locationCode)
	                               .addClass('delete-location-link')
	                               .click(function(){ deleteLocation(this); })
	                               .append(
	    	                           $(document.createElement('img'))
	    	                              .attr('width', '${delImgWidth}')
	    	                              .attr('height', '${delImgHeight}')
	    	                              .attr('src','${context}/${delImgSrc}')
	    	                        );
                                row.append(
                                    $(document.createElement('td')).addClass('delete')
                                        .append(link)
                                );
                                $('#${n}savedLocationsTable tbody').append(row);
                                closeSearchForm();
                                
	                        }, 
	                        "json"
	                    );
			            return false;
		            });
	            });
	        });
        </script>
        
    <div class="location-search" style="display:none">
        <h2><spring:message code="edit.add.location.button"/></h2>
        <form id="${n}locationSearchForm">
	        <p><label class="portlet-form-label"><spring:message code="edit.enter.location"/>:</label></p>
	        <input id="${n}locationSearchText" name="locationSearchText" class="portlet-form-input-field"/>
	        <spring:message var="search" code="edit.search.button"/>
	        <input type="submit" name="submit" value="submit" class="portlet-form-button"/>
            <button class="portlet-form-button weather-search-form-cancel"><spring:message code="edit.cancel.search.button"/></button>
        </form>
        <p class="search-message" style="display:none"><spring:message code="edit.search.loading"/></p>
    </div>
    <div class="search-results" style="display:none">
        <h2><spring:message code="edit.add.location.button"/></h2>
        <form id="${n}addLocationForm">
            <p><label class="portlet-form-label"><spring:message code="edit.multiple.locations"/>:</label></p>
            <select id="${n}locationSelect" name="location" class="portlet-form-input-field"></select>
            
            <p><label class="portlet-form-label"><spring:message code="edit.select.metric"/>:</label></p>
            <select name="metric" id="${n}temperatureUnit" class="portlet-form-input-field">
                <option value="false"><spring:message code="edit.standard.option"/></option>
                <option value="true"><spring:message code="edit.metric.option"/></option>
            </select>
            <br/><br/>
            <spring:message var="addLocation" code="edit.add.location.button"/>
            <spring:message var="cancelSearch" code="edit.cancel.search.button"/>
            <input type="submit" value="${addLocation}" class="portlet-form-button"/>
            <button class="portlet-form-button weather-search-form-cancel"><spring:message code="edit.cancel.search.button"/></button>
        </form>
    </div>
    <div class="locations">
    
        <h2>Edit Weather Locations</h2>
    
        <p><spring:message code="edit.saved.locations.title"/></p>
            <table id="${n}savedLocationsTable">
            <thead>
	            <tr>
	                <th>Location</th>
	                <th>Measurement</th>
	                <th>Delete</th>
	            </tr>
            </thead>
            <tbody>
                <spring:message var="delImgSrc" code="del.img.full.path"/>
                <spring:message var="delImgWidth" code="del.img.width"/>
                <spring:message var="delImgHeight" code="del.img.height"/>
                <c:forEach var="savedLocation" items="${savedLocations}" varStatus="status">
                    <c:set var="shadeClass" value="${(status.count % 2 == 0) ? 'r2' : 'r1'}"/>
                    <tr class="${shadeClass}">
                        <c:forEach var="locationAttr" items="${savedLocation.value}">
                            <td>${locationAttr}</td>
                        </c:forEach>
                        <td class="delete"><a href="javascript:;" key="${savedLocation.key}" class="delete-location-link"><img width="${delImgWidth}" height="${delImgHeight}" src="${context}/${delImgSrc}"/></a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <p><a class="add-location-link" href="javascript:;"><spring:message code="edit.add.location.button"/></a></p>
	    
	    <portlet:renderURL var="formDoneAction" portletMode="VIEW"/>
	    <p><button onclick="window.location='${formDoneAction}'" class="portlet-form-button"><spring:message code="edit.done.button"/></button></p>
    </div>
</div>