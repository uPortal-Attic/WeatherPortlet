/* Copyright 2008 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package org.jasig.portlet.weather;

import java.util.Comparator;

public class StationComparator implements Comparator<Station> {

	public int compare(Station o1, Station o2) {
		String n1=null;
		String n2=null;
		if (o1 != null){
			n1 = o1.name;
		}
		if (o2 != null){
			n2 = o2.name;
		}
		return n1.compareToIgnoreCase(n2);
	}

}
