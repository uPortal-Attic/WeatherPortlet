/* Copyright 2008 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package org.jasig.portlet.weather;

import java.math.BigDecimal;


public class Point {
	BigDecimal latitude;
	BigDecimal longitude;
	
	public String toString(){
		return "{"+latitude+","+longitude+"}";
	}
}
