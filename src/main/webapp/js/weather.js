/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

var weatherPortlet = weatherPortlet || {};

(function ($, fluid) {
    // Declare the component and its configuration
    fluid.defaults("weatherPortlet.editCities", {
        saveOrderUrl: null,
        updateUnitUrl: null,
        deleteLocationUrl: null,
        noLocationsMessage: 'No Locations Found',
        searchingMessage: 'Searching ...',
        evenRow: 'r1',
        oddRow: 'r2',
        templateRowClass: 'template',
        cityRowClass: 'movable',
        selectors: {
            locationRow: '.locations table tr.movable',
            templateRow: '.locations table tr.template',
            orderCell: 'td.handle',
            locationCode: 'input[name=code]',
            selectUnits: '.locations table tr.movable select.select-units',
            deleteLocation: '.locations table tr.movable .delete-location-link',
            addLocationLink: '.add-location-link',
            locationSearch: '.location-search',
            locationList: '.locations',
            locationSearchForm: '.locate-search-form',
            locationSearchMessage: '.search-message',
            cancelSearchLink: '.weather-search-form-cancel',
            searchResults: '.search-results',
            searchResultsForm: '.search-results .select-location-form',
            searchResultsOptions: '.search-results .select-location-form select[name="locationCode"]',
            searchResultsSelectedOption: 'select[name="locationCode"] option:selected'
        },
        events: {
            addCity: null,
            deleteCity: null
        }
    });
    
    var init = function (that) {
        that.refreshLocationRows();
        
        that.locate('selectUnits').change(function(event) {
            changeLocationUnits(that, event, $(this));
        });
        
        that.locate('deleteLocation').click(function(event) {
            deleteLocation(that, event, $(this));
        });
        
        that.locate('addLocationLink').click(function() {
            that.locate('locationList').hide();
            that.locate('locationSearch').show();
        });
        
        that.locate('locationSearchForm').ajaxForm({ 
            dataType:  'json', 
            beforeSubmit: function() {
                that.locate('locationSearchMessage').show();
            },
            success:   function(data) {
                var locations = data.locations;
                if (locations.length == 0) {
                   that.locate('locationSearchMessage').text(that.options.noLocationsMessage);
                }
                else {
                    var select = that.locate('searchResultsOptions').html("").get(0);
                    $.each(locations, function(i, location){
                        select.options[i] = new Option(location.city + ', ' + location.stateOrCountry, location.locationCode);
                    });
                    
                    that.locate('locationSearch').hide();
                    that.locate('searchResults').show();
                }
            }
        }); 
        
        that.locate('cancelSearchLink').click(function() { 
            return cancelSearch(that); 
        });
        
        that.locate('searchResultsForm').ajaxForm({ 
            dataType:  'json', 
            beforeSubmit: function(formData, jqForm, options) {
                var location = that.locate('searchResultsSelectedOption', jqForm).text();
                formData[formData.length] = { name: 'location', value: location};
            },
            success:   function(data) {
                if (data.status != 'success') {
                    //TODO display error message about adding location
                    return;
                }
                
                cancelSearch(that);
        
                var templateRow = that.locate('templateRow');
                var newRow = templateRow.clone();
                 
                var lastRow = templateRow.siblings(':last');
                if (lastRow.size() == 0) lastRow = templateRow;
                lastRow.after(newRow);
                
                newRow.removeClass(that.options.templateRowClass);
                newRow.addClass(that.options.cityRowClass);
                 
                that.locate('locationCode', newRow).val(data.location.code);
                 
                var unitSelect = newRow.find('select.select-units');
                unitSelect.attr('name', unitSelect.attr('name') + data.location.code);
                unitSelect.attr('id', unitSelect.attr('id') + data.location.code);
                unitSelect.find('option[value="' + data.location.temperatureUnit + '"]').attr('selected', 'selected');
                
                newRow.find('.location-name').text(data.location.name);
                 
                newRow.show();
                 
                that.events.addCity.fire();
            }
        });
    };
    
    weatherPortlet.editCities = function (container, options) {
        var that = fluid.initView("weatherPortlet.editCities", container, options);
        
        that.refreshLocationRows = function () {
            that.locate('locationRow').each(function (i, row) {
                //Update location order numbering
                that.locate('orderCell', row).html((i + 1) + ".");
                
                //Update zebra striping
                if (i % 2 == 0) {
                    $(row).removeClass(that.options.oddRow).addClass(that.options.evenRow);
                } else {
                    $(row).removeClass(that.options.evenRow).addClass(that.options.oddRow);
                }
            });
        };
        
        that.saveOrder = function (item, requestedPosition, movables) {
            var locationCodes = new Array();
            $.each(movables, function(i, row) {
                locationCodes[i] = that.locate('locationCode', row).val();
            });
        
            $.post(
                that.options.saveOrderUrl, 
                { "locationCodes" : locationCodes },
                function (data) {
                    //TODO handle success or failure
                },
                "json"
            );
            
        };
        
        init(that);
        
        return that;
    };
    
    var changeLocationUnits = function(that, event, target) {
        //TODO is there a fluidy way to do this?
        var row = target.parents('.locations table tr.movable');
        var locationCode = that.locate('locationCode', row).val();
        var unit =  target.val();
        
        $.post(
            that.options.updateUnitUrl, 
            { 
                'locationCode' : locationCode,
                'unit' : unit 
            },
            function (data) {
                //TODO handle success or failure
            },
            "json"
        );
    };
    
    var deleteLocation = function(that, event, target) {
        //TODO is there a fluidy way to do this?
        var row = target.parents('.locations table tr.movable');
        var locationCode = that.locate('locationCode', row).val();
        
        $.post(
            that.options.deleteLocationUrl, 
            { 'locationCode' : locationCode },
            function (data) {
                if (data.status == 'success') {
                    row.remove();
                    that.events.deleteCity.fire();
                }
                else {
                    //TODO error message
                }
            },
            "json"
        );
    };
    
    var cancelSearch = function(that) {
        that.locate('locationSearch').hide();
        that.locate('searchResults').hide();
        
        that.locate('locationList').show();
        
        that.locate('locationSearchForm').clearForm();
        that.locate('locationSearchMessage').text(that.options.searchingMessage);
        that.locate('locationSearchMessage').hide();
        
        return false;
    };
})(jQuery, fluid);

if (!weatherPortlet.jQuery) {
    weatherPortlet.jQuery = jQuery.noConflict(true);
    weatherPortlet.fluid = fluid;
}
else {
    jQuery.noConflict(true);
}
fluid = null;