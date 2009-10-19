/**
 * Copyright (c) 2000-2009, Jasig, Inc.
 * See license distributed with this file and available online at
 * https://www.ja-sig.org/svn/jasig-parent/tags/rel-10/license-header.txt
 */

package org.jasig.portlet.weather;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public enum TemperatureUnit {
    C,
    F;
    
    public static TemperatureUnit safeValueOf(String s) {
        try {
            return TemperatureUnit.valueOf(s);
        }
        catch (RuntimeException re) {
            return TemperatureUnit.F;
        }
    }
}
