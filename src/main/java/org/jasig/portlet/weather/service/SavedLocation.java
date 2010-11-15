/**
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

package org.jasig.portlet.weather.service;

import org.apache.commons.lang.Validate;
import org.jasig.portlet.weather.TemperatureUnit;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public class SavedLocation {
    public final String code;
    public final String name;
    public final TemperatureUnit temperatureUnit;

    public SavedLocation(String code, String name, TemperatureUnit temperatureUnit) {
        Validate.notNull(code, "location code cannot be null");
        this.code = code;
        this.name = name;
        
        if (temperatureUnit == null) {
            temperatureUnit = TemperatureUnit.F;
        }
        this.temperatureUnit = temperatureUnit;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public TemperatureUnit getTemperatureUnit() {
        return this.temperatureUnit;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.code == null) ? 0 : this.code.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SavedLocation other = (SavedLocation) obj;
        if (this.code == null) {
            if (other.code != null)
                return false;
        }
        else if (!this.code.equals(other.code))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SavedLocation [code=" + this.code + ", name=" + this.name + ", temperatureUnit=" + this.temperatureUnit + "]";
    }
}
