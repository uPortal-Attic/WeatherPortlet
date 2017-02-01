# Apereo Weather Portlet

This is a [Sponsored Portlet][] in the uPortal project.

[Link to old documentation](https://wiki.jasig.org/display/PLT/Weather+Portlet)

## Table of Contents
  - [Description](#desc)
  - [Configuration](#config)
    - [Weather Locations](#locations)
    - [Yahoo! Weather API Key](#yahoo!)
  - [Where to Get Help](#help)
  - [Contribution Guidelines](#contrib)
  - [License](#license)

## <a name="desc"></a> Description

The Apereo Weather Portlet is a fully-functional JSR-286 Java portlet that provides
international weather using either World Weather Online or Yahoo Weather. If permitted,
end users have the ability add and delete multiple locations and standards of measurement
with ease!

The Weather Portlet has been bundled with uPortal since 3.2.0.

## <a name="config"></a> Configuration

### <a name="locations"></a> Weather Locations

The default weather service is [Yahoo! Weather](https://developer.yahoo.com/weather/).
Weather locations are most commonly configured by the portal developer/administrator
using portlet preferences. *Currently this method does not require an API key.*

#### Example Weather Locations Configuration:

```
<portlet-preference>
    <name>locationCodes</name>
    <value>2459115</value>
    <value>1118370</value>
</portlet-preference>
<portlet-preference>
    <name>locations</name>
    <value>New York, NY</value>
    <value>Tokyo, Japan(Tokyo)</value>
</portlet-preference>
<portlet-preference>
    <name>units</name>
    <value>F</value>
    <value>C</value>
</portlet-preference>
<portlet-preference>
    <name>showEditLink</name>
    <value>false</value>
</portlet-preference>
```

You can discover `locationCodes` for your locations by visiting the associated page
in Yahoo! Weather.  For example, the following URL belongs to the Yahoo! Weather page
for Phoenix, AZ:

  - <https://www.yahoo.com/news/weather/united-states/arizona/phoenix-2471390/>

The location code for Phoenix is 2471390.

### <a name="yahoo!"></a> Yahoo! Weather API Key

The Apereo Weather Portlet provides an EDIT mode that allows users to manage their own
weather locations.  To use it, you must provide a valid API key from developer.yahoo.com.

To obtain an API key, you must [Create an App](https://developer.yahoo.com/apps/create)
in the Yahoo! Developer Network portal.  Once you create your app, copy the
`Client Secret (Consumer Secret)` that Yahoo! generates.

Paste the `Client Secret` in the space provided within
`src/main/webapp/WEB-INF/context/applicationContext.xml`:

```
<!--
    Yahoo! Weather DAO Implementation
-->

<bean id="weatherDaoImpl" class="org.jasig.portlet.weather.dao.yahoo.YahooWeatherDaoImpl">
    <property name="key" value=""/> <!--  ENTER YOUR KEY HERE -->
    <property name="weatherParsingService" ref="weatherParsingService"/>
    <property name="locationParsingService" ref="locationParsingService"/>
    <property name="messageSource" ref="messageSource"/>
</bean>
```

You must assemble and deploy a new build of the Weather Portlet for this change to take effect.

## <a name="help"></a> Where to Get Help
The <uportal-user@apereo.org> mailing list is the best place to go with
questions related to Apereo portlets and uPortal.

Issues should be reported at <https://issues.jasig.org/browse/WPP>.
Check if your issue has already been reported. If so, comment that you are also
experiencing the issue and add any detail that could help resolve it. Feel free to
create an issue if it has not been reported. Creating an account is free and can be
initiated at the Login widget in the default dashboard.

## <a name="contrib"></a> Contribution Guidelines
Apereo requires contributors sign a contributor license agreement (CLA).
We realize this is a hurdle. To learn why we require CLAs, see
"Q5. Why does Apereo require Contributor License Agreements (CLAs)?"
at <https://www.apereo.org/licensing>.

The CLA form(s) can be found <https://www.apereo.org/licensing/agreements> along
with the various ways to submit the form.

Contributions will be accepted once the contributor's name appears at
<http://licensing.apereo.org/completed-clas>.

See <https://www.apereo.org/licensing> for details.

## <a name="license"></a> License

Copyright 2016 Apereo Foundation, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this project except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

See <https://www.apereo.org/licensing> for additional details.

[Sponsored Portlet]: https://wiki.jasig.org/display/PLT/Jasig+Sponsored+Portlets
